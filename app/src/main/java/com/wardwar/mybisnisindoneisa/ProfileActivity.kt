package com.wardwar.mybisnisindoneisa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.R.id.profile_name
import com.wardwar.mybisnisindoneisa.network.request.Profile
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.component_header.*
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(this)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val profile = Profile(token, email)

        Fuel.get(profile.endpoint, profile.request).responseObject(profile.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {

                        profile_name.text = data.data.name
                        profile_company.text = data.data.company
                        profile_email.text = data.data.email
                        profile_address.text = data.data.address
                        profile_phone.text = data.data.phone

                    }
                }
            }, {
                err ->
                println("REQUEST ERROR ${err.message}")
                Toast.makeText(applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
