package com.example.baqyla.data.models

data class LessonsResponse(
    val dateTime: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val description: String = "",
    val id: Int = 0,
    val subject: Subject,
    val title: String = ""
)