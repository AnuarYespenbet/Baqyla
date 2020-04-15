package com.example.baqyla.data.models

import java.io.Serializable

data class User(
    val username: String?,
    val children: List<Child>?
) : Serializable

data class Child(
    val id: Int,
    val profilePhoto: String,
    val name: String,
    val age: Int
) : Serializable