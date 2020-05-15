package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class Subject(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("teacher") val teacher: String,
    @SerializedName("color") val color: String
) {
    var attendanceCount: Int = Random.nextInt(1, 10)
    var absenceCount: Int = 0
}