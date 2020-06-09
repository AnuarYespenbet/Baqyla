package com.example.baqyla.data.remote

import com.example.baqyla.data.model.*
import com.example.baqyla.data.remote.response.ExistPassword
import com.example.baqyla.data.remote.response.ExistUser
import com.example.baqyla.data.remote.response.Inform
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

    @POST("/logout")
    fun logout(): Single<ResponseBody>

    @GET("/lessons/get")
    fun getLessonsByChildId(
        @Query("childId") childId: Int,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Observable<List<Lesson>>

    @GET("/lessons/attendance")
    fun getAttendanceByChildId(
        @Query("childId") childId: Int,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Observable<List<Attendance>>

    @GET("/inform/subjects")
    fun getSubjects(): Observable<List<Subject>>

    @GET("/inform/lessons")
    fun getLessons(
        @Query("subjectId") subjectId: Int
    ): Observable<List<Lesson>>

    @GET("/inform/reasons")
    fun getReason(): Observable<List<Reason>>

    @POST("/inform/add")
    fun sendInform(
        @Body data: Map<String, @JvmSuppressWildcards Any>
    ): Single<Inform>

    @GET("/inform/about-school")
    fun getSchoolDetail(): Observable<SchoolDetail>

    @GET("/inform/children2subjects")
    fun getSchoolSubjects(): Observable<List<SchoolSubject>>

    @GET("/inform/statistics")
    fun getStatistics(
        @Query("childId") childId: Int,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Single<Statistic>
}