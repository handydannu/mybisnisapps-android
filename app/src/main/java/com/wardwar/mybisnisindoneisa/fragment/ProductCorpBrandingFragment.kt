package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
class ProductCorpBrandingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_corp_branding, container, false)
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
        val idProduct = 12


        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        val orderButton = view.findViewById(R.id.order_product) as ImageButton

        val textview = view.findViewById(R.id.corp_branding_text_view) as HtmlTextView

        orderButton.setOnClickListener {
            showCreateCategoryDialog(idProduct,token,email)
        }

        val product = Product(idProduct, token, email)
        Fuel.get(product.endpoint, product.request).responseObject(product.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        textview.setHtml(data.data, HtmlHttpImageGetter(textview))
                    }
                }
            }, {
                err ->
                println("REQUEST ERROR ${err.message}")
                Toast.makeText(context, "Can't connect to internet", Toast.LENGTH_SHORT).show()
            })
        }
    }

    fun showCreateCategoryDialog(idProduct:Int,token:String,email:String) {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Pesan produk ini?")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.dialog_order, null)

        val categoryEditText = view.findViewById(R.id.categoryEditText) as EditText

        builder.setView(view)


        // set up the ok button
        builder.setPositiveButton("Order") { dialog, p1 ->
            val description = categoryEditText.text.toString()
            val order = Order(idProduct, token, email,description)
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

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()
    }


}
