package com.example.baqyla.view.main.home

import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.User
import com.example.baqyla.view.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    private val user = LocalStore().get(LocalStoreObjectType.CURRENT_USER, User::class.java)
    val child = if (!user?.children.isNullOrEmpty()) user?.children?.get(0) else null
}