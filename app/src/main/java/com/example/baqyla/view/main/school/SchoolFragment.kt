package com.example.baqyla.view.main.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.baqyla.R
import com.example.baqyla.data.model.SubjectInfo
import kotlinx.android.synthetic.main.fragment_school.*
import kotlinx.android.synthetic.main.layout_progress.*

class SchoolFragment : Fragment() {
    private var subjectInfoAdapter: SubjectInfoAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_school, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val percentText = "90%"
        attendance_percent.text = percentText
        attendance_text.text = getString(R.string.attendance_percent_students)
        progress_bar.progress = 90

        setSubjectsInfo()
    }

    private fun setSubjectsInfo() {
        val subjectsInfo = ArrayList<SubjectInfo>()
        subjectsInfo.add(SubjectInfo(119, "Программирование"))
        subjectsInfo.add(SubjectInfo(67, "Дзюдо"))
        subjectsInfo.add(SubjectInfo(109, "Шахмат"))
        subjectInfoAdapter = SubjectInfoAdapter(subjectsInfo)
        subjects_info_rv.adapter = subjectInfoAdapter
    }
}