package com.example.baqyla.data.models

import org.threeten.bp.LocalDateTime

data class LessonsResponse(
    val dateTime: LocalDateTime? = null,
    val description: String = "",
    val id: Int = 0,
    val subject: Subject,
    val title: String = "",
    val color: Int
)