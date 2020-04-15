package com.example.baqyla.ui.id

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.baqyla.data.UserRepository
import com.example.baqyla.data.UserRepositoryImpl

class IdViewModel(private val repository: UserRepository = UserRepositoryImpl()) : ViewModel() {
    fun isUserExists(username: String): LiveData<Boolean> {
        return repository.isUserExists(username)
    }

    fun isPasswordExists(username: String): LiveData<Boolean> {
        return repository.isPasswordExists(username)
    }
}