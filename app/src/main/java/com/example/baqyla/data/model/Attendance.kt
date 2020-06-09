package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("id") val id: Int,
    @SerializedName("time") val time: String,
    @SerializedName("childId") val childId: Int,
    @SerializedName("status") val status: AttendanceStatus?
)

data class AttendanceStatus(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: AttendanceStatusEnum
)

enum class AttendanceStatusEnum {
    DONT_LATE,
    LATE,
    UNKNOWN
}