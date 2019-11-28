package com.wjp.justforfun.http.service

import com.wjp.justforfun.http.bean.LoginRequestModel
import com.wjp.justforfun.http.bean.UserAccessToken
import com.wjp.justforfun.ui.login.UserInfo
import io.reactivex.Flowable
import retrofit2.http.*

interface UserService {

    companion object {
        const val BASE_URL="https://api.github.com/"
    }

    @POST("authorizations")
    @Headers("Accept: application/json")
    fun authorizations(@Body loginRequestModel: LoginRequestModel): Flowable<UserAccessToken>

    @GET("user")
    fun fetchUserOwner():Flowable<UserInfo>
}