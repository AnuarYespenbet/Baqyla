package com.example.baqyla.view.entry.id

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreStringType
import com.example.baqyla.data.remote.NetworkService
import com.example.baqyla.data.remote.response.ExistPassword
import com.example.baqyla.data.remote.response.ExistUser
import com.example.baqyla.data.repository.UserRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IdViewModel : BaseViewModel() {
    private val networkService = NetworkService
    private val api = networkService.createApiService()
    private val repository = UserRepository(api)

    fun isUserExists(id: String): LiveData<Event<ExistUser>> {
        val liveData = MutableLiveData<Event<ExistUser>>()
        compositeDisposable.add(
            repository.isUserExists(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveData.postValue(Event.loading())
                }
                .subscribe({
                    liveData.postValue(Event.success(it))
                }, {
                    liveData.postValue(Event.error(it.message))
                })
        )
        return liveData
    }

    fun isPasswordExists(id: String): LiveData<Event<ExistPassword>> {
        val liveData = MutableLiveData<Event<ExistPassword>>()
        compositeDisposable.add(
            repository.isPasswordExists(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveData.postValue(Event.loading())
                }
                .subscribe({
                    liveData.postValue(Event.success(it))
                }, {
                    liveData.postValue(Event.error(it.message))
                })
        )
        return liveData
    }

    fun saveId(id: String) {
        LocalStore().save(id, LocalStoreStringType.USER_ID)
    }
}