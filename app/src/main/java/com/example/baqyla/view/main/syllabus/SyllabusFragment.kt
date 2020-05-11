package com.example.baqyla.view.main.syllabus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.baqyla.R
import com.example.baqyla.data.model.Child
import com.example.baqyla.data.model.User
import com.example.baqyla.utils.*
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
import kotlinx.android.synthetic.main.layout_child.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.TextStyle
import java.util.*

class SyllabusFragment : Fragment() {
    private lateinit var syllabusViewModel: SyllabusViewModel
    private var selectedDate: LocalDate? = null
    private val lessonsAdapter = SyllabusAdapter()
    private val lessons = generateLessons().groupBy { it.time?.toLocalDate() }
    private val today = LocalDate.now()
    private var user: User? = null
    private var child: Child? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_syllabus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        syllabusViewModel = ViewModelProvider(this).get(SyllabusViewModel::class.java)
        user = syllabusViewModel.user
        child = user?.children?.get(0)
        child?.apply {
            val fullName = "$name $surname"
            child_name.text = fullName
        }

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_item_decoration
            )!!
        )
        lessons_rv.apply {
            adapter = lessonsAdapter
            addItemDecoration(itemDecoration)
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
        val firstDay = currentMonth.atDay(1)
        val lastDay = currentMonth.atEndOfMonth()

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay
            val textView = view.day_text
            val dayBackground: View = view.day_background
            val dayTopBackground: View = view.day_top_background
            val dayBottomBackground: View = view.day_bottom_background
            val dotView: View = view.dot_view

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
                textView.text = day.date.dayOfMonth.toString()

                val dayBackground = container.dayBackground
                val dayTopBackground = container.dayTopBackground
                val dayBottomBackground = container.dayBottomBackground
                val dotView = container.dotView

                if (day.owner == DayOwner.THIS_MONTH) {
                    val lessonsOnDay = lessons[day.date]
                    when {
                        today == day.date -> {
                            textView.setTextColorRes(R.color.white)
                            dayBackground.background.setTint(requireContext().getColorCompat(R.color.green))
                            dayBackground.visible()
                            dayTopBackground.invisible()
                            dayBottomBackground.invisible()
                        }
                        lessonsOnDay != null -> {
                            textView.setTextColorRes(R.color.white)
                            if (lessonsOnDay.size == 1) {
                                lessonsOnDay[0].color?.let {
                                    dayBackground.background.setTint(
                                        requireContext().getColorCompat(it)
                                    )
                                }

                                dotView.background.setTint(
                                    requireContext().getColorCompat(
                                        lessonsOnDay[0].attendanceColor
                                    )
                                )
                                dayBackground.visible()
                                dayTopBackground.invisible()
                                dayBottomBackground.invisible()
                            } else if (lessonsOnDay.size == 2) {
                                lessonsOnDay[0].color?.let {
                                    dayTopBackground.background.setTint(
                                        requireContext().getColorCompat(it)
                                    )
                                }
                                lessonsOnDay[1].color?.let {
                                    dayBottomBackground.background.setTint(
                                        requireContext().getColorCompat(it)
                                    )
                                }
                                dotView.background.setTint(
                                    requireContext().getColorCompat(
                                        lessonsOnDay[0].attendanceColor
                                    )
                                )
                                dayBackground.invisible()
                                dayTopBackground.visible()
                                dayBottomBackground.visible()
                            }
                        }
                        selectedDate == day.date -> {
                            textView.setTextColorRes(R.color.white)
                            dayBackground.background.setTint(requireContext().getColorCompat(R.color.grey))
                            dayBackground.visible()
                            dayTopBackground.invisible()
                            dayBottomBackground.invisible()

                            dotView.background.setTint(
                                requireContext().getColorCompat(
                                    R.color.white
                                )
                            )
                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            dayBackground.background.setTint(requireContext().getColorCompat(R.color.white))
                            dayBackground.visible()
                            dayTopBackground.invisible()
                            dayBottomBackground.invisible()

                            dotView.background.setTint(
                                requireContext().getColorCompat(
                                    R.color.white
                                )
                            )
                        }
                    }
                } else {
                    textView.invisible()
                    dayBackground.invisible()
                    dayTopBackground.invisible()
                    dayBottomBackground.invisible()
                    dotView.invisible()
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
            val title =
                "${monthNamesRussian[month.month - 1]} ${month.yearMonth.year}, ${selectedDate?.dayOfMonth ?: today.dayOfMonth}"
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