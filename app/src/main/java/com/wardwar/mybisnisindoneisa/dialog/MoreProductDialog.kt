package com.wardwar.mybisnisindoneisa.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.ImageButton
import com.wardwar.mybisnisindoneisa.ProductBisnisTv
import com.wardwar.mybisnisindoneisa.ProductRiset
import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.fragment.ContentHtmlFragment

/**
 * Created by wildan on 5/1/18.
 */
class MoreProductDialog : BottomSheetDialogFragment() {

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.more_product, null)
        dialog?.setContentView(contentView)

        val risetMenu = contentView.findViewById<ImageButton>(R.id.product_riset_menu)
        risetMenu.setOnClickListener {
            val fragment = ContentHtmlFragment()
            val bundle = Bundle()

            bundle.putInt("id", 18)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
            dialog!!.dismiss()        }

        val bisnisTvMenu = contentView.findViewById<ImageButton>(R.id.product_tv_menu)
        bisnisTvMenu.setOnClickListener {
            val fragment = ContentHtmlFragment()
            val bundle = Bundle()

            bundle.putInt("id", 11)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
            dialog!!.dismiss()        }

        val percetakanMenu = contentView.findViewById<ImageButton>(R.id.product_percetakan_menu)
        percetakanMenu.setOnClickListener {
            val fragment = ContentHtmlFragment()
            val bundle = Bundle()

            bundle.putInt("id", 17)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
            dialog!!.dismiss()
        }

        val langgananMenu = contentView.findViewById<ImageButton>(R.id.product_langganan_menu)
        langgananMenu.setOnClickListener {
            val fragment = ContentHtmlFragment()
            val bundle = Bundle()

            bundle.putInt("id", 44)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
            dialog!!.dismiss()
        }

        val radioMenu = contentView.findViewById<ImageButton>(R.id.product_radio_menu)
        radioMenu.setOnClickListener {
            val fragment = ContentHtmlFragment()
            val bundle = Bundle()

            bundle.putInt("id", 45)
            fragment.arguments = bundle
            val trans = fragmentManager!!.beginTransaction()
            trans.replace(R.id.greetingFrame, fragment)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()
            dialog!!.dismiss()
        }

    }
}