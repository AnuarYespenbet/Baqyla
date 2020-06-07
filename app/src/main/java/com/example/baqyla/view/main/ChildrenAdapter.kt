package com.example.baqyla.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R
import com.example.baqyla.data.local.LocalStore
import com.example.baqyla.data.local.LocalStoreObjectType
import com.example.baqyla.data.model.Child
import com.example.baqyla.utils.loadUrl
import kotlinx.android.synthetic.main.item_child.view.*

class ChildrenAdapter(private var children: List<Child>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_child, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ChildViewHolder).bind(children[position])
    }

    override fun getItemCount(): Int {
        return children.size
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(child: Child) {
            itemView.apply {
                child_image.loadUrl(child.profilePhoto)
                val fullName = "${child.name} ${child.surname}"
                child_name.text = fullName

                if (child == LocalStore().get(LocalStoreObjectType.SELECTED_CHILD, Child::class.java)) {
                    child_container.alpha = 0.5f
                    child_container.isEnabled = false
                } else {
                    child_container.alpha = 1f
                    child_container.isEnabled = true
                }

                setOnClickListener {
                    mOnChildSelectedListener?.onChildSelected(child)
                }
            }
        }
    }

    private var mOnChildSelectedListener: OnChildSelectedListener? = null
    fun setOnChildSelectedListener(onChildSelectedListener: OnChildSelectedListener) {
        mOnChildSelectedListener = onChildSelectedListener
    }

    interface OnChildSelectedListener {
        fun onChildSelected(child: Child)
    }
}