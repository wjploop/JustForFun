package com.wjp.justforfun.adapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.wjp.justforfun.util.dp2px
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(MultiTransformation<Bitmap>(
                CenterCrop(),
                RoundedCornersTransformation(view.context.dp2px(6),0,RoundedCornersTransformation.CornerType.ALL)
            )))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}