package com.example.baqyla.view.main.syllabus.calendar

import org.threeten.bp.LocalDate

interface OnDayClickListener {
    fun onDayClick(selectedDate: LocalDate?, oldDate: LocalDate?)
}