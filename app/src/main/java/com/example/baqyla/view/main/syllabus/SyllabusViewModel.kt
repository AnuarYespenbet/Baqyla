package com.example.baqyla.view.main.syllabus

import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.User
import com.example.baqyla.data.repository.LessonsRepository
import com.example.baqyla.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SyllabusViewModel : BaseViewModel() {
    private val repository = LessonsRepository(api)
    val user = LocalStore().get(LocalStoreObjectType.CURRENT_USER, User::class.java)

    fun getLessonsByChildId(dateFrom: String, dateTo: String, childId: Int) {
        addDisposable(
            repository.getLessonsByChildId(dateFrom, dateTo, childId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.e(it.toString())
                }, {
                    Timber.e(it)
                })
        )
    }
}