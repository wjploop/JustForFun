package com.wjp.justforfun.base.ext.reactivex

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun View.clickThrottleFirst(): Observable<Unit> =
    clicks().throttleFirst(500, TimeUnit.MILLISECONDS)