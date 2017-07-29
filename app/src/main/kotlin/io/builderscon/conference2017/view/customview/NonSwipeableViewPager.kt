package io.builderscon.conference2017.view.customview

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NonSwipeableViewPager(context: Context?, attrs: AttributeSet?) : ViewPager(context, attrs) {
    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }
}
