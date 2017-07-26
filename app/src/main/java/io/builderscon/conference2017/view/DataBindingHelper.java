package io.builderscon.conference2017.view;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.view.View;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

public class DataBindingHelper {

    @BindingAdapter("sessionCellBackground")
    public static void setSessionCellBackground(View view, @DrawableRes int backgroundResId) {
        view.setBackgroundResource(backgroundResId);
    }

    @BindingAdapter("twowayview_rowSpan")
    public static void setTwowayViewRowSpan(View view, int rowSpan) {
        final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.rowSpan = rowSpan;
        view.setLayoutParams(lp);
    }

    @BindingAdapter("twowayview_colSpan")
    public static void setTwowayViewColSpan(View view, int colSpan) {
        final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.colSpan = colSpan;
        view.setLayoutParams(lp);
    }

}
