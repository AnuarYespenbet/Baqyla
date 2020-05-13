package com.example.baqyla.view.main.school

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R
import com.example.baqyla.data.model.SubjectInfo
import kotlinx.android.synthetic.main.layout_info.view.*

class SubjectInfoAdapter(private var subjectsInfo: List<SubjectInfo>) :
    RecyclerView.Adapter<SubjectInfoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(subjectInfo: SubjectInfo) {
            itemView.text_count.text = subjectInfo.count.toString()
            val textInfo =
                "${itemView.context.getString(R.string.students_count_for_subject)} ${subjectInfo.subjectName}"
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