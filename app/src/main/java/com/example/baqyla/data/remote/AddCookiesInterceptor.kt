package com.example.baqyla.data.remote

import com.example.baqyla.App
import com.example.baqyla.utils.Preferences
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = Preferences.getDefaultPreferences(App.applicationContext())
            .getStringSet(Preferences.PREF_COOKIES, HashSet())

        preferences?.forEach { cookie ->
            builder.addHeader("Cookie", cookie)
            Timber.d("Adding Header $cookie")
        }

        return chain.proceed(builder.build())
    }
}