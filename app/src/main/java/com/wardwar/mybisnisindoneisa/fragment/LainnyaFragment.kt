package com.wardwar.mybisnisindoneisa.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.UnautorizeActivity
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper

/**
 * A simple [Fragment] subclass.
 *
 */
class LainnyaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lainnya, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<Button>(R.id.btn_back)
        backButton.visibility = View.GONE

        val preferencesHelper = PreferencesHelper(context!!)
        val navName = view.findViewById(R.id.navName) as TextView

        val namenav = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${namenav}!"

        val version = view.findViewById(R.id.version) as TextView
        val packageInfo = context!!.packageManager.getPackageInfo(context!!.packageName,0)
        version.text = packageInfo.versionName

        val logout = view.findViewById(R.id.logout) as RelativeLayout

        logout.setOnClickListener {
            dialog()
        }

        val editProfile = view.findViewById(R.id.editProfile) as RelativeLayout

        editProfile.setOnClickListener {
            val fragment = EditProfileFragment()
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)
            trans.commit()
        }

        val changePassword = view.findViewById(R.id.changePassword) as RelativeLayout

        changePassword.setOnClickListener {
            val fragment = ChangePasswordFragment()
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.frame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)
            trans.commit()
        }
    }


    fun dialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Anda yakin untuk keluar?")


        // set up the ok button
        builder.setPositiveButton("Ya") { dialog, p1 ->
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()
            val preferencesHelper = PreferencesHelper(context!!)
            preferencesHelper.deviceToken = ""
            preferencesHelper.deviceEmail = ""
            startActivity(Intent(context!!.applicationContext, UnautorizeActivity::class.java))
            activity!!.finishAffinity()
        }

        builder.setNegativeButton("Tidak") { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()
    }


}
