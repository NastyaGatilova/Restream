package com.example.restream

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Home()
            1 -> return Broadcasts()
            2 -> return MyTariff()
            else -> return Home()

        }
    }
}