package com.example.baqyla.view.main.school

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
            val textInfo =
                "${itemView.context.getString(R.string.students_count_for_subject)} ${schoolSubject.subject}"
            itemView.text_info.text = textInfo
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