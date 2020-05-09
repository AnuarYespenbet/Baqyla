package com.example.baqyla.data.remote.response

import com.google.gson.annotations.SerializedName

data class ExistUser(
    @SerializedName("aBoolean")
    val isUserExists: Boolean
)