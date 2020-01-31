package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.adapter.SlidingImage_Adapter
import com.wardwar.mybisnisindoneisa.network.request.Iklan
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductIklanMenuFragment : Fragment() {

    lateinit var slider:ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_product_iklan_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById(R.id.btn_back) as Button

        slider = view.findViewById(R.id.slider) as ViewPager

        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val cetak = view.findViewById(R.id.btn_ikcet) as Button

        cetak.setOnClickListener {
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, IklanCetakFragment())
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val web = view.findViewById(R.id.btn_ikweb) as Button

        web.setOnClickListener {
            val fragment = ContentHtmlFragment()
            val bundle = Bundle()

            bundle.putInt("id", 43)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)
            trans.commit()
        }

        getAdvert()
    }

    fun getAdvert() {
        activity?.let {
            activity ->
            val preferencesHelper = PreferencesHelper(activity)
            val token = preferencesHelper.deviceToken
            val email = preferencesHelper.deviceEmail

            val order = Iklan(token, email)

            Fuel.get(order.endpoint).responseObject(order.response) { _, _, result ->
                result.fold({ data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context!!.applicationContext, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            println("IMAGE ADVERT ${data.data}")
                            slider.adapter = SlidingImage_Adapter(context!!, data.data)
                        }
                    }
                }, { err ->
                    println("REQUEST ERROR ${err.message}")

                    Toast.makeText(activity, "Can't connect to internet", Toast.LENGTH_SHORT).show()

                })
            }
        }

    }



}
