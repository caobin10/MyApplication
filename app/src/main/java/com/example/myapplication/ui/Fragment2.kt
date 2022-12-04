package com.example.myapplication.ui

import com.demo.test.base.BaseFragment
import com.example.myapplication.R

class Fragment2 : BaseFragment(){
    override fun layoutRes(): Int = R.layout.fragment2
    companion object {
        fun newInstance() = Fragment2()
    }
}