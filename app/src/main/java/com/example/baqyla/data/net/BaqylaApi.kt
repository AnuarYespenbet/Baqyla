package com.example.baqyla.data.net

import com.example.baqyla.data.User
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BaqylaApi {
    @GET("/open-api/is-user-exists")
    fun isUserExists(@Query("username") username: String): Observable<Response<ResponseBody>>

    @GET("/open-api/is-password-exists")
    fun isPasswordExists(@Query("username") username: String): Observable<Response<ResponseBody>>

    @POST("/open-api/set-password")
    fun setPassword(
        @Body data: Map<String, @JvmSuppressWildcards Any>
    ): Observable<Response<ResponseBody>>

    @POST("/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<Response<User>>
}