package com.example.myapplication.ui.common

import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.model.bean.User
import com.example.myapplication.model.store.UserStore

object UserRepository {
    /**
     * 更新用户信息
     */
    fun updateUser(user: User) = UserStore.setUser(user)

    /**
     * 是否登录
     */
    fun isLogin() = UserStore.isLogin()

    /**
     * 获取用户信息
     */
    fun getUser() = UserStore.getUser()

    /**
     * 清除登录状态
     */
    fun clearLoginState() {
        UserStore.clearUser()
        RetrofitClient.clearCookie()
    }
}