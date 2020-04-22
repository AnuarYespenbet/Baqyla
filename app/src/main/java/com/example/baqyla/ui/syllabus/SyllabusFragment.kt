package com.example.baqyla.ui.syllabus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.baqyla.R
import com.example.baqyla.data.models.LessonsResponse
import com.example.baqyla.data.models.Subject
import com.example.baqyla.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_syllabus.*

class SyllabusFragment : Fragment() {
    private val viewModel: SyllabusViewModel by viewModels()
    private val lessons = ArrayList<LessonsResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_syllabus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateFrom = DateUtils.getFirstDayOfMonth()
        val dateTo = DateUtils.getLastDayOfMonth()
        val subjectId = 1

        val adapter = SyllabusAdapter()
        lessons_recyclerview.adapter = adapter
        lessons_recyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        calendar_view.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            lessons.clear()
            when (dayOfMonth) {
                23 -> {
                    addLesson("09:00", "10:00", "math", "mr. Sultanbek")
                    addLesson("10:00", "11:00", "android", "mr. Yespenbet")
                }
                27 -> {
                    addLesson("11:00", "10:00", "backend", "mr. Taubaldy")
                    adapter.setItems(lessons)
                }
                30 -> {
                    addLesson("14:00", "15:00", "iOS", "mr. Ulubayev")
                    addLesson("15:00", "16:00", "frontend", "mr. Izbergenov")
                    adapter.setItems(lessons)
                }
            }
            adapter.setItems(lessons)
        }

        /*viewModel.getLessons(dateFrom, dateTo, subjectId).observe(this, Observer {
            if (it != null) {
                Log.d(tag, it.toString())

            } else {
                Log.d(tag, "error")
            }
        })*/
    }

    private fun addLesson(
        startTime: String,
        endTime: String,
        subjectName: String,
        teacherName: String
    ) {
        lessons.add(
            LessonsResponse(
                startTime = startTime,
                endTime = endTime,
                subject = Subject(name = subjectName, teacher = teacherName)
            )
        )
    }
}