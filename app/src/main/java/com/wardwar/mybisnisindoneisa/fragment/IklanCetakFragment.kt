package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
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
class IklanCetakFragment : Fragment() {

    lateinit var slider: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iklan_cetak, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById(R.id.btn_back) as Button
        val fragment = ContentHtmlFragment()
        val bundle = Bundle()

        slider = view.findViewById(R.id.slider) as ViewPager
        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val permintaanKhusus = view.findViewById(R.id.btn_permintaan_khusus) as ImageButton

        permintaanKhusus.setOnClickListener {
            bundle.putInt("id",27)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val diskon = view.findViewById(R.id.btn_diskon_penjualan) as ImageButton

        diskon.setOnClickListener {
            bundle.putInt("id",21)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val umum = view.findViewById(R.id.btn_iklan_umum) as ImageButton

        umum.setOnClickListener {
            bundle.putInt("id",24)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val spesifikasi = view.findViewById(R.id.btn_iklan_spesifikasi) as ImageButton

        spesifikasi.setOnClickListener {
            bundle.putInt("id",23)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val khusus = view.findViewById(R.id.btn_iklan_khusus) as ImageButton

        khusus.setOnClickListener {
            bundle.putInt("id",41)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val weekend = view.findViewById(R.id.btn_bi_weekend) as ImageButton

        weekend.setOnClickListener {
            bundle.putInt("id",19)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val paket = view.findViewById(R.id.btn_iklan_paket) as ImageButton

        paket.setOnClickListener {
            bundle.putInt("id",22)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val jaket = view.findViewById(R.id.btn_iklan_jaket) as ImageButton

        jaket.setOnClickListener {
            bundle.putInt("id",25)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val divider = view.findViewById(R.id.btn_iklan_divider) as ImageButton

        divider.setOnClickListener {
            bundle.putInt("id",20)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val klasifikasi = view.findViewById(R.id.btn_iklan_klasifikasi) as ImageButton

        klasifikasi.setOnClickListener {
            bundle.putInt("id",26)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
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
