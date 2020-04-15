package com.example.baqyla.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.baqyla.utils.Constants

abstract class BaseActivity : AppCompatActivity() {

    fun isFirstTime(): Boolean {
        return getPrefs().getBoolean(Constants.FIRST_TIME, true)
    }

    fun setFirstTime(firstTime: Boolean) {
        getPrefs().edit().putBoolean(Constants.FIRST_TIME, firstTime).apply()
    }

    fun isLoggedIn(): Boolean {
        return getPrefs().getBoolean(Constants.LOGGED_IN, false)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        getPrefs().edit().putBoolean(Constants.LOGGED_IN, loggedIn).apply()
    }

    fun setUsername(username: String) {
        getPrefs().edit().putString(Constants.USERNAME, username).apply()
    }

    fun getUsername(): String? {
        return getPrefs().getString(Constants.USERNAME, "")
    }

    private fun getPrefs(): SharedPreferences {
        return getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}