package com.wardwar.mybisnisindoneisa.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.KonfirmasiActivity
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
class ContentHtmlFragment : Fragment() {
    internal val KONFIRMASI_RESULT: Int = 1223
    lateinit var refresh: SwipeRefreshLayout
    lateinit var textView: HtmlTextView


    var id: Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_content_html, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById(R.id.btn_back) as Button

        refresh = view.findViewById(R.id.refresh) as SwipeRefreshLayout


        val bundle = arguments

        if (bundle != null) {
            id = bundle.getInt("id", 0)
        }

        backButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        val context = context!!.applicationContext


        val preferencesHelper = PreferencesHelper(context)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = id!!
        textView = view.findViewById(R.id.html) as HtmlTextView

        fetchData(idProduct, token, email)


        refresh.setOnRefreshListener {
            fetchData(idProduct, token, email)
        }

        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        val orderButton = view.findViewById(R.id.order_product) as ImageButton



        orderButton.setOnClickListener {
            //            showCreateCategoryDialog(idProduct,token,email)
            orderDialog()
        }

    }


    fun fetchData(idProduct: Int, token: String, email: String) {
        activity?.let {
            val product = Product(idProduct, token, email)
            Fuel.get(product.endpoint, product.request).responseObject(product.response) { _, _, result ->
                result.fold({ data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            textView.setHtml(data.data, HtmlHttpImageGetter(textView, null, true))
                            if (refresh.isRefreshing) {
                                refresh.isRefreshing = false
                            }
                        }
                    }
                }, { err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(it, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                    if (refresh.isRefreshing) {
                        refresh.isRefreshing = false
                    }
                })
            }
        }
    }

    fun orderDialog() {
        val intent = Intent(context!!, KonfirmasiActivity::class.java)
        intent.putExtra("id", id)
        startActivityForResult(intent, KONFIRMASI_RESULT)
    }

    fun showCreateCategoryDialog(idProduct: Int, token: String, email: String) {
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
            val order = Order(idProduct, token, email, description)
            Fuel.post(order.endpoint, order.request).responseObject(order.response) { _, _, result ->

                result.fold({ data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(context, "Successfully order product", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, { err ->
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
