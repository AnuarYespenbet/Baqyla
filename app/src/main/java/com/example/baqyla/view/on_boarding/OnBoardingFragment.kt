package com.example.baqyla.view.on_boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.baqyla.R
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreBooleanType
import kotlinx.android.synthetic.main.fragment_on_boarding.*

class OnBoardingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_on_boarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        on_boarding_button_hide.setOnClickListener {
            navigateToSplash()
        }
        on_boarding_button_next.setOnClickListener {
            if (on_boarding_view_pager.currentItem < OnBoardingPagerAdapter.NUM_PAGES - 1)
                on_boarding_view_pager.currentItem = on_boarding_view_pager.currentItem + 1
            else {
                navigateToSplash()
            }
        }
    }

    private fun navigateToSplash() {
        LocalStore().save(true, LocalStoreBooleanType.ON_BOARDING_COMPLETED)
        findNavController().navigate(R.id.action_onBoardingFragment_to_splashFragment)
    }

    private fun setupViewPager() {
        on_boarding_view_pager.adapter = OnBoardingPagerAdapter(requireActivity())
        on_boarding_view_pager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position % 2 == 0) setLight()
                else setDark()
            }
        })
        on_boarding_dots.setViewPager2(on_boarding_view_pager)
    }

    private fun setLight() {
        on_boarding_button_hide.setStrokeColorResource(R.color.light_green)
        on_boarding_button_hide
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.light_green))
        on_boarding_button_next
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_green))
        on_boarding_button_next
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        on_boarding_text_view_copyright
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun setDark() {
        on_boarding_button_hide
            .setStrokeColorResource(R.color.white)
        on_boarding_button_hide
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        on_boarding_button_next
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        on_boarding_button_next
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.second_blue))
        on_boarding_text_view_copyright
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }
}