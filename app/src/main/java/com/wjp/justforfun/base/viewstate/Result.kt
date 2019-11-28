package com.wjp.justforfun.base.viewstate

sealed class Result<out T>:IViewState {

    object Idle: Result<Nothing>()
    object Loading:Result<Nothing>()
    data class Failure(val erro:Throwable):Result<Nothing>()
    data class Success<T>(val data:T):Result<T>()

    companion object{
        fun <T> success(result:T):Result<T> =Success(result)
        fun <T> failure(error:Throwable) :Result<T> =Failure(error)
        fun <T> loading():Result<T> = Loading
        fun <T> idle():Result<T> = Idle
    }

}