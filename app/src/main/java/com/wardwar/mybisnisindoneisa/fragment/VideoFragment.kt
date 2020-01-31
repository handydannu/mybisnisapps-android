package com.wardwar.mybisnisindoneisa.fragment


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.squareup.picasso.Picasso

import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.network.request.Video
import com.wardwar.mybisnisindoneisa.network.response.ResVideo
import com.wardwar.mybisnisindoneisa.utils.PreferencesHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class VideoFragment : Fragment() {
    var list: ListView? = null
    var adapter: MyCustomAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backButton = view.findViewById(R.id.btn_back) as Button
        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        val preferencesHelper = PreferencesHelper(activity!!.applicationContext)

        val navName = view.findViewById<TextView>(R.id.navName)
        val name = preferencesHelper.deviceName.split(" ")[0]
        navName.text = "Hello ${name}!"


        list = view.findViewById(R.id.video_list_view) as ListView
        val bundle = arguments
        val id = bundle!!.getInt("id", 0)
        fetchData(id)

    }

    private fun fetchData(id: Int) {

        val preferencesHelper = PreferencesHelper(context!!)
        val token = preferencesHelper.deviceToken
        val email = preferencesHelper.deviceEmail

        val video = Video(id, token, email)

        Fuel.get(video.endpoint).responseObject(video.response) { _, _, result ->
            result.fold({ data ->
                when {
                    data.statusCode > 400 -> Toast.makeText(context!!.applicationContext, data.message, Toast.LENGTH_SHORT).show()
                    else -> {
                        adapter = MyCustomAdapter(context!!, data.data)
                        list!!.adapter = adapter
                    }
                }
            }, { err ->
                println("REQUEST ERROR ${err.message}")
                Toast.makeText(context!!.applicationContext, "Can't connect to internet", Toast.LENGTH_SHORT).show()
            })
        }
    }

    class MyCustomAdapter(context: Context, private val dataSource: Array<ResVideo>) : BaseAdapter() {

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
