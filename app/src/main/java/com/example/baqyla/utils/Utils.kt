package com.example.baqyla.utils

import com.example.baqyla.R
import com.example.baqyla.data.models.LessonsResponse
import com.example.baqyla.data.models.Subject
import org.threeten.bp.YearMonth

fun generateLessons(): List<LessonsResponse> {
    val list = mutableListOf<LessonsResponse>()
    val currentMonth = YearMonth.now()

    val currentMonth17 = currentMonth.atDay(17)
    list.add(
        LessonsResponse(
            currentMonth17.atTime(9, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange
        )
    )
    list.add(
        LessonsResponse(
            currentMonth17.atTime(10, 0),
            subject = Subject(name = "math"), color = R.color.lesson_blue
        )
    )

    val currentMonth22 = currentMonth.atDay(22)
    list.add(
        LessonsResponse(
            currentMonth22.atTime(11, 0),
            subject = Subject(name = "iOS"), color = R.color.lesson_green
        )
    )
    list.add(
        LessonsResponse(
            currentMonth22.atTime(10, 0),
            subject = Subject(name = "backend"), color = R.color.lesson_red
        )
    )

    list.add(
        LessonsResponse(
            currentMonth.atDay(3).atTime(11, 0),
            subject = Subject(name = "frontend"), color = R.color.lesson_yellow
        )
    )
    list.add(
        LessonsResponse(
            currentMonth.atDay(7).atTime(10, 0),
            subject = Subject(name = "android"), color = R.color.lesson_orange
        )
    )

    val nextMonth13 = currentMonth.plusMonths(1).atDay(13)
    list.add(
        LessonsResponse(
            nextMonth13.atTime(10, 0),
            subject = Subject(name = "ios"), color = R.color.lesson_green
        )
    )
    list.add(
        LessonsResponse(
            nextMonth13.atTime(15, 0),
            subject = Subject(name = "backend"), color = R.color.lesson_blue
        )
    )
    return list
}