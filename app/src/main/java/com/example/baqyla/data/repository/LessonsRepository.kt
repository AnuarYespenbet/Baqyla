package com.example.baqyla.data.repository

import com.example.baqyla.data.model.Attendance
import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.remote.BaqylaService
import io.reactivex.Observable

class LessonsRepository(private val webService: BaqylaService) {
    fun getLessonsByChildId(
        childId: Int,
        dateFrom: String,
        dateTo: String
    ): Observable<List<Lesson>> {
        return webService.getLessonsByChildId(childId, dateFrom, dateTo)
    }

    fun getAttendanceByChildId(
        childId: Int,
        dateFrom: String,
        dateTo: String
    ): Observable<List<Attendance>> {
        return webService.getAttendanceByChildId(childId, dateFrom, dateTo)
    }
}