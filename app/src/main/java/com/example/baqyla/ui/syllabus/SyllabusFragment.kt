package com.example.baqyla.ui.syllabus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.baqyla.R
import com.example.baqyla.utils.daysOfWeekFromLocale
import com.example.baqyla.utils.generateLessons
import com.example.baqyla.utils.getColorCompat
import com.example.baqyla.utils.setTextColorRes
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import kotlinx.android.synthetic.main.calendar_day_legend.view.*
import kotlinx.android.synthetic.main.fragment_syllabus.*
import kotlinx.android.synthetic.main.item_calendar_day.view.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle
import java.util.*

class SyllabusFragment : Fragment() {
    private var selectedDate: LocalDate? = null
    private val lessonsAdapter = SyllabusAdapter()
    private val lessons = generateLessons().groupBy { it.dateTime?.toLocalDate() }
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_syllabus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lessons_rv.apply {
            adapter = lessonsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        lessonsAdapter.notifyDataSetChanged()

        val daysOfWeek = daysOfWeekFromLocale()

        val currentMonth = YearMonth.now()
        calendar_view.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        calendar_view.scrollToMonth(currentMonth)

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val textView = view.day_text
            val layout = view.day_container

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        if (selectedDate != day.date) {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            calendar_view.notifyDateChanged(day.date)
                            oldDate?.let { calendar_view.notifyDateChanged(it) }
                            updateAdapterForDate(day.date)
                        }
                    }
                }
            }
        }

        calendar_view.dayBinder = object : DayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                val layout = container.layout
                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.setTextColorRes(R.color.black)
                    layout.setBackgroundResource(if (selectedDate == day.date) R.drawable.selected_day else 0)

                    val lessonsOnDay = lessons[day.date]
                    if (lessonsOnDay != null) {
                        layout.setBackgroundColor(view.context.getColorCompat(lessonsOnDay[0].color))
                    }
                } else {
                    //textView.setTextColorRes(R.color.grey)
                    layout.background = null
                }
            }

            override fun create(view: View): DayViewContainer = DayViewContainer(view)

        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.legend_layout
        }

        calendar_view.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            tv.text =
                                daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale("ru"))
                            tv.setTextColorRes(R.color.grey)
                        }
                    month.yearMonth
                }
            }

            override fun create(view: View): MonthViewContainer = MonthViewContainer(view)
        }

        calendar_view.monthScrollListener = { month ->
            val title = "${monthTitleFormatter.format(month.yearMonth)}, ${month.yearMonth.year}"
            month_and_year.text = title

            selectedDate?.let {
                selectedDate = null
                calendar_view.notifyDateChanged(it)
                updateAdapterForDate(null)
            }
        }

        next_month.setOnClickListener {
            calendar_view.findFirstVisibleMonth()?.let {
                calendar_view.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        previous_month.setOnClickListener {
            calendar_view.findFirstVisibleMonth()?.let {
                calendar_view.smoothScrollToMonth(it.yearMonth.previous)
            }
        }

    }

    private fun updateAdapterForDate(date: LocalDate?) {
        lessonsAdapter.lessons.clear()
        lessonsAdapter.lessons.addAll(lessons[date].orEmpty())
        lessonsAdapter.notifyDataSetChanged()
    }
}