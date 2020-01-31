package com.wardwar.mybisnisindoneisa

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_video_content.*
import android.content.Intent
import android.content.UriPermission
import android.net.Uri
import kotlinx.android.synthetic.main.component_header.*
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.squareup.picasso.Picasso
import com.wardwar.mybisnisindoneisa.network.request.Video
import com.wardwar.mybisnisindoneisa.network.response.ResVideo
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper


class VideoContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_content)

//        btn_back.setOnClickListener {
//            onBackPressed()
//        }

        val preferencesHelper = PreferencesHelper(this)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail
        val idProduct = 9

        val video = Video(idProduct, token, email)
        Fuel.get(video.endpoint, video.request).responseObject(video.response) { _, _, result ->
            result.fold({data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        println("ResponseData ${data.data}")
                        val listView = findViewById<ListView>(R.id.video_list_view)
                        listView.adapter = MyCustomAdapter(this, data.data)
                    }
                }
            }, {
                err ->
                println("REQUEST ERROR ${err.message}")
                Toast.makeText(applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
            })
        }

    }

    private class MyCustomAdapter(context: Context, private val dataSource: Array<ResVideo>): BaseAdapter() {

        private val mContext: Context
        private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        init {
            mContext = context
        }

        override fun getCount(): Int {
            return dataSource.count()
        }

        override fun getItemId(possision: Int): Long {
            return possision.toLong()
        }

        override fun getItem(possision: Int): Any {
            return dataSource[possision]
        }

        @SuppressLint("ViewHolder")
        override fun getView(possision: Int, convertView: View?, viewGroup: ViewGroup?): View {

            val view = inflater.inflate(R.layout.video_item, viewGroup, false)
            val image = view.findViewById(R.id.vid1) as ImageView

            val video = getItem(possision) as ResVideo
            println("data ==> $video")
            image.setOnClickListener {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=0KEp8RUwRKs", mContext)))
                mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(video.video_url)))

            }

            Picasso.get().load(video.thumbnail_url).into(image)

            return view
        }
    }

}
