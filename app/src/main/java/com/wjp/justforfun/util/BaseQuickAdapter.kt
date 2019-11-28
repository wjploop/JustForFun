package com.wjp.justforfun.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder

fun BaseViewHolder.setImageFromUrl(@IdRes viewId:Int,url: String?,corner:Int=6) {
    getView<ImageView>(viewId).setImageFromUrl(url,corner)
}

fun BaseViewHolder.setVisibleOrGone(@IdRes viewId: Int, visibleOrGone: Boolean?) {
    getView<View>(viewId).setVisibleOrGone(visibleOrGone)
}