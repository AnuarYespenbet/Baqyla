package com.example.baqyla.data.models

import java.io.Serializable

data class User(
    val userId: Int,
    val username: String,
    val children: List<Child>
): Serializable