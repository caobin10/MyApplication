package com.example.myapplication.ui

import com.demo.test.base.BaseFragment
import com.example.myapplication.R

class Fragment1 : BaseFragment(){

    override fun layoutRes(): Int = R.layout.fragment1

    companion object {
        fun newInstance() = Fragment1()
    }
}