package com.example.baqyla.data.models

import com.example.baqyla.R
import com.example.baqyla.utils.Constants
import org.threeten.bp.LocalDateTime

data class LessonsResponse(
    val dateTime: LocalDateTime? = null,
    val description: String = "",
    val id: Int = 0,
    val subject: Subject,
    val title: String = "",
    val color: Int,
    val attendance: String = ""
) {
    val attendanceColor = when (attendance) {
        Constants.NOT_LATE -> {
            R.color.attendance_green
        }
        Constants.DID_NOT_COME -> {
            R.color.attendance_red
        }
        Constants.LATE -> {
            R.color.attendance_orange
        }
        else -> {
            R.color.white
        }
    }
}