package com.example.baqyla.view.main.mail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.baqyla.R
import kotlinx.android.synthetic.main.dialog_mail_success.*

class MailSuccessDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_mail_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

}