package com.nackep.wheatherapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nackep.wheatherapp.fragment.ForteenDaysFragment
import com.nackep.wheatherapp.fragment.TodayFragment
import com.nackep.wheatherapp.fragment.TomorrowFragment

class VPAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            TodayFragment()
        } else if (position == 1) {
            TomorrowFragment()
        } else {
            ForteenDaysFragment()
        }
    }
}