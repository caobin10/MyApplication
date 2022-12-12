package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.File.AppUtils

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
        lateinit var context: Context
        lateinit var dbName: String
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        dbName = "test.db"
        AppUtils.copyDbFileFromAsset(context, dbName)
    }
}