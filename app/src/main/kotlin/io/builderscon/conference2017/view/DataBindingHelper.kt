package io.builderscon.conference2017.view

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.view.View
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.lucasr.twowayview.widget.SpannableGridLayoutManager

object DataBindingHelper {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: CircleImageView, imageUrl: String) {
        if (!imageUrl.isEmpty()) {
            Picasso.with(view.context)
                    .load(imageUrl)
                    .into(view)
        } else {
            view.setImageBitmap(null)
        }

    }

    @BindingAdapter("sessionCellBackground")
    @JvmStatic
    fun setSessionCellBackground(view: View, @DrawableRes backgroundResId: Int) {
        view.setBackgroundResource(backgroundResId)
    }

    @BindingAdapter("twowayview_rowSpan")
    @JvmStatic
    fun setTwowayViewRowSpan(view: View, rowSpan: Int) {
        val lp = view.layoutParams as SpannableGridLayoutManager.LayoutParams
        lp.rowSpan = rowSpan
        view.layoutParams = lp
    }

    @BindingAdapter("twowayview_colSpan")
    @JvmStatic
    fun setTwowayViewColSpan(view: View, colSpan: Int) {
        val lp = view.layoutParams as SpannableGridLayoutManager.LayoutParams
        lp.colSpan = colSpan
        view.layoutParams = lp
    }

}
