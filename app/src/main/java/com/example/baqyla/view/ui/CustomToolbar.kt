package com.example.baqyla.view.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.example.baqyla.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class CustomToolbar(context: Context, attrs: AttributeSet) : CardView(context, attrs) {
    private var mOnBackClickListener: OnBackClickListener? = null
    fun setOnBackClickListener(onBackClickListener: OnBackClickListener) {
        mOnBackClickListener = onBackClickListener
    }

    init {
        View.inflate(context, R.layout.custom_toolbar, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        toolbar_title_text_view.text =
            attributes.getText(R.styleable.CustomToolbar_title_text_view)
        toolbar_back_text_view.setOnClickListener {
            mOnBackClickListener?.onBackClick()
        }
        attributes.recycle()
    }
}

interface OnBackClickListener {
    fun onBackClick()
}