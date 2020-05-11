package com.example.baqyla.view.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.baqyla.R

class CustomDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable =
        ContextCompat.getDrawable(context, R.drawable.divider_item_decoration)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.adapter?.itemCount
        childCount?.let {
            for (i in 0 until childCount) {
                if (i == childCount - 1) {
                    continue
                }

                val child = parent.getChildAt(i)
                val params: RecyclerView.LayoutParams =
                    child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + mDivider.intrinsicHeight
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
        }

    }
}