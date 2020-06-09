package com.example.baqyla.view.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.cardview.widget.CardView
import com.example.baqyla.R
import com.example.baqyla.utils.toast
import kotlinx.android.synthetic.main.custom_spinner.view.*

class CustomSpinner(context: Context, attrs: AttributeSet) : CardView(context, attrs) {
    private var title: String = ""

    init {
        View.inflate(context, R.layout.custom_spinner, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner)
        title = attributes.getString(R.styleable.CustomSpinner_spinner_text) ?: ""
        spinner_icon.setImageResource(
            attributes.getResourceId(
                R.styleable.CustomSpinner_spinner_icon,
                -1
            )
        )
        spinner_container.setOnClickListener {
            spinner.performClick()
        }
        attributes.recycle()
    }

    fun setItems(items: List<String>) {
        val itemsWithTitle = ArrayList<String>()
        itemsWithTitle.add(title)
        itemsWithTitle.addAll(items)

        val arrayAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            itemsWithTitle
        )
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                arg3: Long
            ) {
                context.toast("selected item: $position")
            }
        }
    }
}