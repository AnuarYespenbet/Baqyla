package com.example.baqyla.view.entry.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.local.LocalStoreStringType
import com.example.baqyla.data.model.User
import com.example.baqyla.data.repository.UserRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : BaseViewModel() {
    private val repository = UserRepository(api)
    val id
        get() = LocalStore().get(LocalStoreStringType.USER_ID)

    fun login(id: String, password: String): LiveData<Event<User>> {
        val liveData = MutableLiveData<Event<User>>()
        addDisposable(
            repository.login(id, password)
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

    fun saveUser(user: User) {
        LocalStore().save(user, LocalStoreObjectType.CURRENT_USER)
    }

    fun changeId() {
        LocalStore().removeString(LocalStoreStringType.USER_ID)
    }
}