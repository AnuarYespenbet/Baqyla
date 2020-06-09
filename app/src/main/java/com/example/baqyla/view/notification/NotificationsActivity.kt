package com.example.baqyla.view.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baqyla.R
import com.example.baqyla.view.ui.OnBackClickListener
import kotlinx.android.synthetic.main.activity_notifications.*

class NotificationsActivity : AppCompatActivity(), OnBackClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        notifications_toolbar.setOnBackClickListener(this)

    }

    override fun onBackClick() {
        finish()
    }
}