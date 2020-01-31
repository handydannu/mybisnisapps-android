package com.wardwar.mybisnisindoneisa.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wardwar.mybisnisindoneisa.R
import com.wardwar.mybisnisindoneisa.models.PhotoSlide
import android.support.v4.view.PagerAdapter
import com.wardwar.mybisnisindoneisa.utils.Constats

class SlidingImage_Adapter(private val context: Context, private val imageModelArrayList: MutableList<String>) : PagerAdapter() {
    private val inflater: LayoutInflater


    init {
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.single_image_page, view, false)!!

        val imageView = imageLayout
                .findViewById(R.id.imageView) as ImageView


        Picasso.get().load(imageModelArrayList[position]).placeholder(R.drawable.user_profile).into(imageView)

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

}