package com.example.baqyla.data.remote.response

import com.google.gson.annotations.SerializedName

data class ExistPassword(
    @SerializedName("aBoolean")
    val isPasswordExists: Boolean
)