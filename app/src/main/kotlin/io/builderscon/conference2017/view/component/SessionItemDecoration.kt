package io.builderscon.conference2017.view.component

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import io.builderscon.conference2017.view.adapter.timeline.TimelineViewHolder
import java.util.*

class SessionItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val timeLabelHeight = 64 // TODO: 適切な値をリソースに定義する or TypedArrayから取得
    private val backgroundPaint = Paint().apply { color = Color.GRAY } // TODO: 同上
    private val labelTextPaint = Paint().apply {
        color = Color.WHITE
        textSize = 14f
    } // TODO: 同上
    private val labelTextLeftMargin = 8f

    override fun onDraw(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State) {
        canvas?.let { c ->  parent?.let { drawVertical(c, it) } }
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val calendar = Calendar.getInstance()
        val childCount = parent.childCount
        (1 until childCount).forEach { index ->
            val child = parent.getChildAt(index)
            val prevChild = parent.getChildAt(index - 1)
            val viewHolder = parent.getChildViewHolder(child) as TimelineViewHolder
            val prevViewHolder = parent.getChildViewHolder(prevChild) as TimelineViewHolder
            val session = viewHolder.session
            val prevSession = prevViewHolder.session
            calendar.time = session.startsOn
            val sessionHour = calendar.get(Calendar.HOUR_OF_DAY)
            calendar.time = prevSession.startsOn
            val prevSessionHour = calendar.get(Calendar.HOUR_OF_DAY)

            if (sessionHour != prevSessionHour) {
                drawTimeLabel(c, prevChild, sessionHour)
            }
        }
    }

    private fun drawTimeLabel(canvas: Canvas, view: View, hour: Int) {
        val params = view.layoutParams as RecyclerView.LayoutParams
        val top = view.bottom + params.bottomMargin
        val bottom = top + timeLabelHeight

        canvas.drawRect(Rect(view.left, top, view.right, bottom), backgroundPaint)
        canvas.drawText("$hour:00", labelTextLeftMargin, (timeLabelHeight - labelTextPaint.textSize) / 2, labelTextPaint)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.set(Rect(0, 0, 0, timeLabelHeight))
    }
}
