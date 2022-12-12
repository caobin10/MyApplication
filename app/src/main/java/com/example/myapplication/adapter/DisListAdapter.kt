package com.example.myapplication.adapter

import android.view.View
import androidx.core.view.isGone
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import kotlinx.android.synthetic.main.item_dis_list.view.*


class DisListAdapter(layoutResId: Int = R.layout.item_dis_list) :
    BaseQuickAdapter<MutableMap<String, String>, BaseViewHolder>(layoutResId),LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: MutableMap<String, String>) {

        holder.itemView.run {
            tv_name.text = item["Name"]

            val surveyFlag = item["SurveyFlag"]
            survey_tag.visibility = View.VISIBLE

//            val surveyFlagName = getDictDataName("50",surveyFlag)
//            survey_tag.text = surveyFlagName.ifEmpty { "未调查" }
//
//            //数据状态
//            val surveyStatus = item["SurveyStatus"]
//            state.isGone = surveyStatus.isNullOrEmpty()
//            state.text = getDictDataName("53",surveyStatus)
//
//            tv_update_time.text = item["UpdateTime"]?.substring(0,10)
//            tv_value1.text = formatString("身份证号", formatIndentNum( item["IdentNum"]))
//
//            tv_value2.text = formatString("残疾证号",formatIndentNum( item["DisableNum"]))
//
//            val unitName = getAllUnitName(item["UnitCode"])
//
//            tv_value3.isGone = unitName.isEmpty()
//            tv_value3.text = formatString("所在社区", unitName)
        }
    }

}