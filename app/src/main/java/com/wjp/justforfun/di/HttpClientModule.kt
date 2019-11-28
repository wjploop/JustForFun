package com.wjp.justforfun.di

import com.wjp.justforfun.BuildConfig
import com.wjp.justforfun.http.interceptor.BasicAuthInterceptor
import com.wjp.justforfun.http.interceptor.JustInterceptor
import com.wjp.justforfun.http.service.UserService
import com.wjp.zhihupurify.service.reposity.JustService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val HTTP_CLIENT_MODULE_TAG = "htt_client_module_tag"
private const val http_client_interceptor_log_tag = "http_client_interceptor_log_tag"
private const val http_client_inteceptor_auth_tag = "http_client_interceptor_auth_tag"

private const val TIME_OUT_SECONDS = 10


//为不同源的API创建不同的Retrofit,用TAG区分
const val JUST_API_TAG = "just_api"
const val GITHUB_API_TAG = "github_api"

val httpClientModule = Kodein.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<Retrofit>(JUST_API_TAG) with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(JustService.BASE_URL)
            .client(instance())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    bind<Retrofit>(GITHUB_API_TAG) with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(UserService.BASE_URL)
            .client(instance())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    bind<Retrofit.Builder>() with provider { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with provider { OkHttpClient.Builder() }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
            .connectTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            ).readTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .addInterceptor(JustInterceptor())
            .addInterceptor(instance(http_client_interceptor_log_tag))
            .addInterceptor(instance(http_client_inteceptor_auth_tag))
            .build()
    }

    bind<Interceptor>(http_client_interceptor_log_tag) with singleton {
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    bind<Interceptor>(http_client_inteceptor_auth_tag) with singleton {
        BasicAuthInterceptor(instance())
    }


}
