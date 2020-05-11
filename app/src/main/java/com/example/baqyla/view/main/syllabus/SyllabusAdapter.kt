package com.example.baqyla.view.main.syllabus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R
import com.example.baqyla.data.model.Lesson
import com.example.baqyla.utils.getColorCompat
import com.example.baqyla.utils.invisible
import com.example.baqyla.utils.visible
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
            itemView.apply {
                if (item.time != null) {
                    start_time.text = formatter.format(item.time)
                    val time = item.time.hour.plus(1)
                    val endText = "$time:00"
                    end_time.text = endText
                    item.color?.let { color ->
                        divider.setBackgroundColor(itemView.context.getColorCompat(color))
                        subject_name.setTextColor(itemView.context.getColorCompat(color))
                    }
                    subject_name.text = item.subject?.name
                    start_time.visible()
                    end_time.visible()
                    subject_name.visible()
                    divider.visible()
                } else {
                    start_time.invisible()
                    end_time.invisible()
                    subject_name.invisible()
                    divider.invisible()
                }
            }
        }

    }
}