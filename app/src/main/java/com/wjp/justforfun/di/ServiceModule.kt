package com.wjp.justforfun.di

import com.wjp.justforfun.http.service.UserService
import com.wjp.zhihupurify.service.reposity.JustService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit


private const val SERVICE_MODULE_TAG = "serviceModule"

val serviceModule= Kodein.Module(SERVICE_MODULE_TAG){

    bind<UserService>() with singleton {
        instance<Retrofit>(GITHUB_API_TAG).create(UserService::class.java)
    }

    bind<JustService>() with singleton {
        instance<Retrofit>(JUST_API_TAG).create(JustService::class.java)
    }

}