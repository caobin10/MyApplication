package com.example.myapplication.ui.common

import com.example.myapplication.api.RetrofitClient

class CommonRepository {
    suspend fun getData(args: Map<String, String>) = RetrofitClient.apiService.getData(args)

//    suspend fun getSessionId(args: Map<String, String>) = RetrofitClient.apiService.getSessionId(args)

//    suspend fun downFile(args: Map<String, String>) = RetrofitClient.apiService.downFile(args)

//    suspend fun checkUpdate(url:String) = RetrofitClient.apiService.checkUpdate(url)

//    suspend fun upLoadFiles(args: Map<String, String>,files:List<MultipartBody.Part>) = RetrofitClient.apiService.upLoadFiles(args, files)

}