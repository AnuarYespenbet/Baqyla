package com.example.baqyla.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.baqyla.data.models.User
import com.example.baqyla.data.UserRepository
import com.example.baqyla.data.UserRepositoryImpl

class LoginViewModel(private val repository: UserRepository = UserRepositoryImpl()) : ViewModel() {
    fun login(username: String, password: String): LiveData<User> {
        return repository.login(username, password)
    }
}