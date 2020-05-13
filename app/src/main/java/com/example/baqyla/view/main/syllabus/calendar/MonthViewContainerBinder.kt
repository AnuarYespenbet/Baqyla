package com.example.baqyla.view.main.syllabus.calendar

import android.view.View
import android.widget.TextView
import androidx.core.view.children
import com.example.baqyla.R
import com.example.baqyla.utils.daysOfWeekFromLocale
import com.example.baqyla.utils.setTextColorRes
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import org.threeten.bp.format.TextStyle
import java.util.*

class MonthViewContainerBinder : MonthHeaderFooterBinder<MonthViewContainer> {
    override fun bind(container: MonthViewContainer, month: CalendarMonth) {
        if (container.layoutWeekDays.tag == null) {
            container.layoutWeekDays.tag = month.yearMonth
            container.layoutWeekDays.children.map { it as TextView }
                .forEachIndexed { index, textView ->
                    val daysOfWeek = daysOfWeekFromLocale()
                    textView.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale("ru"))
                    textView.setTextColorRes(R.color.grey)
                }
        }
    }

    override fun create(view: View): MonthViewContainer {
        return MonthViewContainer(view)
    }

}