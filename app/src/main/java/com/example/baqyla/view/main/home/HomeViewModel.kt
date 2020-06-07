package com.example.baqyla.view.main.home

import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.Child
import com.example.baqyla.view.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    val child = LocalStore().get(LocalStoreObjectType.SELECTED_CHILD, Child::class.java)
}