package com.example.baqyla.view.main.syllabus.calendar

import android.content.Context
import android.view.View
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.item_calendar_day.view.*

class DayViewContainer(view: View) : ViewContainer(view) {
    lateinit var day: CalendarDay

    val textView: TextView = view.day_text
    val dayBackground: View = view.day_background
    val dayTopBackground: View = view.day_top_background
    val dayBottomBackground: View = view.day_bottom_background
    val dotView: View = view.dot_view
    val context: Context = view.context

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                if (SelectedDate.date != day.date) {
                    val oldDate = SelectedDate.date
                    SelectedDate.date = day.date
                    mOnDayClickListener?.onDayClick(SelectedDate.date, oldDate)
                }
            }
        }
    }

    private var mOnDayClickListener: OnDayClickListener? = null
    fun setOnDayClickListener(onDayClickListener: OnDayClickListener) {
        mOnDayClickListener = onDayClickListener
    }
}