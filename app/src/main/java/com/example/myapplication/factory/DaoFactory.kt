package com.example.myapplication.factory

import android.os.Build
import com.example.myapplication.App
import com.example.myapplication.dao.DaoMaster
import com.example.myapplication.dao.DaoSession
import com.example.myapplication.dao.SitCodeDao

class DaoFactory private constructor() {
    //私有化构造方法
    companion object {
        val instant by lazy { DaoFactory() }
    }

    fun getOpenHelper(dbName:String)= DaoMaster.DevOpenHelper(App.context, dbName)

    /**
     * basic数据库
     */
    fun getBasicDaoSeesion(): DaoSession {
        val helper =  DaoMaster.DevOpenHelper(App.instance.applicationContext, "test.db");
        val db = helper.readableDatabase
        //解决 Android9.0 找不到表
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging()
        }
        return DaoMaster(db).newSession()
    }

    /**
     * 状态code表
     */
    fun getSitCodeDao(): SitCodeDao {
        return getBasicDaoSeesion().sitCodeDao
    }

}