package com.wjp.justforfun.base.ext.reactivex

import io.reactivex.subjects.BehaviorSubject

/**
 *  基于上一个发射的数据作改动，发射一个改动后的数据。类reducer的思想
 */
inline fun <reified T> BehaviorSubject<T>.copyMap(map:(T) -> T){
    val oldValue:T? = value
    oldValue?.let {
        onNext(map(it))
    }?:throw NullPointerException("BehaviorSubject<${T::class.java}> not contain value")
}