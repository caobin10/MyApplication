package com.example.myapplication.ui

import com.demo.test.base.BaseFragment
import com.example.myapplication.R

class Fragment4 : BaseFragment(){
    override fun layoutRes(): Int = R.layout.fragment4
    companion object {
        fun newInstance() = Fragment4()
    }
}