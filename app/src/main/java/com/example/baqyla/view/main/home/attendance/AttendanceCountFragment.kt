package com.example.baqyla.view.main.home.attendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.baqyla.R

class AttendanceCountFragment : Fragment() {

    companion object {
        fun newInstance(count: Int): AttendanceCountFragment {
            val args = Bundle()
            args.putInt("someInt", count)

            val fragment = AttendanceCountFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mCount: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_attendance_count, container, false)
        mCount = arguments?.getInt("someInt", 0)
        val attendanceCountText = view.findViewById<TextView>(R.id.attendance_count_text)
        attendanceCountText.text = mCount.toString()
        return view
    }
}