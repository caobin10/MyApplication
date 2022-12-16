package com.example.myapplication.util

import android.content.Context
import com.example.myapplication.App
import com.example.myapplication.R.string.app_name
/**
 * SharedPreferences
 */
private var SP_File_Name = App.context.resources.getString(app_name)


fun <T> getSpValue(
        filename: String = SP_File_Name,
        context: Context = App.context,
        key: String,
        defaultVal: T
): T {
    val sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
    return when (defaultVal) {
        is Boolean -> sp.getBoolean(key, defaultVal) as T
        is String -> sp.getString(key, defaultVal) as T
        is Int -> sp.getInt(key, defaultVal) as T
        is Long -> sp.getLong(key, defaultVal) as T
        is Float -> sp.getFloat(key, defaultVal) as T
        is Set<*> -> sp.getStringSet(key, defaultVal as Set<String>) as T
        else -> throw IllegalArgumentException("Unrecognized default value $defaultVal")
    }
}


fun <T> putSpValue(
        filename: String = SP_File_Name,
        context: Context = App.context,
        key: String,
        value: T
) {
    val editor = context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit()
    when (value) {
        is Boolean -> editor.putBoolean(key, value)
        is String -> editor.putString(key, value)
        is Int -> editor.putInt(key, value)
        is Long -> editor.putLong(key, value)
        is Float -> editor.putFloat(key, value)
        is Set<*> -> editor.putStringSet(key, value as Set<String>)
        else -> throw UnsupportedOperationException("Unrecognized value $value")
    }
    editor.apply()
}


fun removeSpValue(filename: String = SP_File_Name, context: Context = App.context, key: String) {

    context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        .edit()
        .remove(key)
        .apply()
}


fun clearSpValue(filename: String = SP_File_Name, context: Context = App.context) {

    context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()
}