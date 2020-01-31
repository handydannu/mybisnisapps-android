package com.wardwar.mybisnisindoneisa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.wardwar.mybisnisindoneisa.network.request.Order
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_konfirmasi.*
import kotlinx.android.synthetic.main.component_header.*

class KonfirmasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(this)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct:Int = intent.getIntExtra("id",0)

        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        when (idProduct) {
            11 -> {
                image.setImageResource(R.drawable.icon_tv)
                text.text = "BISNIS TV"
            }
            10 -> {
                image.setImageResource(R.drawable.icon_buku)
                text.text = "BOOK & MAGZ PUBLISHING"
            }
            12 -> {
                image.setImageResource(R.drawable.icon_corporate_brand)
                text.text = "CORPORATE BRANDING"
            }
            13 -> {
                image.setImageResource(R.drawable.icon_eo)
                text.text = "EVENT ORGANIZER"
            }
            14 -> {
                image.setImageResource(R.drawable.icon_epapers)
                text.text = "E-PAPER"
            }
            17 -> {
                image.setImageResource(R.drawable.icon_percetakan)
                text.text = "PERCETAKAN"
            }
            18 -> {
                image.setImageResource(R.drawable.icon_riset)
                text.text = "RISET"
            }
            19 -> {
                image.setImageResource(R.drawable.icon_bi_weekend)
                text.text = "BISNIS INDONESIA WEEKEND"
            }
            20 -> {
                image.setImageResource(R.drawable.icon_iklan_devider)
                text.text = "IKLAN DIVIDER"
            }
            21 -> {
                image.setImageResource(R.drawable.icon_diskon_penjualan)
                text.text = "DISCOUNT PENJUALAN"
            }
            26 -> {
                image.setImageResource(R.drawable.icon_iklan_klasifikasi)
                text.text = "IKLAN KLASIFIKASI"
            }
            22 -> {
                image.setImageResource(R.drawable.icon_iklan_paket)
                text.text = "IKLAN PAKET"
            }
            23 -> {
                image.setImageResource(R.drawable.icon_iklan_spesifikasi)
                text.text = "IKLAN SPESIFIKASI"
            }
            25 -> {
                image.setImageResource(R.drawable.icon_iklan_jaket)
                text.text = "IKLAN JAKET"
            }
            24 -> {
                image.setImageResource(R.drawable.icon_iklan_umum)
                text.text = "IKLAN UMUM"
            }
            27 -> {
                image.setImageResource(R.drawable.icon_permintaan_khusus)
                text.text = "PERMINTAAN KHUSUS"
            }
            28 -> {
                image.setImageResource(R.drawable.icon_media_sosial)
                text.text = "AKTIVASI SOSIAL MEDIA"
            }
            31 -> {
                image.setImageResource(R.drawable.icon_media_sosial)
                text.text = "OPTIMALISASI BRAND DI MEDSOS"
            }
            29 -> {
                image.setImageResource(R.drawable.icon_media_sosial)
                text.text = "AKTIVASI YOUTUBE DAN MEDSOS"
            }
            30 -> {
                image.setImageResource(R.drawable.icon_media_sosial)
                text.text = "OPTIMALISASI BRAND DI SOSMED"
            }
            32 -> {
                image.setImageResource(R.drawable.icon_it_solution)
                text.text = "WEB DESIGN"
            }
            33 -> {
                image.setImageResource(R.drawable.icon_it_solution)
                text.text = "WEB DEVELOPMENT"
            }
            34 -> {
                image.setImageResource(R.drawable.icon_it_solution)
                text.text = "WEB MAINTENANCE"
            }
            9 -> {
                image.setImageResource(R.drawable.icon_video)
                text.text = "VIDEO PERSENTASI"
            }
            36 -> {
                image.setImageResource(R.drawable.icon_video)
                text.text = "VIDEO DOKUMENTASI"
            }
            37 -> {
                image.setImageResource(R.drawable.icon_video)
                text.text = "VIDEO KAMPANYE"

            }
            38 -> {
                image.setImageResource(R.drawable.icon_video)
                text.text = "VIDEO PROFIL PERUSAHAAN"

            }
            39 -> {
                image.setImageResource(R.drawable.icon_video)
                text.text = "VIDEO KOMERSIAL"

            }
            40 -> {
                image.setImageResource(R.drawable.icon_video)
                text.text = "VIDEO SHORT MOVIE"

            }
            41 -> {
                image.setImageResource(R.drawable.icon_iklan_khusus)
                text.text = "IKLAN KHUSUS"

            }
            42 -> {
                image.setImageResource(R.drawable.icon_video)
                text.text = "VIDEO PRESENTASI"
            }
            43 -> {
                image.setImageResource(R.drawable.icon_iklan)
                text.text = "IKLAN WEBSITE"
            }
            44 -> {
                image.setImageResource(R.drawable.icon_langganan)
                text.text = "LANGGANAN"
            }
            45 -> {
                image.setImageResource(R.drawable.icon_radio)
                text.text = "RADIO"
            }
            else -> { // Note the block
                print("produk belum terdaftar")
            }
        }


        konfirmasi_btn.setOnClickListener {
            val description = keterangan.text.toString()
            val order = Order(idProduct, token, email,description)
            Fuel.post(order.endpoint, order.request).responseObject(order.response) { _, _, result ->

                result.fold({data ->
                    when {
                        data.statusCode > 400 -> Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(this, "Rekues Order Produk Sukses, Kami akan Follow Up maksimal 3x hari kerja", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                }, {
                    err ->
                    println("REQUEST ERROR ${err.message}")
                    Toast.makeText(this, "Can't connect to internet", Toast.LENGTH_SHORT).show()
                })

            }
        }
    }
}
