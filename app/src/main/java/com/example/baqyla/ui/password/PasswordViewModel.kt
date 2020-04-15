package com.example.baqyla.ui.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.baqyla.data.UserRepository
import com.example.baqyla.data.UserRepositoryImpl

class PasswordViewModel(private val repository: UserRepository = UserRepositoryImpl()) :
    ViewModel() {
    fun setPassword(hashMap: HashMap<String, Any>): LiveData<Boolean> {
        return repository.setPassword(hashMap)
    }
}