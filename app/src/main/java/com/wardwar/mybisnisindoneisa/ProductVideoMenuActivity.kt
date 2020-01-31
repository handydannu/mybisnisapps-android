package com.wardwar.mybisnisindoneisa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_product_video_menu.*
import kotlinx.android.synthetic.main.component_header.*

class ProductVideoMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_video_menu)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_presentasi.setOnClickListener{
            startActivity(Intent(this,VideoContentActivity::class.java))
        }
    }
}
