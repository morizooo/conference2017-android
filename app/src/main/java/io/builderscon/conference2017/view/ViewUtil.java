package io.builderscon.conference2017.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

public class ViewUtil {
    /**
     * @see ViewTreeObserver
     */
    public interface OnGlobalLayoutListener {

        /**
         * This emulates {@link ViewTreeObserver.OnGlobalLayoutListener#onGlobalLayout()}
         *
         * @return if true this listener should be removed, otherwise false.
         * @see android.view.ViewTreeObserver.OnGlobalLayoutListener
         */
        boolean onGlobalLayout();
    }

    public static void addOneTimeOnGlobalLayoutListener(@NonNull View view, @NonNull OnGlobalLayoutListener onGlobalLayoutListener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onGlobalLayoutListener.onGlobalLayout()) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
}
