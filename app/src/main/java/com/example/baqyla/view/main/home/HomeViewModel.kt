package com.example.baqyla.view.main.home

import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.Child
import com.example.baqyla.data.model.Statistic
import com.example.baqyla.data.repository.HomeRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : BaseViewModel() {
    private val repository = HomeRepository(api)
    val child = LocalStore().get(LocalStoreObjectType.SELECTED_CHILD, Child::class.java)
    fun getStatistics(childId: Int, dateFrom: String, dateTo: String): MutableLiveData<Event<Statistic>> {
        val liveData = MutableLiveData<Event<Statistic>>()
        addDisposable(
            repository.getStatistics(childId, dateFrom, dateTo).subscribeOn(Schedulers.io())
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