package com.example.baqyla.ui.syllabus

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.baqyla.data.LessonsRepository
import com.example.baqyla.data.models.LessonsResponse

class SyllabusViewModel : ViewModel() {
    private val repository: LessonsRepository = LessonsRepository()

    fun getLessons(
        dateFrom: String,
        dateTo: String,
        subjectId: Int
    ): LiveData<List<LessonsResponse>> {
        return repository.getLessons(dateFrom, dateTo, subjectId)
    }

}