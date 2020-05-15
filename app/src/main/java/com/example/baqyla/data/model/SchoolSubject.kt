package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName

data class SchoolSubject(
    @SerializedName("count") val count: Int,
    @SerializedName("subject") val subject: String
)