package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wardwar.mybisnisindoneisa.dialog.MoreProductDialog
import kotlinx.android.synthetic.main.activity_product_menu.*
import kotlinx.android.synthetic.main.component_header.*


class ProductMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_menu)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        menuSetup()
    }

    private fun menuSetup() {
        product_video_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductVideoMenuActivity::class.java))
        }

        product_it_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductItMenuActivity::class.java))
        }

        product_media_sosial_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductSosmedMenuActivity::class.java))
        }

        product_ads_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductIklanMenuActivity::class.java))
        }

        product_corp_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductCorpBanding::class.java))
        }

        product_epapper_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductEpappers::class.java))
        }

        product_buku_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductBuku::class.java))
        }

        product_eo_menu.setOnClickListener {
            startActivity(Intent(applicationContext, ProductEventOrganizer::class.java))
        }

        product_lain_menu.setOnClickListener {
            val bottomSheetDialogFragment = MoreProductDialog()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.getTag())
        }

    }
}
