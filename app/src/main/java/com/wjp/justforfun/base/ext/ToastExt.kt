package com.wjp.justforfun.base.ext

import android.content.Context
import android.widget.Toast
import com.wjp.justforfun.MyApp


inline fun toast(value: () -> String): Unit =
    MyApp.INSTANCE.toast(value)

fun Context.toast(value: String) = toast { value }

inline fun Context.toast(value: () -> String) =
    Toast.makeText(this, value(), Toast.LENGTH_SHORT).show()

