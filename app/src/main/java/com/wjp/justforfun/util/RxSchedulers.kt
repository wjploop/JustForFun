package com.wjp.justforfun.util

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

object RxSchedulers {

    val database: Scheduler = Schedulers.single()

    val io: Scheduler = Schedulers.io()

    val ui: Scheduler = AndroidSchedulers.mainThread()
}

fun <T> Observable<T>.io2ui()=
    subscribeOn(RxSchedulers.io)
        .observeOn(RxSchedulers.ui)