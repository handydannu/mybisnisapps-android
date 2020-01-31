package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_more_iklan.*
import kotlinx.android.synthetic.main.component_header.*
import kotlinx.android.synthetic.main.more_product.*

class MoreIklanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_iklan)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_permintaan_khusus.setOnClickListener{
            startActivity(Intent(this, ProductPermintaanKhusus::class.java))
        }

        btn_diskon_penjualan.setOnClickListener{
            startActivity(Intent(this, ProductDiskonPenjualan::class.java))
        }

        btn_iklan_umum.setOnClickListener{
            startActivity(Intent(this, ProductIklanUmum::class.java))
        }

        btn_iklan_spesifikasi.setOnClickListener{
            startActivity(Intent(this, ProductIklanSpesifikasi::class.java))
        }

        btn_iklan_khusus.setOnClickListener{
            startActivity(Intent(this, ProductIklanKhusus::class.java))
        }

        btn_bi_weekend.setOnClickListener{
            startActivity(Intent(this, ProductBiWeekend::class.java))
        }

        btn_iklan_paket.setOnClickListener{
            startActivity(Intent(this, ProductIklanPaket::class.java))
        }

        btn_iklan_jaket.setOnClickListener{
            startActivity(Intent(this, ProductIklanJaket::class.java))
        }

        btn_iklan_divider.setOnClickListener{
            startActivity(Intent(this, ProductIklanDivider::class.java))
        }

        btn_iklan_klasifikasi.setOnClickListener{
            startActivity(Intent(this, ProductIklanKlasifikasi::class.java))
        }

    }
}
