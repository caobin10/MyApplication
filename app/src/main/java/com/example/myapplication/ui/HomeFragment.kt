package com.example.myapplication.ui

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

import com.example.myapplication.R
import com.example.myapplication.adapter.HomeRvAdapter
import com.example.myapplication.base.BaseVmFragment
import com.example.myapplication.ui.common.CommonViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVmFragment<CommonViewModel>(), OnItemClickListener {

    override fun layoutRes(): Int = R.layout.fragment_home

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val mAdapter by lazy {
        HomeRvAdapter().apply {
            setOnItemClickListener(this@HomeFragment)
        }
    }

    override fun initView() {
        rv.adapter = mAdapter
    }

    override fun lazyLoadData() {
        setResources()
        mAdapter.setList(list)
    }

    override fun viewModelClass(): Class<CommonViewModel> = CommonViewModel::class.java

    var list = mutableListOf<MutableMap<Int, String>>()
    private fun setResources() {
        disableGroup()
    }

    private fun disableGroup() {
        addItemResources(R.mipmap.main_record, getString(R.string.ic1))
        addItemResources(R.mipmap.main_record, getString(R.string.ic2))
        addItemResources(R.mipmap.main_record, getString(R.string.ic3))
        addItemResources(R.mipmap.main_record, getString(R.string.ic4))
        addItemResources(R.mipmap.main_record, getString(R.string.ic5))
        addItemResources(R.mipmap.main_record, getString(R.string.ic6))
        addItemResources(R.mipmap.main_record, getString(R.string.ic7))
        addItemResources(R.mipmap.main_record, getString(R.string.ic6))
        addItemResources(R.mipmap.main_record, getString(R.string.ic7))
    }

    private fun addItemResources(key: Int, value: String) {
        list.add(mutableMapOf<Int, String>(key to value))
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}