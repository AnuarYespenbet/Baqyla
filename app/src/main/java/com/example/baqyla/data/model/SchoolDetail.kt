package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName

data class SchoolDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("mail") val mail: String,
    @SerializedName("phone") val phone: String
)