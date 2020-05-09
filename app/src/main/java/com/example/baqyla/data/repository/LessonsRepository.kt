package com.example.baqyla.data.repository

import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.remote.BaqylaService
import io.reactivex.Observable

class LessonsRepository(private val webService: BaqylaService) {
    fun getAllLessons(dateFrom: String, dateTo: String): Observable<List<Lesson>> {
        return webService.getAllLessons(dateFrom, dateTo)
    }

    fun getLessonsByChildId(
        dateFrom: String,
        dateTo: String,
        childId: Int
    ): Observable<List<Lesson>> {
        return webService.getLessonsByChildId(dateFrom, dateTo, childId)
    }
}