package com.example.baqyla.view.main.mail

import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.model.Reason
import com.example.baqyla.data.model.Subject
import com.example.baqyla.data.model.User
import com.example.baqyla.data.remote.response.Inform
import com.example.baqyla.data.repository.MailRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MailViewModel : BaseViewModel() {
    private val user = LocalStore().get(LocalStoreObjectType.CURRENT_USER, User::class.java)
    val child = if (!user?.children.isNullOrEmpty()) user?.children?.get(0) else null
    private val repository = MailRepository(api)

    fun getSubjects(): MutableLiveData<Event<List<Subject>>> {
        val liveData = MutableLiveData<Event<List<Subject>>>()
        addDisposable(
            repository.getSubjects()
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

    fun getLessons(subjectId: Int): MutableLiveData<Event<List<Lesson>>> {
        val liveData = MutableLiveData<Event<List<Lesson>>>()
        addDisposable(
            repository.getLessons(subjectId)
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

    fun getReasons(): MutableLiveData<Event<List<Reason>>> {
        val liveData = MutableLiveData<Event<List<Reason>>>()
        addDisposable(
            repository.getReasons()
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

    fun sendInform(hashMap: HashMap<String, Any>): MutableLiveData<Event<Inform>> {
        val liveData = MutableLiveData<Event<Inform>>()
        addDisposable(
            repository.sendInform(data = hashMap)
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