package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.network.request.Order
import com.wardwar.mybisnisindoneisa.network.request.Product
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_konten_youtube_sosmed.*
import kotlinx.android.synthetic.main.component_footer.*
import kotlinx.android.synthetic.main.component_header.*
import kotlinx.android.synthetic.main.item_kontak.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

class ActivityKontenYoutubeSosmed : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konten_youtube_sosmed)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(this)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = 29


        order_product.setOnClickListener {
            val order = Order(idProduct, token, email,"")
            Fuel.post(order.endpoint, order.request).responseObject(order.response) { _, _, result ->
                result.fold({data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(applicationContext, "Successfully order product.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                })
            }
        }

        val product = Product(idProduct, token, email)
        Fuel.get(product.endpoint, product.request).responseObject(product.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        kys_text_view.setHtml(data.data, HtmlHttpImageGetter(kys_text_view))
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
