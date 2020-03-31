package com.example.baqyla.data

import androidx.lifecycle.LiveData

interface IdRepository {
    fun isUserExists(username: String): LiveData<Boolean>
}