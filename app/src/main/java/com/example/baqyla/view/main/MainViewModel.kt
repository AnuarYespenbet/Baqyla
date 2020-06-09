package com.example.baqyla.view.main

import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.local.LocalStoreStringType
import com.example.baqyla.data.model.Child
import com.example.baqyla.data.model.User
import com.example.baqyla.data.repository.UserRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class MainViewModel : BaseViewModel() {
    private val user = LocalStore().get(LocalStoreObjectType.CURRENT_USER, User::class.java)
    val children = user?.children ?: arrayListOf()
    private val repository = UserRepository(api)
    val id
        get() = LocalStore().get(LocalStoreStringType.USER_ID)

    fun saveSelectedChild(child: Child) {
        LocalStore().save(child, LocalStoreObjectType.SELECTED_CHILD)
    }

    fun logout(): MutableLiveData<Event<ResponseBody>> {
        val liveData = MutableLiveData<Event<ResponseBody>>()
        addDisposable(
            repository.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveData.postValue(Event.loading()) }
                .subscribe({
                    liveData.postValue(Event.success(it))
                }, {
                    liveData.postValue(Event.error(it.message))
                })
        )
        return liveData
    }

    private fun unsubscribeFromFirebaseNotification() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(id!!)
    }
}