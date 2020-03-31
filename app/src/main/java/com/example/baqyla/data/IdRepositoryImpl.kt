package com.example.baqyla.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.net.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class IdRepositoryImpl : IdRepository {
    private val retrofitClient = RetrofitClient()

    var disposables = CompositeDisposable()

    companion object {
        val TAG = IdRepositoryImpl::class.java.name
    }

    override fun isUserExists(username: String): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
        disposables.add(
            retrofitClient.isUserExists(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val exists = it.body()?.string()?.toBoolean() ?: false
                    Log.d(TAG, "before if: ${it.body()?.string()}")
                    if (it.isSuccessful) {
                        Log.d(TAG, "after if: ${it.body()?.string()}")
                        data.value = exists
                    } else {
                        data.value = null
                    }
                }
        )
        return data
    }
}