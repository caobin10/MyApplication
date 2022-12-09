package com.example.myapplication.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.inputMethodManager

abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(layoutRes())

        initView()

        initListener()
    }

    open fun initView() {

    }

    open fun initListener() {
    }

    abstract fun layoutRes(): Int

    /**
     * 隐藏软键盘
     */
    fun hideSoftKeyboard() {
        try {
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }catch (e:Exception){}
    }

    fun goBack(v: View){
        finish()
    }


    /**
     * 防止重复点击
     */
    private var lastClickTime: Long=0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    open fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD: Long = time - lastClickTime
        lastClickTime = time
        return timeD <= 300
    }
}