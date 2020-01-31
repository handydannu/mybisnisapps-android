package com.wardwar.mybisnisindoneisa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.component_header.*

class ProductItMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_it_menu)

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }
}
