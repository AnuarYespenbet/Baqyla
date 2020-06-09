package com.example.baqyla.view.entry.id

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
import androidx.navigation.fragment.findNavController
import com.example.baqyla.R
import com.example.baqyla.data.remote.response.ExistPassword
import com.example.baqyla.data.remote.response.ExistUser
import com.example.baqyla.utils.Status
import com.example.baqyla.utils.gone
import com.example.baqyla.utils.invisible
import com.example.baqyla.utils.visible
import kotlinx.android.synthetic.main.fragment_id.*
import timber.log.Timber

class IdFragment : Fragment(), TextWatcher {
    private lateinit var idViewModel: IdViewModel
    private var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_id, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idViewModel = IdViewModel()
        id_edit.addTextChangedListener(this)
        next_btn.setOnClickListener { onNextClick() }
    }

    override fun afterTextChanged(p0: Editable?) {
        next_btn.isEnabled = id_edit.text.isNotEmpty()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        setBgEdit(id_edit)
    }

    private fun setBgEdit(editText: EditText) {
        editText.background =
            if (editText.text.toString().isEmpty())
                ContextCompat.getDrawable(requireContext(), R.drawable.rounded_shape_white)
            else {
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_green)
            }
    }

    private fun onNextClick() {
        id = id_edit.text.toString()
        idViewModel.isUserExists(id).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> userExistsLoading()
                Status.SUCCESS -> userExistsSuccess(it.data!!)
                Status.ERROR -> showError(it.error ?: "error")
            }
        })
    }

    private fun userExistsLoading() {
        error_text.gone()
        progress_bar.visible()
        next_btn.gone()
    }

    private fun userExistsSuccess(existUser: ExistUser) {
        if (existUser.isUserExists) {
            idViewModel.isPasswordExists(id).observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> passwordExistsLoading()
                    Status.SUCCESS -> passwordExistsSuccess(it.data!!)
                    Status.ERROR -> showError(it.error ?: "error")
                }
            })
        } else {
            showError(getString(R.string.error_id))
        }
    }

    private fun passwordExistsLoading() {
        Timber.d("password exists loading")
    }

    private fun passwordExistsSuccess(existPassword: ExistPassword) {
        if (existPassword.isPasswordExists) {
            idViewModel.saveId(id)
            findNavController().navigate(R.id.action_idFragment_to_loginFragment)
        } else {
            val action = IdFragmentDirections.actionIdFragmentToPasswordFragment(id)
            findNavController().navigate(action)
        }
    }

    private fun showError(error: String) {
        Timber.e(error)
        id_edit.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_red)
        error_text.text = error
        error_text.visible()
        progress_bar.gone()
        next_btn.visible()
    }
}