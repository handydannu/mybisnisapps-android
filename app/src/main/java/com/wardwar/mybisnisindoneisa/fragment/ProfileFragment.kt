package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.squareup.picasso.Picasso

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.Profile
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper


/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencesHelper = PreferencesHelper(context!!)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val profile = Profile(token, email)


        val name = view.findViewById(R.id.profile_name) as TextView
        val company = view.findViewById(R.id.profile_company) as TextView
        val emailUser = view.findViewById(R.id.profile_email) as TextView
        val address = view.findViewById(R.id.profile_address) as TextView
        val phone = view.findViewById(R.id.profile_phone) as TextView
        val image = view.findViewById(R.id.profile_pic) as ImageView

        Fuel.get(profile.endpoint, profile.request).responseObject(profile.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(context!!.applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        println(data.data)
                        name.text = data.data.name
                        company.text = data.data.company
                        emailUser.text = data.data.email
                        address.text = data.data.address
                        phone.text = data.data.phone
//                        Picasso.get().load("${Constats.BASE_IMAGE}${data.data.picture}").placeholder(R.drawable.user_profile).into(image)
                        Picasso.get().load( "${data.data.picture}?type=normal").placeholder(R.drawable.user_profile).into(image)
                    }
                }
            }, {
                err ->
                println("REQUEST ERROR ${err.message}")
                activity?.let {
                    Toast.makeText(it, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }



}
