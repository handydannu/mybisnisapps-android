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
import com.wardwar.mybisnisindoneisa.dialog.MoreProductDialog
import com.wardwar.mybisnisindoneisa.network.request.Iklan
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductFragment : Fragment() {

    lateinit var slider: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById(R.id.btn_back) as Button

        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        slider = view.findViewById(R.id.slider) as ViewPager
        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        menuSetup(view)
    }
    private fun menuSetup(view: View) {


        val video = view.findViewById(R.id.product_video_menu) as ImageButton
        video.setOnClickListener {
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, ProductVideoMenuFragment())
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val it = view.findViewById(R.id.product_it_menu) as ImageButton
        it.setOnClickListener {
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, ProductItMenuFragment())
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val sosmed = view.findViewById(R.id.product_media_sosial_menu) as ImageButton

        sosmed.setOnClickListener {
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, SosmedMenuFragment())
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val iklan = view.findViewById(R.id.product_ads_menu) as ImageButton

        iklan.setOnClickListener {
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, ProductIklanMenuFragment())
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val corp = view.findViewById(R.id.product_corp_menu) as ImageButton

        corp.setOnClickListener {
            //        val trans = fragmentManager!!.beginTransaction()
//        trans.replace(R.id.greetingFrame, ProductCorpBrandingFragment())
//        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//        trans.addToBackStack(null)
//
//        trans.commit()

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 12)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()

        }

//
//    product_epapper_menu.setOnClickListener {
//        startActivity(Intent(applicationContext, ProductEpappers::class.java))
//    }

        val buku = view.findViewById(R.id.product_buku_menu) as ImageButton

        buku.setOnClickListener {
            //        val trans = fragmentManager!!.beginTransaction()
//        trans.replace(R.id.greetingFrame, BukuFragment())
//        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//        trans.addToBackStack(null)
//
//        trans.commit()

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 10)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val eo = view.findViewById(R.id.product_eo_menu) as ImageButton
        eo.setOnClickListener {
            //        val trans = fragmentManager!!.beginTransaction()
//        trans.replace(R.id.greetingFrame, ProductEOFragment())
//        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//        trans.addToBackStack(null)
//
//        trans.commit()
            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 13)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }


        val epeaper = view.findViewById(R.id.product_epapper_menu) as ImageButton
        epeaper.setOnClickListener {

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 14)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
//
        }
//
        val lain = view.findViewById(R.id.product_lain_menu) as ImageButton
        lain.setOnClickListener {
            val bottomSheetDialogFragment = MoreProductDialog()
            bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.getTag())
        }

        getAdvert()

    }

    fun getAdvert() {
        activity?.let { activity ->
            val preferencesHelper = PreferencesHelper(activity)
            val token = preferencesHelper.deviceToken
            val email = preferencesHelper.deviceEmail

            val order = Iklan(token, email)

            Fuel.get(order.endpoint).responseObject(order.response) { _, _, result ->
                result.fold({ data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(activity, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            println("IMAGE ADVERT ${data.data}")
                            slider.adapter = SlidingImage_Adapter(activity, data.data)
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
