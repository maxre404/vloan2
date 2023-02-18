package com.tg.vloan.ui.adapter

import com.tg.vloan.bean.MainConfig
import androidx.viewpager2.adapter.FragmentStateAdapter
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.tg.vloan.ui.fragment.HomeFragment
import com.tg.vloan.ui.fragment.MineFragment
import com.tg.vloan.ui.fragment.ProductFragment
import com.tg.vloan.utils.EmptyUtils
import com.tg.vloan.utils.Utils
import java.lang.RuntimeException

/**
 * Created by frcx-hb on 2022/12/3 15:13.
 */
class MainPageAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val mainConfigList: List<MainConfig>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    var fragmentList = listOf<Fragment>(HomeFragment(),ProductFragment(),MineFragment())
    override fun createFragment(position: Int): Fragment {
        val mainConfig = mainConfigList[position]
        val pageClassName = mainConfig.pageClassName
        if (TextUtils.isEmpty(pageClassName)) {
            throw RuntimeException("pageClassName不能为空")
        }
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return if (EmptyUtils.isEmpty(mainConfigList)) 0 else mainConfigList.size
    }
}