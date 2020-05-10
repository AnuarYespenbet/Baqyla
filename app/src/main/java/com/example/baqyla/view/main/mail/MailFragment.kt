package com.example.baqyla.view.main.mail

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.baqyla.R
import com.example.baqyla.data.model.*
import com.example.baqyla.utils.Status
import com.example.baqyla.utils.getDayMonthYearWithDots
import com.example.baqyla.utils.invisible
import com.example.baqyla.utils.visible
import kotlinx.android.synthetic.main.fragment_mail.*
import kotlinx.android.synthetic.main.layout_child.*
import timber.log.Timber

class MailFragment : Fragment(), TextWatcher {
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

        additional_field_edit.addTextChangedListener(this)
        send_btn.setOnClickListener {
            onSendClick()
        }
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
        val subjectNameArray =
            subjects?.map { it.name }?.toMutableList() ?: mutableListOf("Выберите предмет")
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
                setSendEnabled()
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
        val lessonNameArray =
            lessons?.map {
                it.datetime?.let { date ->
                    getDayMonthYearWithDots(date)
                }
            }?.toMutableList() ?: mutableListOf("Выберите день")
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
                setSendEnabled()
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
        val reasonNameArray =
            reasons?.map { it.name }?.toMutableList() ?: mutableListOf("Выберите причину")
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
                setSendEnabled()
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        setBgEdit(additional_field_edit)
    }

    private fun setBgEdit(editText: EditText) {
        editText.background =
            if (editText.text.isEmpty())
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_grey)
            else ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_green)
    }

    private fun setSendEnabled() {
        send_btn.isEnabled =
            selectedSubjectId != 0 && selectedLessonId != 0 && selectedReasonId != 0
    }

    private fun onSendClick() {
        val hashMap = HashMap<String, Any>()
        hashMap["lessonId"] = selectedLessonId
        hashMap["reasonId"] = selectedReasonId
        if (additional_field_edit.text.isNotEmpty()) {
            hashMap["additionalInfo"] = additional_field_edit.text.toString()
        }
        mailViewModel.sendInform(hashMap).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    onSendSuccess()
                }
                Status.ERROR -> {
                    Timber.e(it.error)
                    progress_bar.invisible()
                    send_btn.visible()
                }
                Status.LOADING -> {
                    progress_bar.visible()
                    send_btn.invisible()
                }
            }
        })
    }

    private fun onSendSuccess() {
        val dialog = MailSuccessDialog()
        dialog.show(childFragmentManager, dialog::class.java.name)
        progress_bar.invisible()
        send_btn.visible()
    }
}