package com.example.baqyla

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getPrefs(): SharedPreferences {
        return getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

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

}
