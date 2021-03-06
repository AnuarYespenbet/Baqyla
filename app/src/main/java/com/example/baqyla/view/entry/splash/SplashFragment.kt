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
import com.example.baqyla.view.main.MainActivity
import com.example.baqyla.view.on_boarding.OnBoardingActivity

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
            splashViewModel.id == null -> {
                findNavController().navigate(R.id.action_splashFragment_to_idFragment)
            }
            splashViewModel.user == null -> {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
            !splashViewModel.onBoardingCompleted -> {
                startActivity(Intent(requireContext(), OnBoardingActivity::class.java))
                requireActivity().finish()
            }
            else -> {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}