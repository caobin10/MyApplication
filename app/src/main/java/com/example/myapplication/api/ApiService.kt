package com.example.myapplication.api

import com.example.myapplication.model.bean.GetData
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {
    @POST("Mobile/Ashx/MobiSource.ashx?")
    suspend fun getData(@QueryMap args: Map<String, String>): GetData
}