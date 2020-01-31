package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.Product
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView

/**
 * A simple [Fragment] subclass.
 *
 */
class SolusiMedaTerpaduFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solusi_meda_terpadu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById(R.id.btn_back) as Button
        var smtText = view.findViewById(R.id.smt_text_view) as HtmlTextView

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(context!!.applicationContext)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = 15

        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        val product = Product(idProduct, token, email)
        Fuel.get(product.endpoint, product.request).responseObject(product.response) { _, _, result ->
            result.fold({ data ->
                when {
                    data.statusCode > 400 ->
                        activity?.let {
                            Toast.makeText(it, data.message, Toast.LENGTH_SHORT).show()
                        }
                    else -> {
                        smtText.setHtml(data.data, HtmlHttpImageGetter(smtText, null, true))


                    }
                }
            }, { err ->
                println("REQUEST ERROR ${err.message}")
                activity?.let {
                    Toast.makeText(it, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

}
