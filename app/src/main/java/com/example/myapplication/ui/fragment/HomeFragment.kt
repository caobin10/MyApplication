package com.example.myapplication.ui.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

import com.example.myapplication.R
import com.example.myapplication.adapter.HomeRvAdapter
import com.example.myapplication.base.BaseVmFragment
import com.example.myapplication.ui.activity.staff.DisListActivity
import com.example.myapplication.ui.common.CommonViewModel
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

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
        setupBanner()
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
        val item = mAdapter.getItem(position)
        item.entries.forEach {
            when (it.value) {
                getString(R.string.ic1) -> {
                    val intent = Intent(getActivity(), DisListActivity::class.java)
                    startActivity(intent)
                }
                getString(R.string.ic2) -> {
                    toast(it.value)
                }
                getString(R.string.ic3) -> {

                }
            }
        }
    }

    /**
     * ???????????????
     */
    private fun setupBanner() {
        val banners = mutableListOf<Int>()
        banners.add(R.mipmap.banner1)
        banners.add(R.mipmap.banner2)
        banners.add(R.mipmap.banner3)

        bannerView.run {
            //??????????????????
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            //???????????????
            setIndicatorGravity(BannerConfig.RIGHT)
            //????????????????????????????????????????????????
            setImageLoader(BannerImageLoader())
            //????????????????????????????????????
            setImages(banners)
            //???????????????????????????
            setBannerAnimation(Transformer.ZoomOutSlide)
            //??????????????????????????????
            //setBannerTitles(titles)
            //????????????????????????
            setDelayTime(3000)
            start()
            setOnBannerListener {
                //??????????????????
                val banner = banners[it]
                when (banner) {
                    R.mipmap.banner1 -> {
                        toast("hello")
                    }
                    R.mipmap.banner2 -> {
                        toast("hello")
                    }
                    R.mipmap.banner3 -> {
                        toast("hello")
                    }
                }
            }
        }
    }

    class BannerImageLoader : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            val banner = path as Int
            imageView.load(banner)
        }
    }
}