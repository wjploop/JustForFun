package com.wjp.justforfun.util

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

fun ImageView.setImageFromUrl(url: String?,corner:Int=6 ) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions.bitmapTransform(
                    MultiTransformation<Bitmap>(
                        CenterCrop(),
                        RoundedCornersTransformation(context.dp2px(corner),0, RoundedCornersTransformation.CornerType.ALL)
                    )
                ))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}