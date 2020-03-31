package com.example.baqyla.id

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.baqyla.R
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
            override fun afterTextChanged(p0: Editable?) {
                checkValid()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        next_btn.setOnClickListener {
            viewModel.isUserExists(id_edit.text.toString()).observe(this, Observer {
                Log.d(IdFragment::class.java.name, it.toString())
                if (it) {
                    findNavController().navigate(R.id.action_idFragment_to_loginFragment)
                } else {
                    findNavController().navigate(R.id.action_idFragment_to_passwordFragment)
                }
            })
        }
    }

    private fun checkValid() {
        next_btn.isEnabled = id_edit.text.isNotEmpty()
    }
}