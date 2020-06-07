package com.example.baqyla.view.main.syllabus

import androidx.lifecycle.MutableLiveData
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.*
import com.example.baqyla.data.repository.LessonsRepository
import com.example.baqyla.utils.Event
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SyllabusViewModel : BaseViewModel() {
    private val repository = LessonsRepository(api)
    val child = LocalStore().get(LocalStoreObjectType.SELECTED_CHILD, Child::class.java)

    fun getLessonsAndAttendance(
        childId: Int,
        dateFrom: String,
        dateTo: String
    ): MutableLiveData<Event<Syllabus>> {
        val liveData = MutableLiveData<Event<Syllabus>>()
        addDisposable(
            zip(childId, dateFrom, dateTo)
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

    private fun zip(childId: Int, dateFrom: String, dateTo: String): Observable<Syllabus> {
        return Observable.zip(
            getLessons(childId, dateFrom, dateTo),
            getAttendances(childId, dateFrom, dateTo),
            BiFunction { t1, t2 ->
                createSyllabus(t1, t2)
            }
        )
    }

    private fun createSyllabus(lessons: List<Lesson>, attendances: List<Attendance>): Syllabus {
        return Syllabus(lessons, attendances)
    }

    private fun getLessons(
        childId: Int,
        dateFrom: String,
        dateTo: String
    ): Observable<List<Lesson>> {
        return repository.getLessonsByChildId(childId, dateFrom, dateTo)
    }

    private fun getAttendances(
        childId: Int,
        dateFrom: String,
        dateTo: String
    ): Observable<List<Attendance>> {
        return repository.getAttendanceByChildId(childId, dateFrom, dateTo)
    }
}