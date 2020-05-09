package com.example.baqyla.view.entry.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.baqyla.R
import com.example.baqyla.view.main.NavigationActivity

class SplashFragment : Fragment() {
    private lateinit var splashViewModel: SplashViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        when {
            splashViewModel.user != null -> {
                startActivity(Intent(requireContext(), NavigationActivity::class.java))
            }
            splashViewModel.id != null -> {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
            else -> {
                findNavController().navigate(R.id.action_splashFragment_to_idFragment)
            }
        }
    }
}