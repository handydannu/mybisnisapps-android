package com.wardwar.mybisnisindoneisa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.network.request.ForgotPassword
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        forgot_btn.setOnClickListener {
            val email = forgot_email_text.text.toString()

            when {
                email.isEmpty() -> Toast.makeText(applicationContext, "Form cannot empty!", Toast.LENGTH_SHORT).show()
                else -> {
                    val auth = ForgotPassword(email)
                    Fuel.post(auth.endpoint, auth.request).responseObject(auth.response) { _, _, result ->
                        result.fold({ data ->
                            when {
                                data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                                else -> {
                                    Toast.makeText(applicationContext, "Link untuk mengganti password akan di kirim melalui email, silahkan cek email anda.", Toast.LENGTH_LONG).show()
                                    forgot_email_text.text.clear()
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
