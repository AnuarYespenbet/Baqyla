package com.example.baqyla.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.baqyla.data.UserRepository
import com.example.baqyla.data.UserRepositoryImpl

class LoginViewModel(private val repository: UserRepository = UserRepositoryImpl()) : ViewModel() {
    fun login(username: String, password: String): LiveData<Boolean> {
        return repository.login(username, password)
    }
}