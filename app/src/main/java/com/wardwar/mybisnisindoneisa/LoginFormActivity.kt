package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.network.request.Auth
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_login_form.*

class LoginFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        forgot.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }

        login_btn.setOnClickListener {
            val username = forgot_email_text.text.toString()
            val password = login_password_form.text.toString()

            when {
                username.isEmpty() and password.isEmpty() -> Toast.makeText(applicationContext, "Form cannot empty!", Toast.LENGTH_SHORT).show()
                else -> {
                    val auth = Auth(username, password)
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
        }
    }
}
