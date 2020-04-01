package com.example.baqyla

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item_subjects.view.*

class SubjectsAdapter(private val subjects: ArrayList<Subject>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_subjects, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = subjects[position]
        (holder as ViewHolder).bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Subject) {
            itemView.subject_name.text = item.name

            if (item.active) {
                itemView.subject_name.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
                itemView.indicator.visibility = View.VISIBLE
            } else {
                itemView.subject_name.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.grey
                    )
                )
                itemView.indicator.visibility = View.INVISIBLE
            }

            itemView.subject_container.setOnClickListener {

            }


        }
    }

    private var mSubjectListener: SubjectListener? = null
    fun setListener(listener: SubjectListener) {
        mSubjectListener = listener
    }

    interface SubjectListener {
        fun onClick()
    }

}