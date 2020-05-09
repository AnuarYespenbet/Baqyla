package com.example.baqyla.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.baqyla.App
import com.google.gson.Gson

class LocalStore {
    private val preference: SharedPreferences
    private var editor: SharedPreferences.Editor? = null
    private val storeName = "LocalStore"
    private val context = App.applicationContext()

    init {
        preference = context.getSharedPreferences(storeName, Context.MODE_PRIVATE)
    }

    fun save(string: String, type: LocalStoreStringType) {
        editor = preference.edit()
        editor?.putString(type.name, string)
        editor?.apply()
    }

    fun get(type: LocalStoreStringType): String? {
        return preference.getString(type.name, null)
    }

    fun <T> save(gsonObject: T, type: LocalStoreObjectType) {
        editor = preference.edit()
        val gson = Gson()
        val jsonString = gson.toJson(gsonObject)
        editor?.putString(type.name, jsonString)
        editor?.apply()
    }

    fun <T> get(type: LocalStoreObjectType, classOfT: Class<T>): T? {
        val jsonString = preference.getString(type.name, null)
        return Gson().fromJson(jsonString, classOfT)
    }

    fun removeString(type: LocalStoreStringType) {
        editor = preference.edit()
        editor?.remove(type.name)
        editor?.apply()
    }

    fun removeObject(type: LocalStoreObjectType) {
        editor = preference.edit()
        editor?.remove(type.name)
        editor?.apply()
    }

    fun removeAllValues() {
        removeString(LocalStoreStringType.USER_ID)
        removeObject(LocalStoreObjectType.CURRENT_USER)
    }
}

enum class LocalStoreStringType {
    USER_ID
}

enum class LocalStoreObjectType {
    CURRENT_USER
}