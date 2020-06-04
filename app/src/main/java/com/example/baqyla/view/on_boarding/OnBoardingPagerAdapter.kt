package com.example.baqyla.view.on_boarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class OnBoardingPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    companion object {
        const val NUM_PAGES = 3
    }
    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                OnBoardingFirstFragment()
            }
            1 -> {
                OnBoardingSecondFragment()
            }
            else -> {
                OnBoardingThirdFragment()
            }
        }
    }
}