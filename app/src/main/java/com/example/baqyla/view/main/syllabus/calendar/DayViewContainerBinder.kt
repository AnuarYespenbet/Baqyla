package com.example.baqyla.view.main.syllabus.calendar

import android.graphics.Color
import android.view.View
import com.example.baqyla.R
import com.example.baqyla.data.model.Attendance
import com.example.baqyla.data.model.Lesson
import com.example.baqyla.utils.getColorCompat
import com.example.baqyla.utils.invisible
import com.example.baqyla.utils.setTextColorRes
import com.example.baqyla.utils.visible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import org.threeten.bp.LocalDate

class DayViewContainerBinder : DayBinder<DayViewContainer>, OnDayClickListener {
    private val today = LocalDate.now()

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.day = day
        container.setOnDayClickListener(this)

        val textView = container.textView
        textView.text = day.date.dayOfMonth.toString()

        val dayBackground = container.dayBackground
        val dayTopBackground = container.dayTopBackground
        val dayBottomBackground = container.dayBottomBackground
        val dotView = container.dotView

        val context = container.context

        textView.invisible()
        dayBackground.invisible()
        dayTopBackground.invisible()
        dayBottomBackground.invisible()
        dotView.invisible()

        if (day.owner == DayOwner.THIS_MONTH) {
            textView.visible()
            textView.setTextColorRes(R.color.black)
            if (day.date == SelectedDate.date) {
                dayBackground.visible()
                textView.setTextColorRes(R.color.white)
                dayBackground.background.setTint(context.getColorCompat(R.color.light_grey))
            }
            if (day.date == today) {
                dayBackground.visible()
                textView.setTextColorRes(R.color.white)
                dayBackground.background.setTint(context.getColorCompat(R.color.green))
            }
            val lessonsOnDay = mLessonsMap?.get(day.date)
            if (!lessonsOnDay.isNullOrEmpty()) {
                textView.setTextColorRes(R.color.white)
                if (lessonsOnDay.size == 1) {
                    dayBackground.visible()
                    lessonsOnDay[0].subject.color.let {
                        dayBackground.background.setTint(Color.parseColor(it))
                    }
                } else {
                    dayTopBackground.visible()
                    dayBottomBackground.visible()
                    dayBackground.invisible()
                    lessonsOnDay[0].subject.color.let {
                        dayTopBackground.background.setTint(Color.parseColor(it))
                    }
                    lessonsOnDay[1].subject.color.let {
                        dayBottomBackground.background.setTint(Color.parseColor(it))
                    }
                }
            }
            val attendancesOnDay = mAttendanceMap?.get(day.date)
            if (!attendancesOnDay.isNullOrEmpty()) {
                dotView.visible()
                dotView.background.setTint(context.getColorCompat(R.color.green))
            }
        }
    }

    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view)
    }

    override fun onDayClick(selectedDate: LocalDate?, oldDate: LocalDate?) {
        mOnDayClickListener?.onDayClick(selectedDate, oldDate)
    }

    private var mOnDayClickListener: OnDayClickListener? = null
    fun setOnDayClickListener(onDayClickListener: OnDayClickListener) {
        mOnDayClickListener = onDayClickListener
    }

    private var mLessonsMap: Map<LocalDate?, List<Lesson>>? = null
    private var mAttendanceMap: Map<LocalDate?, List<Attendance>>? = null
    fun setMap(
        lessonsMap: Map<LocalDate?, List<Lesson>>,
        attendanceMap: Map<LocalDate?, List<Attendance>>
    ) {
        mLessonsMap = lessonsMap
        mAttendanceMap = attendanceMap
    }
}