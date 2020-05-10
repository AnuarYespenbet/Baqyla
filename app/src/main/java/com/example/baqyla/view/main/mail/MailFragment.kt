package com.example.baqyla.view.main.mail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.baqyla.R
import com.example.baqyla.data.model.*
import com.example.baqyla.utils.Status
import kotlinx.android.synthetic.main.fragment_mail.*
import kotlinx.android.synthetic.main.layout_child.*
import timber.log.Timber

class MailFragment : Fragment() {
    private lateinit var mailViewModel: MailViewModel
    private var user: User? = null
    private var child: Child? = null

    private var subjects: List<Subject>? = null
    private var selectedSubjectId = 0

    private var lessons: List<Lesson>? = null
    private var selectedLessonId = 0

    private var reasons: List<Reason>? = null
    private var selectedReasonId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mailViewModel = ViewModelProvider(this).get(MailViewModel::class.java)
        user = mailViewModel.user
        child = user?.children?.get(0)

        child?.apply {
            val fullName = "$name $surname"
            child_name.text = fullName
        }

        loadSubjects()
        loadReasons()

    }

    private fun loadSubjects() {
        mailViewModel.getSubjects().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> { onSubjectsSuccess(it.data) }
                Status.ERROR -> { Timber.e(it.error) }
                Status.LOADING -> { Timber.d("subjects loading") }
            }
        })
    }

    private fun onSubjectsSuccess(data: List<Subject>?) {
        subjects = data
        val subjectNameArray = subjects?.map { it.name }?.toMutableList() ?: mutableListOf()
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            subjectNameArray
        )
        subject_spinner.adapter = arrayAdapter
        subject_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, arg3: Long) {
                selectedSubjectId = subjects?.get(position)?.id ?: 0
                loadLessons()
            }

        }
    }

    private fun loadLessons() {
        mailViewModel.getLessons(selectedSubjectId).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> { onLessonsSuccess(it.data) }
                Status.LOADING -> { Timber.d("lessons loading") }
                Status.ERROR -> { Timber.e(it.error) }
            }
        })
    }

    private fun onLessonsSuccess(data: List<Lesson>?) {
        lessons = data
        val lessonNameArray = lessons?.map { it.dateTime }?.toMutableList() ?: mutableListOf()
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            lessonNameArray
        )
        day_spinner.adapter = arrayAdapter
        day_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, arg3: Long) {
                selectedLessonId = lessons?.get(position)?.id ?: 0
            }
        }
    }

    private fun loadReasons() {
        mailViewModel.getReasons().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> { onReasonsSuccess(it.data) }
                Status.ERROR -> { Timber.e(it.error) }
                Status.LOADING -> { Timber.d("reasons loading") }
            }
        })
    }

    private fun onReasonsSuccess(date: List<Reason>?) {
        reasons = date
        val reasonNameArray = reasons?.map { it.name }?.toMutableList() ?: mutableListOf()
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            reasonNameArray
        )
        reason_spinner.adapter = arrayAdapter
        reason_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, arg3: Long) {
                selectedReasonId = reasons?.get(position)?.id ?: 0
            }
        }
    }
}