package com.example.baqyla.data.repository

import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.remote.BaqylaService
import io.reactivex.Observable

class LessonsRepository(private val webService: BaqylaService) {
    fun getLessons(dateFrom: String, dateTo: String, subjectId: Int): Observable<List<Lesson>> {
        return webService.getLessons(dateFrom, dateTo, subjectId)
    }
}