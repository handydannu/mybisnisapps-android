package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.github.kittinunf.fuel.Fuel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wardwar.mybisnisindoneisa.network.request.SosmedAuth
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_unautorize.*
import java.util.*


class UnautorizeActivity : AppCompatActivity() {
    private val TAG = UnautorizeActivity::class.java.simpleName

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var callbackManager: CallbackManager

    private val RC_SIGN_IN = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unautorize)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mGoogleSignInClient.signOut()

        callbackManager = CallbackManager.Factory.create()

        unautorize_fb_btn.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        }

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("Login Facebook token", loginResult.accessToken.token)


                val request = GraphRequest.newMeRequest(
                        loginResult.accessToken
                ) { result, response ->
                    Log.v("LoginActivity", response.toString())

                    if (Profile.getCurrentProfile() == null) {

                        object : ProfileTracker() {
                            override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {
                                // Application code
                                val email = result.getString("email")
                                val name = Profile.getCurrentProfile().name
                                val picture = Profile.getCurrentProfile().getProfilePictureUri(512, 512)
                                signinServer(name, email, picture.toString())
                                stopTracking()
                            }
                        }
                    } else {
                        val email = result.getString("email")
                        val name = Profile.getCurrentProfile().name
                        val picture = Profile.getCurrentProfile().getProfilePictureUri(512, 512)
                        signinServer(name, email, picture.toString())
                    }

                    // Application code

                }

                val parameters = Bundle()
                parameters.putString("fields", "email")
                request.parameters = parameters
                request.executeAsync()

            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                Toast.makeText(applicationContext, "Authentication facebook cancel.",
                        Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                Crashlytics.logException(exception)
                Log.d(TAG, "facebook:onError : ${exception.localizedMessage}")
                Toast.makeText(applicationContext, "Authentication facebook failed : ${exception.localizedMessage}",
                        Toast.LENGTH_SHORT).show()
            }
        })





        unautorize_g_btn.setOnClickListener {
            signIn()
        }

        unautorize_login_btn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginFormActivity::class.java))
        }

        unautorize_signup_btn.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }


    }

    //mGoogleSignInClient.getSignInIntent()
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            account?.let { gmail ->
                gmail.id?.let {
                    val name = gmail.displayName
                    val email = gmail.email
                    val picture = gmail.photoUrl
                    signinServer(name!!, email!!, picture.toString())

                }
            }
        } catch (e: ApiException) {
            Crashlytics.logException(e)
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            e.printStackTrace()
            Toast.makeText(applicationContext, "Authentication google failed : ${e.localizedMessage}", Toast.LENGTH_SHORT).show()

        }

    }


    fun signinServer(name: String, email: String, picture: String) {
        val auth = SosmedAuth(name, email, picture)
        Log.d(TAG, "Picture ${picture}")
        Fuel.post(auth.endpoint, auth.request).responseObject(auth.response) { _, _, result ->
            result.fold({ data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        val preferencesHelper = PreferencesHelper(this)
                        preferencesHelper.deviceToken = data.data.token
                        preferencesHelper.deviceEmail = data.data.email
                        preferencesHelper.deviceName = data.data.name

                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        finishAffinity()
                    }
                }
            }, { err ->
                println("REQUEST ERROR ${err.message}")
                Toast.makeText(applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
            })

        }
    }

}
