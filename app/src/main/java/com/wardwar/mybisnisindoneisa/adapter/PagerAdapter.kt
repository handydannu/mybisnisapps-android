package com.wardwar.mybisnisindoneisa.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wardwar.mybisnisindoneisa.fragment.GreetingFragment
import com.wardwar.mybisnisindoneisa.fragment.LainnyaFragment
import com.wardwar.mybisnisindoneisa.fragment.OrderFragment
import com.wardwar.mybisnisindoneisa.fragment.ProfileFragment

open class PagerAdapter(private val fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        return when (position) {
            0 -> GreetingFragment()
            1 ->  ProfileFragment()
            2 ->  OrderFragment()
            else -> {
                return LainnyaFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }


}