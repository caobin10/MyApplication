package com.example.myapplication.util

import android.app.Activity
import android.content.Intent
import com.example.myapplication.ext.putExtras

object ActivityUtil {
    val activities = mutableListOf<Activity>()

    fun start(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    fun startAndFinsh(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
        currentActivity().finish()
    }

    /**
     * finish指定的一个或多个Activity
     */
    fun finish(vararg clazz: Class<out Activity>) {
        activities.forEach { activiy ->
            if (clazz.contains(activiy::class.java)) {
                activiy.finish()
            }
        }
    }

    fun startForResult(clazz: Class<out Activity>, requestCode:Int, params: Map<String, Any> = emptyMap()) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivityForResult(intent,requestCode)
    }

    fun setResult(requestCode:Int, params: Map<String, Any> = emptyMap()){
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent()
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.setResult(requestCode,intent)
    }

    fun currentActivity(): Activity {
        return activities.last()
    }

    fun finishAll(){
        activities.forEach { activiy ->
            activiy.finish()
        }
        activities.clear()
    }
}