package com.example.myapplication.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.classic.common.MultipleStatusView
import com.demo.test.base.BaseFragment
import com.demo.test.base.BaseViewModel

abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {

    /**
     * 请求参数Map
     */
    protected var args= mutableMapOf<String,String>()
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null


    protected lateinit var mViewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        initListener()
        observe()
        // 因为Fragment恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        if (savedInstanceState == null) {
            initData()
        }

        mLayoutStatusView?.setOnClickListener { lazyLoadData() }

    }

    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    abstract fun viewModelClass(): Class<VM>

    open fun initView() {
        // Override if need
    }
    open fun initListener() {
        // Override if need
    }

    open fun observe() {

    }

    open fun initData() {
        // Override if need
    }

    open fun lazyLoadData() {
        // Override if need
    }


    /**
     * 加载更多适配
     */
    fun <T>setLoadMoreAdapter(mAdapter: BaseQuickAdapter<T, BaseViewHolder>){
        if(offset == 0)mLayoutStatusView?.showLoading()

        //设置加载更多
        mAdapter.loadMoreModule.setOnLoadMoreListener{
            offset += limit
            initData()
        }
        mAdapter.loadMoreModule.isAutoLoadMore = true
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage=false
    }



    /**
     * 加载更多结束
     */
    fun <T>loadMoreResultView(mAdapter: BaseQuickAdapter<T, BaseViewHolder>, data:List<T>){
        if(offset == 0){
            mLayoutStatusView?.showContent()
            mAdapter.setList(data)
        }else{
            mAdapter.addData(data)
        }

        if(data.size < limit){
            //如果s少于20,显示没有更多数据布局
            mAdapter.loadMoreModule.loadMoreEnd()
        }else{
            mAdapter.loadMoreModule.loadMoreComplete()
        }
    }

}
