package com.example.myapplication

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.myapplication.File.AppUtils

class App : MultiDexApplication() {
    companion object {
        lateinit var instance: App
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