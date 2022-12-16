package com.example.myapplication.api

import com.example.myapplication.App
import com.example.myapplication.AppConfig
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val cookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(App.context)
    )
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
//            .proxy(Proxy.NO_PROXY)
        .cookieJar(cookieJar)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(AppConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun clearCookie() = cookieJar.clear()
}