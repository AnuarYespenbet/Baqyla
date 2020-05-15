package com.example.baqyla.data.repository

import com.example.baqyla.data.model.SchoolDetail
import com.example.baqyla.data.model.SchoolSubject
import com.example.baqyla.data.remote.BaqylaService
import io.reactivex.Observable

class SchoolRepository(private val webService: BaqylaService) {
    fun getSchoolDetail(): Observable<SchoolDetail> {
        return webService.getSchoolDetail()
    }

    fun getSchoolSubjects(): Observable<List<SchoolSubject>> {
        return webService.getSchoolSubjects()
    }
}