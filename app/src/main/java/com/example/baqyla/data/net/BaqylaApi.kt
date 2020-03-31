package com.example.baqyla.data.net

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BaqylaApi {
    @GET("/open-api/is-user-exists")
    fun isUserExists(@Query("username") username: String): Observable<Response<ResponseBody>>
}