package com.example.baqyla.id

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.baqyla.R
import com.example.baqyla.toast
import kotlinx.android.synthetic.main.fragment_id.*

class IdFragment : Fragment() {

    private val viewModel: IdViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_id, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        id_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val background =
                    if (count == 0) R.drawable.bg_rounded_edit_normal else R.drawable.bg_rounded_edit_focused
                id_edit.background = ContextCompat.getDrawable(context!!, background)

                checkValid()
            }

        })
        next_btn.setOnClickListener {
            val id = id_edit.text.toString()
            viewModel.isUserExists(id).observe(this, Observer { userExists ->
                Log.d(IdFragment::class.java.name, it.toString())
                if (userExists) {
                    viewModel.isPasswordExists(id).observe(this, Observer { passwordExists ->
                        if (passwordExists) {
                            findNavController().navigate(R.id.action_idFragment_to_loginFragment)
                        } else {
                            val action = IdFragmentDirections.actionIdFragmentToPasswordFragment(id)
                            findNavController().navigate(action)
                        }
                    })
                } else {
                    context?.toast("Неверный ID")
                }
            })
        }
    }

    private fun checkValid() {
        next_btn.isEnabled = id_edit.text.isNotEmpty()
    }
}