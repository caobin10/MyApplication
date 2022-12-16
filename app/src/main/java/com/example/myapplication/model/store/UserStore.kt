package com.example.myapplication.model.store

import com.example.myapplication.App
import com.example.myapplication.model.bean.User
import com.example.myapplication.util.GsonUtil
import com.example.myapplication.util.clearSpValue
import com.example.myapplication.util.getSpValue
import com.example.myapplication.util.putSpValue


object UserStore {

    private const val SP_USER_INFO = "user_info"
    private const val KEY_USER_INFO = "userInfo"
    private val mGson by lazy { GsonUtil.gson }

    fun isLogin(): Boolean {
        val userInfoStr = getSpValue(SP_USER_INFO, App.context, KEY_USER_INFO, "")
        return userInfoStr.isNotEmpty()
    }

    fun getUser(): User? {
        val userInfoStr = getSpValue(SP_USER_INFO, App.context, KEY_USER_INFO, "")
        return if (userInfoStr.isNotEmpty()) {
            mGson.fromJson(userInfoStr, User::class.java)
        } else {
            null
        }
    }

    fun setUser(userInfo: User) = putSpValue(SP_USER_INFO, App.context, KEY_USER_INFO, mGson.toJson(userInfo))

    fun clearUser() = clearSpValue(SP_USER_INFO, App.context)

}