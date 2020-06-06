package com.example.baqyla.view.on_boarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baqyla.R
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreBooleanType
import com.example.baqyla.view.main.navigation.MainActivity
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        setupViewPager()
        on_boarding_button_hide.setOnClickListener {
            navigateToMain()
        }
        on_boarding_button_next.setOnClickListener {
            if (on_boarding_view_pager.currentItem < OnBoardingPagerAdapter.NUM_PAGES - 1)
                on_boarding_view_pager.currentItem = on_boarding_view_pager.currentItem + 1
            else {
                navigateToMain()
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        LocalStore().save(true, LocalStoreBooleanType.ON_BOARDING_COMPLETED)
        finish()
    }

    private fun setupViewPager() {
        on_boarding_view_pager.adapter = OnBoardingPagerAdapter(this)
        on_boarding_dots.setViewPager2(on_boarding_view_pager)
    }
}