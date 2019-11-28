package com.wjp.justforfun.http.interceptor

import android.util.Base64
import com.wjp.justforfun.service.reposity.UserInfoRepository
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(
    private val userInfoRepository:UserInfoRepository
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request=chain.request()
        val accessToken=getAuthorization()
        if (accessToken.isNotEmpty()) {
            request=request.newBuilder()
                .url(request.url().toString())
                .addHeader("Authorization",accessToken)
                .build()
        }

        return chain.proceed(request)
    }

    private fun getAuthorization():String{
        val accessToken=userInfoRepository.accessToken
        val username=userInfoRepository.username
        val password=userInfoRepository.password

        if (accessToken.isEmpty()) {
            val basicIsEmpty=username.isEmpty()||password.isEmpty()
            return if (basicIsEmpty) {
                ""
            }else{
                "$username:$password".let {
                    "basic "+ Base64.encode(it.toByteArray(),Base64.NO_WRAP)
                }
            }
        }
        return "token $accessToken"
    }
}