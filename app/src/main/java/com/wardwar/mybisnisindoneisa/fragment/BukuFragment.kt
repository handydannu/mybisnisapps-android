package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.Order
import com.wardwar.mybisnisindoneisa.network.request.Product
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView

/**
 * A simple [Fragment] subclass.
 *
 */
class BukuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buku, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById(R.id.btn_back) as Button

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }


        val context = context!!.applicationContext


        val preferencesHelper = PreferencesHelper(context)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = 10


        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        val orderButton = view.findViewById(R.id.order_product) as ImageButton

        val textView = view.findViewById(R.id.buku_text_view) as HtmlTextView


        orderButton.setOnClickListener{
            val order = Order(idProduct, token, email)
            Fuel.post(order.endpoint, order.request).responseObject(order.response) { _, _, result ->

                result.fold({data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(context, "Successfully order product", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(context, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                })

            }
        }

        val product = Product(idProduct, token, email)
        Fuel.get(product.endpoint, product.request).responseObject(product.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        textView.setHtml(data.data, HtmlHttpImageGetter(textView))
                    }
                }
            }, {
                err ->
                println("REQUEST ERROR ${err.message}")
                Toast.makeText(context, "Can't connect to internet", Toast.LENGTH_SHORT).show()
            })
        }
    }


}
