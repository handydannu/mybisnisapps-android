package com.wardwar.mybisnisindoneisa

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.network.request.Order
import com.wardwar.mybisnisindoneisa.network.request.Product
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_product_bi_weekend.*
import kotlinx.android.synthetic.main.component_header.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

class ProductBiWeekend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_bi_weekend)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(this)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = 19

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
            result.fold({ data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        bi_weekend_text_view.setHtml(data.data, HtmlHttpImageGetter(bi_weekend_text_view))
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
