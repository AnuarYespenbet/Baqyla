package com.example.baqyla.view.base

import androidx.lifecycle.ViewModel
import com.example.baqyla.data.remote.NetworkService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val networkService = NetworkService
    val api = networkService.createApiService()
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }
}