package com.wardwar.mybisnisindoneisa.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.ChangePassword
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ChangePasswordFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val password = view.findViewById<EditText>(R.id.register_password_form)
        val retypePassword = view.findViewById<EditText>(R.id.register_retype_password_form)
        val changePasswordButton = view.findViewById<Button>(R.id.change_password_btn)

        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail

        val backButton = view.findViewById(R.id.btn_back) as Button
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val navName = view.findViewById<TextView>(R.id.navName)
        val firstName = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${firstName}!"


        changePasswordButton.setOnClickListener {

            if (password.text.toString() != retypePassword.text.toString()) {
                Toast.makeText(context!!.applicationContext, "Password not same.", Toast.LENGTH_SHORT).show()
            }

            val changePassword = ChangePassword(password.text.toString(), retypePassword.text.toString(), token, email)

            Fuel.post(changePassword.endpoint, changePassword.request).responseObject(changePassword.response) { _, _, result ->
                result.fold({data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(context!!.applicationContext, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            println(data.data)
                            Toast.makeText(context!!.applicationContext, "Success change password.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(context!!.applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                })
            }

        }
    }


}
