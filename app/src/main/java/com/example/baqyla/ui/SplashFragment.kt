package com.example.baqyla.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.baqyla.R

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
                    (activity as BaseActivity).setLoggedIn(true)
                    val intent = Intent(activity, NavigationActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    activity?.finish()

                    //findNavController().navigate(R.id.action_splashFragment_to_idFragment)
                }
                else -> {
                    findNavController().navigate(R.id.action_splashFragment_to_idFragment)
                }
            }
        }, 1000)
    }
}