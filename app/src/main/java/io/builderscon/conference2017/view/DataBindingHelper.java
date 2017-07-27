package io.builderscon.conference2017.view;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataBindingHelper {

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(CircleImageView view, String imageUrl) {
        if (!imageUrl.isEmpty()) {
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .into(view);
        }

    }

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
