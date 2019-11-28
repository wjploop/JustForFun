package com.wjp.justforfun.http.bean

import com.google.gson.annotations.SerializedName
import com.wjp.justforfun.BuildConfig

data class LoginRequestModel(
    val scope:List<String>,
    val note:String,
    @SerializedName("client_id")
    val clientId:String,
    @SerializedName("client_secret")
    val clientSecret:String
) {

    companion object{
        fun generate()=
            LoginRequestModel(
                scope = listOf("user","repo","gist","notification"),
                note = BuildConfig.APPLICATION_ID,
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
            )
    }
}