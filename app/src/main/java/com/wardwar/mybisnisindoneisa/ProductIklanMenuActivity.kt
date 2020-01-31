package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_product_iklan_menu.*
import kotlinx.android.synthetic.main.component_header.*

class ProductIklanMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_iklan_menu)

        btn_ikcet.setOnClickListener {
            startActivity(Intent(applicationContext,MoreIklanActivity::class.java))
        }

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }
}
