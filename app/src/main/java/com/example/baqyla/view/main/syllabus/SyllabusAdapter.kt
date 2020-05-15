package com.example.baqyla.view.main.syllabus

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R
import com.example.baqyla.data.model.EmptyLesson
import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.model.LessonItem
import com.example.baqyla.utils.invisible
import com.example.baqyla.utils.parseStringToLocalDate
import kotlinx.android.synthetic.main.row_item_syllabus.view.*
import org.threeten.bp.format.DateTimeFormatter

class SyllabusAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lessons: ArrayList<LessonItem> = arrayListOf()
    fun setLessonItems(items: ArrayList<LessonItem>) {
        lessons = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LESSON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_item_syllabus, parent, false)
                LessonHolder(view)
            }
            EMPTY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_item_syllabus, parent, false)
                EmptyLessonHolder(view)
            }
            else -> {
                throw RuntimeException("The type has to be SUBJECT or EMPTY")
            }
        }
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LESSON -> (holder as LessonHolder).bind(lessons[position] as Lesson)
            EMPTY -> (holder as EmptyLessonHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (lessons[position]) {
            is Lesson -> LESSON
            is EmptyLesson -> EMPTY
        }
    }

    inner class LessonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lesson: Lesson) {
            itemView.apply {
                val dateFormat = DateTimeFormatter.ofPattern("HH:mm")
                val date = parseStringToLocalDate(lesson.datetime)
                start_time.text = date?.format(dateFormat)
                end_time.text = date?.format(dateFormat)
                subject_name.text = lesson.subject.name
                subject_name.setTextColor(Color.parseColor(lesson.subject.color))
                divider.background.setTint(Color.parseColor(lesson.subject.color))
            }
        }
    }

    inner class EmptyLessonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.apply {
                subject_name.invisible()
                divider.invisible()
                start_time.invisible()
                end_time.invisible()
            }
        }
    }

    companion object {
        const val LESSON = 1
        const val EMPTY = 2
    }
}