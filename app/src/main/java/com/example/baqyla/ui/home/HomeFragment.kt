package com.example.baqyla.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.example.baqyla.R
import com.example.baqyla.data.models.Subject
import com.example.baqyla.data.models.User
import com.example.baqyla.ui.attendance.AttendanceCountFragment
import com.example.baqyla.ui.attendance.AttendanceCountPagerAdapter
import com.example.baqyla.utils.Constants
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var subjects: List<Subject>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.intent?.let {
            if (it.hasExtra(Constants.USER)) {
                val user = it.getSerializableExtra(Constants.USER) as User

                if (!user.children.isNullOrEmpty()) {
                    val child = user.children[0]
                    name.text = child.name

                    /*Glide.with(context!!)
                        .load(child.profilePhoto)
                        .centerCrop()
                        .into(image)*/

                    subjects = child.subjects as ArrayList<Subject>
                }

            }
        }

        val tabs = tab_layout
        tabs.setSelectedTabIndicator(R.drawable.bg_indicator)
        val adapter =
            AttendanceCountPagerAdapter(
                childFragmentManager,
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager.currentItem = tab?.position ?: 0
            }

        })

        subjects?.forEach {
            tabs.addTab(tabs.newTab().setText(it.name))
            adapter.addFragment(
                AttendanceCountFragment.newInstance(
                    it.count
                )
            )
        }

        view_pager.adapter = adapter
        view_pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                tab_layout
            )
        )

    }
}