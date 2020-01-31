package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.network.request.Register
import com.wardwar.mybisnisindoneisa.utils.CryptUtils
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var spinner: Spinner

    var cityId = arrayOf(1, 2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val city = arrayOf("Bandung", "Jakarta")


        spinner = this.register_city_form
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                println("nothing")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                println(city.get(p2))
            }
        }

        spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,city)


        register_btn.setOnClickListener {

            val name = register_name_form.text.toString()
            val email = register_email_form.text.toString()
            val address = register_address_form.text.toString()
            val phone = register_phone_form.text.toString()

            val company = register_company_form.text.toString()
            val password = register_password_form.text.toString()
            val retypePassword = register_retype_password_form.text.toString()

            if (password != retypePassword) {
                Toast.makeText(applicationContext, "Password not same.", Toast.LENGTH_SHORT).show()
            }

            when {(
                    name.isEmpty() and
                            email.isEmpty() and
                            address.isEmpty() and
                            phone.isEmpty() and
                            !register_city_form.isSelected and
                            company.isEmpty() and
                            password.isEmpty() and
                            retypePassword.isEmpty()
                    ) -> Toast.makeText(applicationContext, "Form cannot empty!", Toast.LENGTH_SHORT).show()
                else -> {
                    val register = Register(name, email, address, phone, cityId[register_city_form.selectedItemPosition], company, password)
                    Fuel.post(register.endpoint, register.request).responseObject(register.response) { _, _, result ->
                        result.fold({ data ->
                            when {
                                data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                                else -> {
                                    startActivity(Intent(applicationContext, UnautorizeActivity::class.java))
                                    Toast.makeText(applicationContext, "Registration is success, please login", Toast.LENGTH_SHORT).show()
                                    finish()
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
