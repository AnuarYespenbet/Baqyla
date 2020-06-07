package com.example.baqyla.view.main.school

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R
import com.example.baqyla.data.model.SchoolSubject
import kotlinx.android.synthetic.main.layout_info.view.*

class SubjectInfoAdapter(private var subjectsInfo: List<SchoolSubject>) :
    RecyclerView.Adapter<SubjectInfoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(schoolSubject: SchoolSubject) {
            itemView.text_count.text = schoolSubject.count.toString()
            val staticText = itemView.context.getString(R.string.students_count_for_subject)
            val textInfo =
                "$staticText ${schoolSubject.subject}"
            val spannable = SpannableString(textInfo)
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#3A5CB0")),
                staticText.length,
                textInfo.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                staticText.length,
                textInfo.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            itemView.text_info.text = spannable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_info, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subjectsInfo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subjectInfo = subjectsInfo[position]
        holder.bind(subjectInfo)
    }
}