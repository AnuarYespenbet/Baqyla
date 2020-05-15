package com.example.baqyla.view.main.school

import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.model.School
import com.example.baqyla.data.model.SchoolDetail
import com.example.baqyla.data.model.SchoolSubject
import com.example.baqyla.data.repository.SchoolRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SchoolViewModel : BaseViewModel() {
    private val repository = SchoolRepository(api)

    fun getSchool(): MutableLiveData<Event<School>> {
        val liveData = MutableLiveData<Event<School>>()
        addDisposable(
            zip().subscribeOn(Schedulers.io())
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

    private fun zip(): Observable<School> {
        return Observable.zip(
            getDetails(),
            getSubjects(),
            BiFunction { t1, t2 ->
                createSchool(t1, t2)
            }
        )
    }

    private fun createSchool(detail: SchoolDetail, subjects: List<SchoolSubject>): School {
        return School(detail, subjects)
    }

    private fun getDetails(): Observable<SchoolDetail> {
        return repository.getSchoolDetail()
    }

    private fun getSubjects(): Observable<List<SchoolSubject>> {
        return repository.getSchoolSubjects()
    }
}