package com.example.baqyla.data.repository

import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.model.Reason
import com.example.baqyla.data.model.Subject
import com.example.baqyla.data.remote.BaqylaService
import io.reactivex.Observable

class MailRepository(private val webService: BaqylaService) {
    fun getSubjects(): Observable<List<Subject>> {
        return webService.getSubjects()
    }
    fun getLessons(subjectId: Int): Observable<List<Lesson>> {
        return webService.getLessons(subjectId)
    }

    fun getReasons(): Observable<List<Reason>> {
        return webService.getReason()
    }
}