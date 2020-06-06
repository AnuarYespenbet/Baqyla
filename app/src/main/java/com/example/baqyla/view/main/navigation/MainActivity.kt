package com.example.baqyla.view.main.navigation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.baqyla.R
import com.example.baqyla.view.entry.EntryActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var navigationViewModel: NavigationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment2) as NavHostFragment? ?: return
        val navController = host.navController
        setUpBottomNav(navController)

        logout.setOnClickListener {
            showDialog()
        }
    }

    private fun navigateToEntry() {
        startActivity(Intent(this, EntryActivity::class.java))
        finish()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.logout_text))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            navigationViewModel.logout()
            navigateToEntry()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }
}