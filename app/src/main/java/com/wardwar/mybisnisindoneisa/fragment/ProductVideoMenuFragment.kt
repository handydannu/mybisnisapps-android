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
class ProductVideoMenuFragment : Fragment() {

    lateinit var slider: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_video_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"

        slider = view.findViewById(R.id.slider) as ViewPager


//        val fragment = ContentHtmlFragment()
        val backButton = view.findViewById(R.id.btn_back) as Button

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val videoPresentasiButton = view.findViewById<Button>(R.id.btn_presentasi)
        videoPresentasiButton.setOnClickListener {

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 42)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val videoDokumentasiButton = view.findViewById<Button>(R.id.btn_dokumentasi)
        videoDokumentasiButton.setOnClickListener {

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 36)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val videoSocialisasiButton = view.findViewById<Button>(R.id.btn_sosialisasi)
        videoSocialisasiButton.setOnClickListener {

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 37)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val videoCompanyButton = view.findViewById<Button>(R.id.btn_company)
        videoCompanyButton.setOnClickListener {

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 38)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val videoAdsButton = view.findViewById<Button>(R.id.btn_video_ads)
        videoAdsButton.setOnClickListener {

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 39)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
        }

        val videoShortMovieButton = view.findViewById<Button>(R.id.btn_short_movie)
        videoShortMovieButton.setOnClickListener {

            val fragment = ContentHtmlFragment()
            val bundle = Bundle()
            bundle.putInt("id", 40)
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
        activity?.let { activity ->
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
