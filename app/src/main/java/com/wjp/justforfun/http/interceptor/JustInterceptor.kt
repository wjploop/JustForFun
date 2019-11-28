package com.wjp.justforfun.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class JustInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
            chain.request().newBuilder()
                .addHeader("app_id","toownvffhllrmbre")
                .addHeader("app_secret","YW11c1E5ZU43ZDdLcy9TZXcxMUNhdz09")
                .build()
                .let {
                    return chain.proceed(it)
                }
    }
}