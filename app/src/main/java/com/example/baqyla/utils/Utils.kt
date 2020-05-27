package com.example.baqyla.utils

import android.content.Context
import android.util.TypedValue
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.WeekFields
import java.text.SimpleDateFormat
import java.util.*

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
    val date = SimpleDateFormat(Constants.BACKEND_DATE_FORMAT, Locale("ru")).parse(dateTime)
    return SimpleDateFormat(Constants.ANDROID_DATE_FORMAT, Locale("ru")).format(date!!)
}

fun parseStringToLocalDate(dateTime: String): LocalDateTime? {
    return LocalDateTime.parse(dateTime)
}