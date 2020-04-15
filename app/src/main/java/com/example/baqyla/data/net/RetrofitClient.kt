package com.example.baqyla.data.net

import com.example.baqyla.data.models.User
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val baqylaApi: BaqylaApi

    companion object {
        private const val BASE_URL = "http://185.231.153.76:8080/"
    }

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.addInterceptor(httpLoggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        baqylaApi = retrofit.create(BaqylaApi::class.java)

    }

    fun isUserExists(username: String): Observable<Response<ResponseBody>> {
        return baqylaApi.isUserExists(username)
    }

    fun isPasswordExists(username: String): Observable<Response<ResponseBody>> {
        return baqylaApi.isPasswordExists(username)
    }

    fun setPassword(data: HashMap<String, Any>): Observable<Response<ResponseBody>> {
        return baqylaApi.setPassword(data)
    }

    fun login(username: String, password: String): Observable<Response<User>> {
        return baqylaApi.login(username, password)
    }
}