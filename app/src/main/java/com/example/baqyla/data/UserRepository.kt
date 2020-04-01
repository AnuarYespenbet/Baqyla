package com.example.baqyla.data

import androidx.lifecycle.LiveData

interface UserRepository {
    fun isUserExists(username: String): LiveData<Boolean>
    fun isPasswordExists(username: String): LiveData<Boolean>
    fun setPassword(hashMap: HashMap<String, Any>): LiveData<Boolean>
    fun login(username: String, password: String): LiveData<Boolean>
}