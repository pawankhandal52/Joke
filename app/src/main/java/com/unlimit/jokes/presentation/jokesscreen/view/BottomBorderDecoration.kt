package com.unlimit.jokes.presentation.jokesscreen.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class BottomBorderDecoration(borderColor: Int, borderWidth: Int) :
    ItemDecoration() {
    private val borderPaint: Paint = Paint()
    private val borderWidth: Int

    init {
        borderPaint.color = borderColor
        this.borderWidth = borderWidth
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = borderWidth
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val top = child.bottom
            val bottom = top + borderWidth
            c.drawRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                borderPaint
            )
        }
    }
}
