package com.example.baqyla.data.model

import com.google.gson.annotations.SerializedName

data class Statistic(
    @SerializedName("come") val come: Int,
    @SerializedName("comePercent") val comePercent: Double,
    @SerializedName("dontCome") val dontCome: Int,
    @SerializedName("dontComePercent") val dontComePercent: Double
)