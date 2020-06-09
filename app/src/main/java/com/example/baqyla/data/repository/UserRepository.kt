package com.example.baqyla.data.repository

import com.example.baqyla.data.model.User
import com.example.baqyla.data.remote.BaqylaService
import com.example.baqyla.data.remote.response.ExistPassword
import com.example.baqyla.data.remote.response.ExistUser
import io.reactivex.Single
import okhttp3.ResponseBody

class UserRepository(private val webService: BaqylaService) {
    fun isUserExists(id: String): Single<ExistUser> {
        return webService.isUserExists(id)
    }

    fun isPasswordExists(id: String): Single<ExistPassword> {
        return webService.isPasswordExists(id)
    }

    fun setPassword(hashMap: HashMap<String, Any>): Single<ResponseBody> {
        return webService.setPassword(hashMap)
    }

    fun login(id: String, password: String): Single<User> {
        return webService.login(id, password)
    }

    fun logout(): Single<ResponseBody> {
        return webService.logout()
    }
}