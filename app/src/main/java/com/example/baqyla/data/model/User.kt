package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("userId") val userId: Int,
    @SerializedName("username") val username: String,
    @SerializedName("children") val children: List<Child>?
): Serializable