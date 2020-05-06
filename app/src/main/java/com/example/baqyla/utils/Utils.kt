package com.example.baqyla.utils

import android.content.Context
import android.util.TypedValue
import com.example.baqyla.R
import com.example.baqyla.data.models.LessonsResponse
import com.example.baqyla.data.models.Subject
import org.threeten.bp.DayOfWeek
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.*

fun generateLessons(): List<LessonsResponse> {
    val list = mutableListOf<LessonsResponse>()
    val currentMonth = YearMonth.now()

    val currentMonth17 = currentMonth.atDay(17)
    list.add(
        LessonsResponse(
            currentMonth17.atTime(9, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange,
            attendance = Constants.NOT_LATE
        )
    )
    list.add(
        LessonsResponse(
            currentMonth17.atTime(10, 0),
            subject = Subject(name = "math"), color = R.color.lesson_blue,
            attendance = Constants.DID_NOT_COME
        )
    )

    val currentMonth22 = currentMonth.atDay(22)
    list.add(
        LessonsResponse(
            currentMonth22.atTime(11, 0),
            subject = Subject(name = "iOS"), color = R.color.lesson_green,
            attendance = Constants.LATE
        )
    )
    list.add(
        LessonsResponse(
            currentMonth22.atTime(10, 0),
            subject = Subject(name = "backend"), color = R.color.lesson_red,
            attendance = Constants.NOT_LATE
        )
    )

    list.add(
        LessonsResponse(
            currentMonth.atDay(3).atTime(11, 0),
            subject = Subject(name = "frontend"), color = R.color.lesson_yellow,
            attendance = Constants.NOT_LATE
        )
    )
    list.add(
        LessonsResponse(
            currentMonth.atDay(7).atTime(10, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange,
            attendance = Constants.DID_NOT_COME
        )
    )

    val nextMonth13 = currentMonth.plusMonths(1).atDay(13)
    list.add(
        LessonsResponse(
            nextMonth13.atTime(10, 0),
            subject = Subject(name = "ios"), color = R.color.lesson_green,
            attendance = Constants.LATE
        )
    )
    list.add(
        LessonsResponse(
            nextMonth13.atTime(15, 0),
            subject = Subject(name = "backend"), color = R.color.lesson_blue,
            attendance = Constants.NOT_LATE
        )
    )
    return list
}

fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale("ru")).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

val monthNamesRussian = arrayOf(
    "Январь",
    "Февраль",
    "Март",
    "Апрель",
    "Май",
    "Июнь",
    "Июль",
    "Август",
    "Сентябрь",
    "Октябрь",
    "Ноябрь",
    "Декабрь"
)

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()