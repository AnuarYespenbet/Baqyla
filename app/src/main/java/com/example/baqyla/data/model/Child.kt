package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Child(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("surname") val surname: String?,
    @SerializedName("profilePhoto") val profilePhoto: String?,
    @SerializedName("age") val age: Int?,
    @SerializedName("birthday") val birthday: List<Int>?,
    @SerializedName("telephone") val phone: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("subjects") val subjects: List<Subject>?
) : Serializable