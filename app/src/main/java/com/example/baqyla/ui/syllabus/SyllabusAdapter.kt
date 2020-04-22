package com.example.baqyla.ui.syllabus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R
import com.example.baqyla.data.models.LessonsResponse
import kotlinx.android.synthetic.main.row_item_syllabus.view.*

class SyllabusAdapter : RecyclerView.Adapter<SyllabusAdapter.ViewHolder>() {

    private var items: List<LessonsResponse> = ArrayList()

    fun setItems(items: List<LessonsResponse>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_syllabus, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: LessonsResponse) {
            itemView.start_time.text = item.startTime
            itemView.end_time.text = item.endTime
            itemView.subject_name.text = item.subject.name
            itemView.teacher_name.text = item.subject.teacher
        }

    }
}