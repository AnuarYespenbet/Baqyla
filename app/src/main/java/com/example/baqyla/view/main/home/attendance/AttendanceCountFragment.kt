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
        fun newInstance(attendanceCount: Int, absenceCount: Int): AttendanceCountFragment {
            val args = Bundle()
            args.putInt("attendance", attendanceCount)
            args.putInt("absence", absenceCount)

            val fragment = AttendanceCountFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var attendanceCount: Int? = 0
    private var absenceCount: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_attendance_count, container, false)
        attendanceCount = arguments?.getInt("attendance", 0)
        absenceCount = arguments?.getInt("absence", 0)
        val attendanceCountText = view.findViewById<TextView>(R.id.attendance_count_text)
        attendanceCountText.text = attendanceCount.toString()

        val absenceCountText = view.findViewById<TextView>(R.id.absence_count_text)
        absenceCountText.text = absenceCount.toString()
        return view
    }
}