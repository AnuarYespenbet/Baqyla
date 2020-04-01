package com.example.baqyla

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            when {
                (activity as MainActivity).isLoggedIn() -> {
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }
                else -> {
                    findNavController().navigate(R.id.action_splashFragment_to_idFragment)
                }
            }
        }, 1000)
    }
}