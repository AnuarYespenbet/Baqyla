package com.example.baqyla.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.models.LessonsResponse
import com.example.baqyla.data.net.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LessonsRepository {
    private val retrofitClient = RetrofitClient()

    private var disposables = CompositeDisposable()

    companion object {
        val TAG = LessonsRepository::class.java.name
    }

    fun getLessons(
        dateFrom: String,
        dateTo: String,
        subjectId: Int
    ): LiveData<List<LessonsResponse>> {
        val data = MutableLiveData<List<LessonsResponse>>()
        disposables.add(
            retrofitClient.getLessons(dateFrom, dateTo, subjectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    data.value = response.body()
                }, { throwable ->
                    Log.d(TAG, throwable.message ?: "")
                    data.value = null
                })
        )
        return data
    }
}