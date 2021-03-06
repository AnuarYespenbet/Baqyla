package com.example.baqyla.view.entry.splash

import androidx.lifecycle.ViewModel
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreBooleanType
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.local.LocalStoreStringType
import com.example.baqyla.data.model.User

class SplashViewModel : ViewModel() {
    val onBoardingCompleted = LocalStore().get(LocalStoreBooleanType.ON_BOARDING_COMPLETED) ?: false
    val user = LocalStore().get(LocalStoreObjectType.CURRENT_USER, User::class.java)
    val id = LocalStore().get(LocalStoreStringType.USER_ID)
}