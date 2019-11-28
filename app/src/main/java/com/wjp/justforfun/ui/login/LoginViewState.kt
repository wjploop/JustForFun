package com.wjp.justforfun.ui.login

data class LoginViewState(
    val isLoading:Boolean,
    val throwable: Throwable?,
    val loginInfo:UserInfo?,
    val autoLoginEvent:AutoLoginEvent?,
    val userAutoLoginEvent:Boolean
) {
    companion object{
        fun initial() =LoginViewState(
            isLoading = false,
            throwable = null,
            loginInfo = null,
            autoLoginEvent = null,
            userAutoLoginEvent = false
        )
    }
}