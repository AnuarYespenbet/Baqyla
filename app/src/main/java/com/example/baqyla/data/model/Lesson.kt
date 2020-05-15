package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("datetime") val datetime: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("subject") val subject: Subject,
    @SerializedName("title") val title: String
) : LessonItem()

object EmptyLesson : LessonItem()

sealed class LessonItem