package com.example.baqyla.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.ViewModelProvider
import com.example.baqyla.R
import com.example.baqyla.data.model.Child
import com.example.baqyla.data.model.Subject
import com.example.baqyla.data.model.User
import com.example.baqyla.utils.birthdayFromArrayToString
import com.example.baqyla.view.main.home.attendance.AttendanceCountFragment
import com.example.baqyla.view.main.home.attendance.AttendanceCountPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var user: User? = null
    private var child: Child? = null
    private var subjects: List<Subject>? = null
    private var adapter: AttendanceCountPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setObjects()
        child?.apply {
            val fullName = "$name $surname"
            name_text.text = fullName
            //image.loadUrl(profilePhoto)
            birthday_text.text = birthday?.let { birthdayFromArrayToString(it) }
            phone_text.text = phone
            address_text.text = address
        }
        setTabLayout()
    }

    private fun setObjects() {
        user = homeViewModel.user
        child = user?.children?.get(0)
        subjects = child?.subjects
    }

    private fun setTabLayout() {
        setAdapter()
        setIndicator()
        onTabSelected()
        setSubjects()
        setViewPager()
    }

    private fun setAdapter() {
        adapter =
            AttendanceCountPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    }

    private fun setIndicator() {
        tab_layout.setSelectedTabIndicator(R.drawable.bg_indicator)
    }

    private fun onTabSelected() {
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager.currentItem = tab?.position ?: 0
            }
        })
    }

    private fun setSubjects() {
        subjects?.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it.name))
            adapter?.addFragment(
                AttendanceCountFragment.newInstance(
                    it.attendanceCount, it.absenceCount
                )
            )
        }
    }

    private fun setViewPager() {
        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                tab_layout
            )
        )
    }
}