package com.example.myapplication.ui.activity.staff

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.example.myapplication.R
import com.example.myapplication.adapter.DisListAdapter
import com.example.myapplication.base.BaseVmCommonActivity
import kotlinx.android.synthetic.main.activity_dis_list.*
import kotlinx.android.synthetic.main.toolbar.*

class DisListActivity: BaseVmCommonActivity() ,OnItemClickListener{

    //xxxxxxxxxxxxxxxxxx

    private var whereSQL:String?=""

    private val mAdapter by lazy {
        DisListAdapter().apply {
            setOnItemClickListener(this@DisListActivity)
        }
    }
    override fun layoutRes(): Int = R.layout.activity_dis_list

    override fun initView() {
        tvHeaderTitle.text = getString(R.string.ic1)
        mRecyclerView.adapter = mAdapter
        setLoadMoreAdapter(mAdapter)
    }

    override fun initData(){
        super.initData()
//        loadOffLineDatas(getSql(), offset, limit)
//        loadOffLineDatas()
    }

//    private fun getSql() = with(StringBuffer()){
//
//        //默认条件语句
//        if(whereSQL.isNullOrEmpty()){
//            whereSQL = " where unitCode like '${user.unit?.unitCode}%' and cardStatus = '1' "
//        }
//
//        append(whereSQL)
//
//        val nameOrIdentNum = acetInput.text.toString().trim()
//        if(nameOrIdentNum.isNotEmpty()){
//            if(isIdentNum(nameOrIdentNum)){
//                append(" and identNum LIKE '$nameOrIdentNum%'")
//            }else{
//                append(" and name LIKE '$nameOrIdentNum%'")
//            }
//        }
//        toString()
//    }

    private fun loadOffLineDatas() {
//        val query = DaoFactory.daoFactory.getSitCodeDao().queryBuilder().orderAsc(SitCodeDao.Properties.Code).build()
//        val list: List<SitCode> = query.list()
//        loadMoreResult(getData,mList,mAdapter)

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val name = mAdapter.data[position]["Name"]
    }

}