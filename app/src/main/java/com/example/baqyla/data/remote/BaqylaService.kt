package com.example.baqyla.data.remote

import com.example.baqyla.data.model.Lesson
import com.example.baqyla.data.model.Reason
import com.example.baqyla.data.model.Subject
import com.example.baqyla.data.model.User
import com.example.baqyla.data.remote.response.ExistPassword
import com.example.baqyla.data.remote.response.ExistUser
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface BaqylaService {
    @GET("/open-api/is-user-exists")
    fun isUserExists(@Query("username") id: String): Single<ExistUser>

    @GET("/open-api/is-password-exists")
    fun isPasswordExists(@Query("username") id: String): Single<ExistPassword>

    @POST("/open-api/set-password")
    fun setPassword(
        @Body data: Map<String, @JvmSuppressWildcards Any>
    ): Single<ResponseBody>

    @POST("/login")
    @FormUrlEncoded
    fun login(
        @Field("username") id: String,
        @Field("password") password: String
    ): Single<User>

    @GET("/lessons/get-with-period")
    fun getAllLessons(
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Observable<List<Lesson>>

    @GET("/lessons/get")
    fun getLessonsByChildId(
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
        @Query("childId") childId: Int
    ): Observable<List<Lesson>>

    @GET("/inform/subjects")
    fun getSubjects(): Observable<List<Subject>>

    @GET("/inform/lessons")
    fun getLessons(
        @Query("subjectId") subjectId: Int
    ): Observable<List<Lesson>>

    @GET("inform/reasons")
    fun getReason(): Observable<List<Reason>>
}