package com.example.baqyla.data.model

import java.io.Serializable

data class Child(
    val id: Int,
    val name: String,
    val profilePhoto: String,
    val age: Int,
    val birthday: List<Int>,
    val subjects: List<Subject>
) : Serializable