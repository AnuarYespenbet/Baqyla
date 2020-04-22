package com.example.baqyla.data.net

import com.example.baqyla.App
import com.example.baqyla.utils.Preferences
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies = HashSet<String>()
            originalResponse.headers("Set-Cookie").forEach { header ->
                cookies.add(header)
            }

            Preferences.getDefaultPreferences(App.applicationContext()).edit()
                .putStringSet(Preferences.PREF_COOKIES, cookies)
                .apply()
        }

        return originalResponse
    }
}