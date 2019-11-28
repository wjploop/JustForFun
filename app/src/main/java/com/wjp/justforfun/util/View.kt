package com.wjp.justforfun.util

import android.view.View

fun View.setVisibleOrGone(visibleOrGone: Boolean?) {
    visibility=if(visibleOrGone==true)View.VISIBLE else View.GONE
}