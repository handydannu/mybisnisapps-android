package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper


class SplashActivity : AppCompatActivity() {
    private var delayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000


    internal val runnable: Runnable = Runnable {

        val preferencesHelper = PreferencesHelper(this)
        val token = preferencesHelper.deviceToken
        if (!isFinishing) {
            val intent = if (token.isEmpty()) {
                Intent(applicationContext, UnautorizeActivity::class.java)
            } else {
                Intent(applicationContext, HomeActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_splash)

        delayHandler = Handler()

        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(runnable)
        }
        super.onDestroy()
    }
}
