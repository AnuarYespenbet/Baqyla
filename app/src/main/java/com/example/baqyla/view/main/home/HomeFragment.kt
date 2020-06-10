package com.example.baqyla.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.baqyla.R
import com.example.baqyla.data.model.Child
import com.example.baqyla.data.model.Statistic
import com.example.baqyla.data.model.Subject
import com.example.baqyla.utils.Status
import com.example.baqyla.utils.birthdayFromArrayToString
import com.example.baqyla.utils.loadUrl
import com.example.baqyla.utils.toast
import com.example.baqyla.view.main.home.attendance.AttendanceCountFragment
import com.example.baqyla.view.main.home.attendance.AttendanceCountPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_progress.*
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var child: Child? = null
    private var subjects: List<Subject>? = null
    private var adapter: AttendanceCountPagerAdapter? = null
    private var statistic: Statistic? = null

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
        child = homeViewModel.child
        val currentMonth = YearMonth.now()

        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val firstDayOfMonth = currentMonth.atDay(1).atStartOfDay()
        val lastDayOfMonth = currentMonth.atEndOfMonth().atStartOfDay()
        val dateFrom = firstDayOfMonth.format(dateFormat)
        val dateTo = lastDayOfMonth.format(dateFormat)

        homeViewModel.getStatistics(childId = child!!.id!!, dateFrom = dateFrom, dateTo = dateTo)
            .observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        Timber.d("loading")
                    }
                    Status.ERROR -> {
                        requireActivity().toast(it.error ?: "")
                        Timber.e(it.error)
                    }
                    Status.SUCCESS -> {
                        onSuccess(it.data)
                    }
                }
            })
        setChildDetails()
    }

    private fun onSuccess(statistic: Statistic?) {
        this.statistic = statistic
        val come = statistic?.come?.toDouble() ?: 0.0
        val dontCome = statistic?.dontCome?.toDouble() ?: 0.0
        val attendancePercent = ((come / (come + dontCome)) * 100).toInt()
        val attendancePercentText = "$attendancePercent%"
        attendance_percent.text = attendancePercentText
        attendance_text.text = getString(R.string.attendance_percent)
        progress_bar.progress = attendancePercent
        setTabLayout()
    }

    private fun setChildDetails() {

        child?.apply {
            val fullName = "$name $surname"
            name_text.text = fullName
            image.loadUrl(profilePhoto)
            birthday_text.text = birthday?.let { birthdayFromArrayToString(it) }
            phone_text.text = phone
            address_text.text = address
        }
    }

    private fun setTabLayout() {
        setAdapter()
        setIndicator()
        onTabSelected()
        setSubjects()
        setViewPager()
    }

    private fun setAdapter() {
        adapter = AttendanceCountPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
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
        subjects = child?.subjects
        subjects?.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it.name))
            adapter?.addFragment(
                AttendanceCountFragment.newInstance(
                    statistic?.come ?: 0, statistic?.dontCome ?: 0
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