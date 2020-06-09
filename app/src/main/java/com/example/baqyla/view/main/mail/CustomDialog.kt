package com.example.baqyla.view.main.mail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.baqyla.R
import kotlinx.android.synthetic.main.dialog_custom.*

class CustomDialog(private val res: Int, private val text: String) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_custom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog_image_view.setImageResource(res)
        dialog_title_text_view.text = text
        dialog_close_text_view.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }
}