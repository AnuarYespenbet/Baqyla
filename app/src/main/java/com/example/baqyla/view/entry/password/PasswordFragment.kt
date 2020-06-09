package com.example.baqyla.view.entry.password

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
import androidx.navigation.fragment.navArgs
import com.example.baqyla.R
import com.example.baqyla.utils.Status
import com.example.baqyla.utils.gone
import com.example.baqyla.utils.visible
import kotlinx.android.synthetic.main.fragment_password.*
import timber.log.Timber

class PasswordFragment : Fragment(), TextWatcher {
    private lateinit var passwordViewModel: PasswordViewModel
    private val args by navArgs<PasswordFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passwordViewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)
        password_edit.addTextChangedListener(this)
        confirm_password_edit.addTextChangedListener(this)
        next_btn.setOnClickListener { onNextClick() }
    }

    override fun afterTextChanged(p0: Editable?) {
        next_btn.isEnabled = password_edit.text.toString().isNotEmpty()
                && confirm_password_edit.text.toString().isNotEmpty()
                && password_edit.text.toString() == confirm_password_edit.text.toString()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        setBgEdit(password_edit)
        setBgEdit(confirm_password_edit)
    }

    private fun setBgEdit(editText: EditText) {
        editText.background =
            if (editText.text.toString().isEmpty())
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_grey)
            else ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_green)
    }

    private fun onNextClick() {
        val hashMap = HashMap<String, Any>()
        val id = args.id
        hashMap["username"] = id
        hashMap["password"] = password_edit.text.toString()

        passwordViewModel.setPassword(hashMap).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    onLoading()
                }
                Status.SUCCESS -> {
                    onSuccess(id)
                }
                Status.ERROR -> {
                    onError(it.error ?: "error")
                }
            }
        })
    }

    private fun onLoading() {
        progress_bar.visible()
        next_btn.gone()
        error_text.gone()
    }

    private fun onSuccess(id: String) {
        passwordViewModel.saveId(id)
        findNavController().navigate(R.id.action_passwordFragment_to_loginFragment)
    }

    private fun onError(error: String) {
        Timber.e(error)
        progress_bar.gone()
        error_text.visible()
    }
}