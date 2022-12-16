package com.example.myapplication.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken


object GsonUtil {

     var gson  = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .create()


    /**
     * 转成json
     *
     * @param object
     * @return
     */
    fun toString(`object`: Any?): String {
        var gsonString = ""
        if(`object` !=null){
            gsonString = gson.toJson(`object`)
        }
        return gsonString
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> toObj(gsonString: String, cls: Class<T>): T? {
        var t: T? = null
        if (gson != null) {
            t = gson!!.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> toList(gsonString: String, cls: Class<T>): List<T> {
        var list: List<T> = listOf()
        if (gson != null && gsonString.isNotEmpty()) {
            list = gson!!.fromJson<List<T>>(gsonString, object : TypeToken<List<T>>() {

            }.type)
        }
        return list
    }

    fun <T> toObjectList(jsonString: String, cls: Class<T>): List<T> {

        val list: MutableList<T> = ArrayList()
        try {
            if(!jsonString.isNullOrEmpty()){
                val arry = JsonParser().parse(jsonString).asJsonArray
                for (jsonElement in arry) {
                    list.add(gson.fromJson(jsonElement, cls))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun <T> toSet(gsonString: String, cls: Class<T>): Set<T>? {
        var set:Set<T>? = null
        if (gson != null) {
            set = gson!!.fromJson<Set<T>>(gsonString, object : TypeToken<Set<T>>() {

            }.type)
        }
        return set
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> toListMaps(gsonString: String?): List<MutableMap<String, T>> {
        var list: List<MutableMap<String, T>> = arrayListOf()
        if(!gsonString.isNullOrEmpty()){
            if (gson != null) {
                list = gson!!.fromJson(gsonString,
                    object : TypeToken<List<MutableMap<String, T>>>() {

                    }.type)
            }
        }
        return list
    }


    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> toMap(gsonString: String?): MutableMap<String, T>? {
        var map: MutableMap<String, T>? = null
        if (gson != null && !gsonString.isNullOrEmpty()) {
            map = gson!!.fromJson<MutableMap<String, T>>(gsonString, object : TypeToken<MutableMap<String, T>>() {

            }.type)
        }
        return map
    }


}