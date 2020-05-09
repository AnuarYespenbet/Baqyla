package com.example.baqyla.data.remote

val client = NetworkClient()

object NetworkService {
    private val service = client.getRetrofit()
    fun createApiService(): BaqylaService {
        return service.create(BaqylaService::class.java)
    }
}