package com.example.baqyla.id

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.baqyla.data.IdRepository
import com.example.baqyla.data.IdRepositoryImpl

class IdViewModel(private val repository: IdRepository = IdRepositoryImpl()) : ViewModel() {
    fun isUserExists(username: String): LiveData<Boolean> {
        return repository.isUserExists(username)
    }
}