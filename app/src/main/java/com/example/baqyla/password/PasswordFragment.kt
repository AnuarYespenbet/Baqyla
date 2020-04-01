package com.example.baqyla.password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.baqyla.R
import com.example.baqyla.toast
import kotlinx.android.synthetic.main.fragment_password.*

class PasswordFragment : Fragment() {

    private val viewModel: PasswordViewModel by viewModels()
    private val args: PasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        password_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setBgEdit(password_edit, count)
            }
        })

        confirm_password_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setBgEdit(confirm_password_edit, count)
            }
        })

        previous_btn.setOnClickListener {
            findNavController().navigateUp()
        }

        next_btn.setOnClickListener {
            val hashMap = HashMap<String, Any>()

            hashMap["username"] = args.id
            hashMap["password"] = password_edit.text.toString()
            viewModel.setPassword(hashMap).observe(this, Observer {
                if (it) {
                    findNavController().navigate(R.id.action_passwordFragment_to_loginFragment)
                } else {
                    context?.toast("Ошибка")
                }
            })
        }
    }

    private fun setBgEdit(view: View, count: Int) {
        val background =
            if (count == 0) R.drawable.bg_rounded_edit_normal else R.drawable.bg_rounded_edit_focused
        view.background = ContextCompat.getDrawable(context!!, background)
        checkValid()
    }

    private fun checkValid() {
        val password = password_edit.text
        val confirmPassword = confirm_password_edit.text
        next_btn.isEnabled =
            !password.isNullOrEmpty() && !confirmPassword.isNullOrEmpty() && password.toString() == confirmPassword.toString()
    }
}