package com.wardwar.mybisnisindoneisa


import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.network.request.Product
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_solusi_media_terpadu.*
import kotlinx.android.synthetic.main.component_header.*

class SolusiMediaTerpaduActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solusi_media_terpadu)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(this)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = 15

        val product = Product(idProduct, token, email)
        Fuel.get(product.endpoint, product.request).responseObject(product.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        @Suppress("DEPRECATION")
                        smt_text_view.text =  when {
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(data.data,Html.FROM_HTML_MODE_LEGACY)
                            else ->  Html.fromHtml(data.data)
                        }

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
