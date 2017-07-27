package io.builderscon.conference2017.view

import android.view.View
import android.view.ViewTreeObserver


fun View.addOneTimeOnGlobalLayoutListener(onGlobalLayoutListener: OnGlobalLayoutListener) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (onGlobalLayoutListener.onGlobalLayout()) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    })
}

/**
 * @see ViewTreeObserver
 */
interface OnGlobalLayoutListener {

    /**
     * This emulates [ViewTreeObserver.OnGlobalLayoutListener.onGlobalLayout]

     * @return if true this listener should be removed, otherwise false.
     * *
     * @see android.view.ViewTreeObserver.OnGlobalLayoutListener
     */
    fun onGlobalLayout(): Boolean
}
