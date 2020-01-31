package com.wardwar.mybisnisindoneisa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.models.Kontak
import android.widget.LinearLayout



class KontakAdapter(private val context: Context,private val dataSource: ArrayList<Kontak>):BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_kontak, p2, false)

        val kontak = getItem(p0) as Kontak
        val judul = rowView.findViewById(R.id.judul) as TextView
        val description = rowView.findViewById(R.id.description) as TextView

        judul.text = kontak.judul
        description.text = kontak.description


        return rowView
    }

}