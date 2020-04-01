package com.example.baqyla.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.baqyla.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next_btn.setOnClickListener {
            val username = id_edit.text.toString()
            val password = password_edit.text.toString()
            viewModel.login(username, password).observe(this, Observer {
                if (it != null) {
                    (activity as BaseActivity).setLoggedIn(true)
                    val intent = Intent(activity, NavigationActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra(Constants.USER, it)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Неправильный логин или пароль", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}