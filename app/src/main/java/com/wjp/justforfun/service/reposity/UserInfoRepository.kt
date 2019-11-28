package com.wjp.justforfun.service.reposity

import android.content.SharedPreferences
import com.wjp.justforfun.util.boolean
import com.wjp.justforfun.util.string

class UserInfoRepository(prefs:SharedPreferences){

    var accessToken:String by prefs.string("user_access_token","")

    var username:String by prefs.string("username","")

    var password by prefs.string("password", "")

    var isAutoLogin by prefs.boolean("is_auto_login",true)


}