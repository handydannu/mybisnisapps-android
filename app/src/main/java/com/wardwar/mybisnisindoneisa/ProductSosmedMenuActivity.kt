package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_product_sosmed_menu.*
import kotlinx.android.synthetic.main.component_header.*

class ProductSosmedMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_sosmed_menu)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_aktivasi_medsos.setOnClickListener{
            startActivity(Intent(this, AktivasiMediaSosial::class.java))
        }

        btn_opgoogle.setOnClickListener{
            startActivity(Intent(this, OpBrandingGoogle::class.java))
        }

        btn_opyoutube.setOnClickListener{
            startActivity(Intent(this, ActivityKontenYoutubeSosmed::class.java))
        }

        btn_opsosmed.setOnClickListener{
            startActivity(Intent(this, OpBrandingSosmed::class.java))
        }
    }
}
