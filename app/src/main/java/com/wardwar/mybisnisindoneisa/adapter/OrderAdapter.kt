package com.wardwar.mybisnisindoneisa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.DeleteOrder
import com.wardwar.mybisnisindoneisa.network.request.MyOrder
import com.wardwar.mybisnisindoneisa.network.response.ResOrder
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper

class OrderAdapter(private val context: Context, private val dataSource: MutableList<ResOrder>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = inflater.inflate(R.layout.order_item, p2, false)
        val image = view.findViewById(R.id.image) as ImageView
        val text = view.findViewById(R.id.text) as TextView
        val keterangan = view.findViewById(R.id.keterangan) as TextView
        val hapusBtn = view.findViewById(R.id.hapusBtn) as Button

        val order = getItem(p0) as ResOrder
        text.text = order.product_name
        keterangan.text = order.description


        val preferencesHelper = PreferencesHelper(context)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail

        hapusBtn.setOnClickListener {
            // Initialize a new instance of
            val builder = AlertDialog.Builder(context)

            // Set the alert dialog title
            builder.setTitle("Delete Cart?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Delete"){dialog, which ->

                val ordered = DeleteOrder(token, email,order.order_id)

                Fuel.post(ordered.endpoint, ordered.request).responseObject(ordered.response) { _, _, result ->
                    result.fold({ data ->
                        when {
                            data.statusCode > 400 -> Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                            else -> {
                                dataSource.remove(order)
                                notifyDataSetChanged()
                            }
                        }
                    }, { err ->
                        println("REQUEST ERROR ${err.message}")
                        Toast.makeText(context, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                    })
                }
            }

            // Display a negative button on alert dialog
            builder.setNegativeButton("Cancel"){ dialog,which ->
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }
        when (order.product_id) {
            11 -> image.setImageResource(R.drawable.icon_tv)
            10 -> image.setImageResource(R.drawable.icon_buku)
            12 -> image.setImageResource(R.drawable.icon_corporate_brand)
            13 -> image.setImageResource(R.drawable.icon_eo)
            14 -> image.setImageResource(R.drawable.icon_epapers)
            17 -> image.setImageResource(R.drawable.icon_percetakan)
            18 -> image.setImageResource(R.drawable.icon_riset)
            19 -> image.setImageResource(R.drawable.icon_bi_weekend)
            20 -> image.setImageResource(R.drawable.icon_iklan_devider)
            21 -> image.setImageResource(R.drawable.icon_diskon_penjualan)
            26 -> image.setImageResource(R.drawable.icon_iklan_klasifikasi)
            22 -> image.setImageResource(R.drawable.icon_iklan_paket)
            23 -> image.setImageResource(R.drawable.icon_iklan_spesifikasi)
            25 -> image.setImageResource(R.drawable.icon_iklan_jaket)
            24 -> image.setImageResource(R.drawable.icon_iklan_umum)
            27 -> image.setImageResource(R.drawable.icon_permintaan_khusus)
            28 -> image.setImageResource(R.drawable.icon_media_sosial)
            31 -> image.setImageResource(R.drawable.icon_media_sosial)
            29 -> image.setImageResource(R.drawable.icon_media_sosial)
            30 -> image.setImageResource(R.drawable.icon_media_sosial)
            32 -> image.setImageResource(R.drawable.icon_it_solution)
            33 -> image.setImageResource(R.drawable.icon_it_solution)
            34 -> image.setImageResource(R.drawable.icon_it_solution)
            9 -> image.setImageResource(R.drawable.icon_video)
            36 -> image.setImageResource(R.drawable.icon_video)
            37 -> image.setImageResource(R.drawable.icon_video)
            38 -> image.setImageResource(R.drawable.icon_video)
            39 -> image.setImageResource(R.drawable.icon_video)
            40 -> image.setImageResource(R.drawable.icon_video)
            41 -> image.setImageResource(R.drawable.icon_iklan_khusus)
            42 -> image.setImageResource(R.drawable.icon_video)
            43 -> image.setImageResource(R.drawable.icon_iklan)
            44 -> image.setImageResource(R.drawable.icon_langganan)
            45 -> image.setImageResource(R.drawable.icon_radio)
            else -> { // Note the block
                print("produk belum terdaftar")
            }
        }

        return view
    }

    override fun getItem(p0: Int): Any {
        return dataSource.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return dataSource.count()
    }

}