package com.example.baqyla.view.entry.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.baqyla.R
import com.example.baqyla.data.model.User
import com.example.baqyla.utils.Status
import com.example.baqyla.utils.gone
import com.example.baqyla.utils.visible
import com.example.baqyla.view.main.MainActivity
import com.example.baqyla.view.on_boarding.OnBoardingActivity
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber

class LoginFragment : Fragment(), TextWatcher {
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        id_edit.keyListener = null
        id_edit.setText(loginViewModel.id)

        password_edit.addTextChangedListener(this)
        next_btn.setOnClickListener { onNextClick() }
        change_id.setOnClickListener { onChangeIdClick() }
    }

    override fun afterTextChanged(p0: Editable?) {
        next_btn.isEnabled = password_edit.text.isNotEmpty()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        setBgEdit(password_edit)
    }

    private fun setBgEdit(editText: EditText) {
        editText.background =
            if (editText.text.toString().isEmpty())
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_grey)
            else ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_green)
    }

    private fun onNextClick() {
        val id = id_edit.text.toString()
        val password = password_edit.text.toString()
        loginViewModel.login(id, password).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    onLoading()
                }
                Status.SUCCESS -> {
                    onSuccess(it.data!!)
                }
                Status.ERROR -> {
                    onError(it.error ?: "error")
                }
            }
        })
    }

    private fun onChangeIdClick() {
        loginViewModel.changeId()
        findNavController().navigate(R.id.action_loginFragment_to_idFragment)
    }

    private fun onLoading() {
        progress_bar.visible()
        next_btn.gone()
    }

    private fun onSuccess(user: User) {
        loginViewModel.saveUser(user)
        loginViewModel.subscribeToFirebaseNotification()
        if (loginViewModel.onBoardingCompleted) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        } else {
            startActivity(Intent(requireContext(), OnBoardingActivity::class.java))
        }
        activity?.finish()
    }

    private fun onError(error: String) {
        Timber.e(error)
        error_text.text = getString(R.string.error_login)
        error_text.visible()
        next_btn.visible()
        progress_bar.gone()
    }
}