package com.example.baqyla.view.main.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.baqyla.R
import com.example.baqyla.data.model.School
import com.example.baqyla.data.model.SchoolDetail
import com.example.baqyla.data.model.SchoolSubject
import com.example.baqyla.utils.Status
import com.example.baqyla.utils.invisible
import com.example.baqyla.utils.toast
import com.example.baqyla.utils.visible
import kotlinx.android.synthetic.main.fragment_school.*
import kotlinx.android.synthetic.main.layout_progress.*

class SchoolFragment : Fragment() {
    private lateinit var schoolViewModel: SchoolViewModel
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
        schoolViewModel = ViewModelProvider(this).get(SchoolViewModel::class.java)
        getSchool()
    }

    private fun getSchool() {
        schoolViewModel.getSchool().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { school -> onSuccess(school) }
                }
                Status.ERROR -> {
                    onError(it.error ?: "error")
                }
                Status.LOADING -> {
                    onLoading()
                }
            }
        })
    }

    private fun onSuccess(school: School) {
        hideProgress()
        setSchoolDetail(school.detail)
        setSchoolSubjects(school.subjects)
    }

    private fun setSchoolDetail(schoolDetail: SchoolDetail) {
        schoolDetail.apply {
            school_name.text = name
            school_phone.text = phone
            school_address.text = address
            school_email.text = mail
        }
        val percentText = "90%"
        attendance_percent.text = percentText
        attendance_text.text = getString(R.string.attendance_percent_students)
        progress_bar.progress = 90
    }

    private fun setSchoolSubjects(schoolSubjects: List<SchoolSubject>) {
        subjectInfoAdapter = SubjectInfoAdapter(schoolSubjects)
        subjects_info_rv.adapter = subjectInfoAdapter
    }

    private fun onError(message: String) {
        hideProgress()
        requireContext().toast(message)
    }

    private fun onLoading() {
        showProgress()
    }

    private fun showProgress() {
        school_info_progress.visible()
        school_info_container.invisible()
    }

    private fun hideProgress() {
        school_info_progress.invisible()
        school_info_container.visible()
    }
}