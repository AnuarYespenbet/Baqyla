package com.example.baqyla.utils

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    const val PREF_COOKIES = "pref_cookies"

    fun getDefaultPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
}