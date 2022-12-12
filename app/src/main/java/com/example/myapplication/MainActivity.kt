package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.test.base.BaseViewModel
import com.example.myapplication.base.BaseVmActivity
import com.example.myapplication.common.ScrollToTop
import com.example.myapplication.ui.fragment.HomeFragment
import com.example.myapplication.ui.fragment.Fragment2
import com.example.myapplication.ui.fragment.Fragment3
import com.example.myapplication.ui.fragment.Fragment4
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_main.*
import q.rorbin.badgeview.QBadgeView

class MainActivity : BaseVmActivity<BaseViewModel>() {

    private val badgeView by lazy {
        QBadgeView(this)
    }

    private lateinit var fragments: Map<Int, Fragment>

    override fun layoutRes(): Int = R.layout.activity_main

    override fun viewModelClass(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragments = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.fragment2 to createFragment(Fragment2::class.java),
            R.id.fragment3 to createFragment(Fragment3::class.java),
            R.id.fragment4 to createFragment(Fragment4::class.java)
        )

        bottomNavigationView.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener { menuItem ->
                val fragment = fragments.entries.find {
                    it.key == menuItem.itemId
                }?.value
                if (fragment is ScrollToTop) {
                    fragment.scrollToTop()
                }
            }
        }

        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            bottomNavigationView.selectedItemId = initialItemId
            showFragment(initialItemId)
        }

    }

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance()
                Fragment2::class.java -> Fragment2.newInstance()
                Fragment3::class.java -> Fragment3.newInstance()
                Fragment4::class.java -> Fragment4.newInstance()
                else ->
                    throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(menuItemId: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments.entries.find {
            it.key == menuItemId
        }?.value
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let {
                if (it.isVisible)
                    hide(it)
            }
            targetFragment?.let {
                if (it.isAdded)
                    show(it)
                else
                    add(R.id.fl, it)
            }
        }.commit()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            showBadgeView(1, 99)
        }
    }

    /**
     * BottomNavigationView显示角标
     * @param viewIndex tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    private fun showBadgeView(viewIndex: Int, showNumber: Int) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        val menuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.childCount) {
            // 获得viewIndex对应子tab
            val view: View = menuView.getChildAt(viewIndex)

            badgeView.bindTarget(view).badgeNumber = showNumber
        }
    }


}