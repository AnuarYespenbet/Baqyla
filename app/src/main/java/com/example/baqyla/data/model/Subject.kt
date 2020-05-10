package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.random.Random

data class Subject(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("teacher") val teacher: String = ""
) : Serializable {
    var attendanceCount: Int = Random.nextInt(1, 10)
    var absenceCount: Int = 0
}