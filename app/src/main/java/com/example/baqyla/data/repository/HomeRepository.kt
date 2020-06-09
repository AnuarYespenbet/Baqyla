package com.example.baqyla.data.repository

import com.example.baqyla.data.model.Statistic
import com.example.baqyla.data.remote.BaqylaService
import io.reactivex.Single

class HomeRepository(private val webService: BaqylaService) {
    fun getStatistics(childId: Int, dateFrom: String, dateTo: String): Single<Statistic> {
        return webService.getStatistics(childId, dateFrom, dateTo)
    }
}