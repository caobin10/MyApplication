package com.example.myapplication.ui.fragment

import com.demo.test.base.BaseFragment
import com.example.myapplication.R

class Fragment3 : BaseFragment(){
    override fun layoutRes(): Int = R.layout.fragment3

    companion object {
        fun newInstance() = Fragment3()
    }

}