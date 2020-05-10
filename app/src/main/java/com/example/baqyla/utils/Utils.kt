package com.example.baqyla.utils

import android.content.Context
import android.util.TypedValue
import com.example.baqyla.R
import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.model.Subject
import org.threeten.bp.DayOfWeek
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.text.SimpleDateFormat
import java.util.*

fun generateLessons(): List<Lesson> {
    val list = mutableListOf<Lesson>()
    val currentMonth = YearMonth.now()

    val currentMonth17 = currentMonth.atDay(17)
    list.add(
        Lesson(
            time = currentMonth17.atTime(9, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange,
            attendance = Constants.NONE
        )
    )

    val currentMonth22 = currentMonth.atDay(22)
    list.add(
        Lesson(
            time = currentMonth22.atTime(11, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange,
            attendance = Constants.NONE
        )
    )
    list.add(
        Lesson(
            time = currentMonth22.atTime(15, 0),
            subject = Subject(name = "math"), color = R.color.lesson_blue,
            attendance = Constants.NONE
        )
    )

    list.add(
        Lesson(
            time = currentMonth.atDay(3).atTime(11, 0),
            subject = Subject(name = "math"), color = R.color.lesson_blue,
            attendance = Constants.NOT_LATE
        )
    )
    list.add(
        Lesson(
            time = currentMonth.atDay(7).atTime(10, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange,
            attendance = Constants.DID_NOT_COME
        )
    )

    val nextMonth13 = currentMonth.plusMonths(1).atDay(13)
    list.add(
        Lesson(
            time = nextMonth13.atTime(10, 0),
            subject = Subject(name = "math"), color = R.color.lesson_blue,
            attendance = Constants.NONE
        )
    )
    list.add(
        Lesson(
            time = nextMonth13.atTime(15, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange,
            attendance = Constants.NONE
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

fun birthdayFromArrayToString(list: List<Int>): String {
    return "${list[2]}.${list[1]}.${list[0]}"
}

fun getDayMonthYearWithDots(dateTime: String): String {
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("ru")).parse(dateTime)
    return SimpleDateFormat("dd.MM.yyyy", Locale("ru")).format(date!!)
}