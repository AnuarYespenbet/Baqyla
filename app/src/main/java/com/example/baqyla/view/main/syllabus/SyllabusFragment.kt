package com.example.baqyla.view.main.syllabus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.baqyla.R
import com.example.baqyla.data.model.*
import com.example.baqyla.utils.*
import com.example.baqyla.view.main.syllabus.calendar.DayViewContainerBinder
import com.example.baqyla.view.main.syllabus.calendar.MonthViewContainerBinder
import com.example.baqyla.view.main.syllabus.calendar.OnDayClickListener
import com.example.baqyla.view.main.syllabus.calendar.SelectedDate
import com.example.baqyla.view.ui.CustomDividerItemDecoration
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import kotlinx.android.synthetic.main.fragment_syllabus.*
import kotlinx.android.synthetic.main.layout_child.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber

class SyllabusFragment : Fragment(), OnDayClickListener {
    private lateinit var syllabusViewModel: SyllabusViewModel
    private val currentMonth = YearMonth.now()
    private val minusMonth = currentMonth.minusMonths(10)
    private val plusMonth = currentMonth.plusMonths(10)
    private var child: Child? = null
    private val dayViewContainerBinder = DayViewContainerBinder()
    private val lessonsAdapter = SyllabusAdapter()
    private var lessonsMap: Map<LocalDate?, List<Lesson>>? = null

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

        setChild()
        setLessonsRv()
        getLessonsAndAttendances()
    }

    private fun getLessonsAndAttendances() {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val minusMonthFirstDay = minusMonth.atDay(1).atStartOfDay()
        val plusMonthFirstDay = plusMonth.atDay(1).atStartOfDay()
        val dateFrom = minusMonthFirstDay.format(dateFormat)
        val dateTo = plusMonthFirstDay.format(dateFormat)
        child?.id?.let { childId ->
            syllabusViewModel.getLessonsAndAttendance(childId, dateFrom, dateTo)
                .observe(viewLifecycleOwner, Observer { event ->
                    when (event.status) {
                        Status.SUCCESS -> {
                            onSuccess(event)
                        }
                        Status.ERROR -> {
                            onError(event)
                        }
                        Status.LOADING -> {
                            onLoading()
                        }
                    }
                })
        }
    }

    private fun onLoading() {
        progress_bar.visible()
        error_text.invisible()
        syllabus_container.invisible()
    }

    private fun onError(event: Event<Syllabus>) {
        Timber.e(event.error)
        error_text.text = event.error
        error_text.visible()
        progress_bar.invisible()
        syllabus_container.invisible()
    }

    private fun onSuccess(event: Event<Syllabus>) {
        val lessons = event.data?.lessons
        val attendances = event.data?.attendances

        lessonsMap = lessons?.groupBy { parseStringToLocalDate(it.datetime)?.toLocalDate() }
        val attendanceMap =
            attendances?.groupBy { parseStringToLocalDate(it.time)?.toLocalDate() }

        if (lessonsMap != null && attendanceMap != null) {
            dayViewContainerBinder.setMap(lessonsMap!!, attendanceMap)
            setCalendarView()
            progress_bar.invisible()
            error_text.invisible()
            syllabus_container.visible()
        } else {
            progress_bar.invisible()
            error_text.visible()
            syllabus_container.invisible()
        }
    }

    override fun onDayClick(selectedDate: LocalDate?, oldDate: LocalDate?) {
        SelectedDate.date = selectedDate
        selectedDate?.let { calendar_view.notifyDateChanged(it) }
        oldDate?.let { calendar_view.notifyDateChanged(it) }
        updateAdapterForDate(selectedDate)
    }

    private fun setCalendarView() {
        val daysOfWeek = daysOfWeekFromLocale()
        calendar_view.setHasFixedSize(true)
        calendar_view.setup(minusMonth, plusMonth, daysOfWeek.first())
        calendar_view.scrollToMonth(currentMonth)
        setDayBinder()
        setMonthBinder()
        setMonthScrollListener(currentMonth)
        onNextMonthClick()
        onPreviousMonthClick()
    }

    private fun setMonthBinder() {
        calendar_view.monthHeaderBinder = MonthViewContainerBinder()
    }

    private fun setDayBinder() {
        dayViewContainerBinder.setOnDayClickListener(this)
        calendar_view.dayBinder = dayViewContainerBinder
    }

    private fun onPreviousMonthClick() {
        previous_month.setOnClickListener {
            calendar_view.findFirstVisibleMonth()?.let {
                calendar_view.smoothScrollToMonth(it.yearMonth.previous)
            }
        }
    }

    private fun onNextMonthClick() {
        next_month.setOnClickListener {
            calendar_view.findFirstVisibleMonth()?.let {
                calendar_view.smoothScrollToMonth(it.yearMonth.next)
            }
        }
    }

    private fun setMonthScrollListener(currentMonth: YearMonth?) {
        calendar_view.monthScrollListener = {
            val today = LocalDate.now()
            val dateText = if (currentMonth == it.yearMonth)
                "${monthNamesRussian[it.month - 1]} ${it.yearMonth.year}, ${today.dayOfMonth}"
            else "${monthNamesRussian[it.month - 1]} ${it.yearMonth.year}"
            date.text = dateText
        }

        SelectedDate.date?.let {
            SelectedDate.date = null
            calendar_view.notifyDateChanged(it)
            updateAdapterForDate(null)
        }
    }

    private fun setChild() {
        child = syllabusViewModel.child
        child?.let {
            val fullName = "${child?.name} ${child?.surname}"
            child_name.text = fullName
        }
    }

    private fun setLessonsRv() {
        val itemDecoration = CustomDividerItemDecoration(requireContext())
        lessons_rv.apply {
            adapter = lessonsAdapter
            addItemDecoration(itemDecoration)
        }
        updateAdapterForDate(null)
    }

    private fun updateAdapterForDate(date: LocalDate?) {
        val lessonsOnDay = lessonsMap?.get(date)
        val items = arrayListOf<LessonItem>()
        lessonsOnDay?.let { items.addAll(it) }
        for (i in 0 until 6 - (lessonsOnDay?.size ?: 0)) {
            items.add(EmptyLesson)
        }
        lessonsAdapter.setLessonItems(items)
    }
}