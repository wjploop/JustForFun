package com.wjp.justforfun.http

sealed class Error:Throwable(){
    object NetworkError:Error()
    object EmptyInputError:Error()
    object EmptyResultError:Error()
    object SingleError:Error()

}