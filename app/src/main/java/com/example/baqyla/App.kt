package com.example.baqyla

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class App : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}