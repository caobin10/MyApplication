package com.example.myapplication.adapter

import android.widget.RelativeLayout
import coil.load

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * Created by WLM on 2021/8/7
 * Desc:
 */
class HomeRvAdapter(layoutResId: Int = R.layout.item_home) :
    BaseQuickAdapter<MutableMap<Int, String>, BaseViewHolder>(layoutResId) {

//    var synchreBadgeView = QBadgeView(App.context)

    override fun convert(helper: BaseViewHolder, item: MutableMap<Int, String>) {

        helper.itemView.run {

            item.entries.forEach {

                iv_item.load(it.key)
                tv_item.text = it.value

                //位同步数据数
//                if (it.value == context.getString(R.string.main_xxtb)) {
//                    setsynchreBadgeView(rl)
//                }
            }
        }
    }

//    private fun setsynchreBadgeView(synchroRl: RelativeLayout) {
//        synchroRl.context.doAsync {
//            val dao = DaoFactory.instant.getDisBaseDao()
//            val count = dao.queryBuilder().where(DisBaseDao.Properties.SynchFlag.`in`("1","2"))
//                    .orderRaw("updateTime DESC").orderAsc().buildCount().count()
//            uiThread {
//                synchreBadgeView.bindTarget(synchroRl).badgeNumber = count.toInt()
//            }
//        }
//    }

}