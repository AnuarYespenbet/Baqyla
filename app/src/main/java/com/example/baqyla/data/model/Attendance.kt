package com.example.baqyla.data.model

import com.example.baqyla.utils.Constants
import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("id") val id: Int,
    @SerializedName("time") val time: String,
    @SerializedName("childId") val childId: Int,
    val type: String = Constants.NOT_LATE
)