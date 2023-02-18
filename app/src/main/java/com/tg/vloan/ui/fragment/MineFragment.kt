package com.tg.vloan.ui.fragment

import androidx.viewbinding.ViewBinding
import com.tg.vloan.base.BaseFragment
import com.tg.vloan.databinding.FragmentMineBinding

class MineFragment:BaseFragment<FragmentMineBinding>() {
    override fun generateBinding(): ViewBinding? {
        return FragmentMineBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initSubscription() {

    }
}