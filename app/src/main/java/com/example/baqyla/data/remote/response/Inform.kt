package com.example.baqyla.data.remote.response

import com.google.gson.annotations.SerializedName

data class Inform(
    @SerializedName("additionalInfo") val additionalInfo: String?
)