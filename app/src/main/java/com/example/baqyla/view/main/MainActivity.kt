package com.example.baqyla.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.baqyla.R
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.Child
import com.example.baqyla.utils.Status
import com.example.baqyla.utils.toast
import com.example.baqyla.view.entry.EntryActivity
import com.example.baqyla.view.notification.NotificationsActivity
import com.example.baqyla.view.ui.OnBackClickListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), ChildrenAdapter.OnChildSelectedListener,
    OnBackClickListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ChildrenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.saveSelectedChild(mainViewModel.children[0])

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment2) as NavHostFragment? ?: return
        val navController = host.navController
        setUpBottomNav(navController)


        menu.setOnClickListener {
            drawer_layout.open()
        }

        notifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        log_out.setOnClickListener {
            showDialog()
        }

        nav_view_toolbar.setOnBackClickListener(this)

        adapter = ChildrenAdapter(mainViewModel.children)
        adapter.setOnChildSelectedListener(this)
        children_rv.adapter = adapter
    }

    private fun navigateToEntry() {
        startActivity(Intent(this, EntryActivity::class.java))
        finish()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.logout_text))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            mainViewModel.logout().observe(this, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        //todo
                    }
                    Status.SUCCESS -> {
                        LocalStore().removeObject(LocalStoreObjectType.CURRENT_USER)
                        navigateToEntry()
                    }
                    Status.ERROR -> {
                        toast(it.error ?: "")
                    }
                }
            })
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    override fun onChildSelected(child: Child) {
        mainViewModel.saveSelectedChild(child)
        drawer_layout.close()
        bottom_nav_view.selectedItemId = bottom_nav_view.selectedItemId
        adapter.notifyDataSetChanged()
    }

    override fun onBackClick() {
        drawer_layout.close()
    }
}