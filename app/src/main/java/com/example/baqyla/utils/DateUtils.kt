package com.example.baqyla.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getFirstDayOfMonth(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        val firstDayOfMonth = calendar.time

        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT_PATTERN)
        return dateFormat.format(firstDayOfMonth)
    }

    fun getLastDayOfMonth(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val lastDayOfMonth = calendar.time

        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT_PATTERN)
        return dateFormat.format(lastDayOfMonth)
    }
}