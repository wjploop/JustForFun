package com.wjp.justforfun.util

import android.content.Context

fun Context.dp2px(dp:Int):Int  {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}
fun Context.px2dp(px:Int):Int {
    val scale = resources.displayMetrics.density
    return (px/scale +0.5f).toInt()
}