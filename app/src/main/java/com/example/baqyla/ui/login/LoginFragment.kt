package com.example.baqyla.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.baqyla.ui.BaseActivity
import com.example.baqyla.utils.Constants
import com.example.baqyla.ui.NavigationActivity
import com.example.baqyla.R
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

        id_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                setBgEdit(id_edit, p0?.length ?: 0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, count: Int) {}

        })
        password_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                setBgEdit(password_edit, p0?.length ?: 0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, count: Int) {}

        })

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

    private fun setBgEdit(view: View, count: Int) {
        Log.d("count", count.toString())
        val background =
            if (count == 0) R.drawable.bg_rounded_edit_normal else R.drawable.bg_rounded_edit_focused
        view.background = ContextCompat.getDrawable(context!!, background)
        checkValid()
    }

    private fun checkValid() {
        next_btn.isEnabled =
            !id_edit.text.isNullOrEmpty() && !password_edit.text.isNullOrEmpty()
    }
}