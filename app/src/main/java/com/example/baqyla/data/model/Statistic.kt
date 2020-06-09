package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName

data class Statistic(
    @SerializedName("come") val come: Int,
    @SerializedName("comePercent") val comePercent: Int,
    @SerializedName("dontCome") val dontCome: Int,
    @SerializedName("dontComePercent") val dontComePercent: Int
)