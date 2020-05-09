package com.example.baqyla.view.entry.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreStringType
import com.example.baqyla.data.repository.UserRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class PasswordViewModel : BaseViewModel() {
    private val repository = UserRepository(api)

    fun saveId(userId: String) {
        LocalStore().save(userId, LocalStoreStringType.USER_ID)
    }

    fun setPassword(hashMap: HashMap<String, Any>): LiveData<Event<ResponseBody>> {
        val liveData = MutableLiveData<Event<ResponseBody>>()
        addDisposable(
            repository.setPassword(hashMap)
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
}