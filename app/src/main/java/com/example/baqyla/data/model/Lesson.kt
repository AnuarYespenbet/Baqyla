package com.example.baqyla.data.model

import com.example.baqyla.R
import com.example.baqyla.utils.Constants
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Lesson(
    @SerializedName("datetime") val datetime: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("subject") val subject: Subject?,
    @SerializedName("title") val title: String? = "",
    val color: Int?,
    val attendance: String? = "",
    val time: LocalDateTime? = null
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