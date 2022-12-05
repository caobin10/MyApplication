package com.demo.test.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){

    protected var offset:Int = 0;
    protected var limit:Int = 20;
    protected var lazyLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes(), container, false)
    }

    open fun layoutRes() = 0
}