package com.example.baqyla.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.net.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserRepositoryImpl : UserRepository {
    private val retrofitClient = RetrofitClient()

    private var disposables = CompositeDisposable()

    companion object {
        val TAG = UserRepositoryImpl::class.java.name
    }

    override fun isUserExists(username: String): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
        disposables.add(
            retrofitClient.isUserExists(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val exists = it.body()?.string()?.toBoolean() ?: false
                    if (it.isSuccessful) {
                        data.value = exists
                    } else {
                        data.value = null
                    }
                }
        )
        return data
    }

    override fun isPasswordExists(username: String): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
        disposables.add(
            retrofitClient.isPasswordExists(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val exists = it.body()?.string()?.toBoolean() ?: false
                    if (it.isSuccessful) {
                        data.value = exists
                    } else {
                        data.value = null
                    }
                }
        )
        return data
    }

    override fun setPassword(hashMap: HashMap<String, Any>): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
        disposables.add(
            retrofitClient.setPassword(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isSuccessful) {
                        Log.d(TAG, it.code().toString())
                        data.value = true
                    } else {
                        data.value = false
                    }
                }
        )
        return data
    }

    override fun login(username: String, password: String): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
        disposables.add(
            retrofitClient.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isSuccessful) {
                        Log.d(TAG, it.code().toString())
                        data.value = true
                    } else {
                        data.value = false
                    }
                }
        )
        return data
    }
}