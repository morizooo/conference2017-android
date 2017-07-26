package io.builderscon.conference2017.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import org.lucasr.twowayview.widget.TwoWayView

class TouchlessTwoWayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : TwoWayView(context, attrs, defStyle) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    fun forceToDispatchTouchEvent(ev: MotionEvent): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}