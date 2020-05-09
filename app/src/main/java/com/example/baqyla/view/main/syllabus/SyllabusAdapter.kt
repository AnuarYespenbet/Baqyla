package com.example.baqyla.view.main.syllabus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R
import com.example.baqyla.data.model.Lesson
import com.example.baqyla.utils.getColorCompat
import kotlinx.android.synthetic.main.row_item_syllabus.view.*
import org.threeten.bp.format.DateTimeFormatter

class SyllabusAdapter : RecyclerView.Adapter<SyllabusAdapter.ViewHolder>() {

    val lessons = mutableListOf<Lesson>()
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_syllabus, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lessons[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = lessons.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Lesson) {
            itemView.start_time.text = formatter.format(item.dateTime)
            itemView.end_time.text = formatter.format(item.dateTime)
            itemView.divider.setBackgroundColor(itemView.context.getColorCompat(item.color))
            itemView.subject_name.setTextColor(itemView.context.getColorCompat(item.color))
            itemView.subject_name.text = item.subject.name
        }

    }
}