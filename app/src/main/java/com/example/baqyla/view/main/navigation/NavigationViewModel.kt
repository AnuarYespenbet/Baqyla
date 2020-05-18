package com.example.baqyla.view.main.navigation

import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.view.base.BaseViewModel

class NavigationViewModel : BaseViewModel() {
    fun logout() {
        LocalStore().removeObject(LocalStoreObjectType.CURRENT_USER)
    }
}