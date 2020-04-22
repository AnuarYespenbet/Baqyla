package com.example.baqyla.data.models

import java.io.Serializable

data class Subject(
    val id: Int = 0,
    val name: String = "",
    val teacher: String = "",
    val count: Int = 0
): Serializable