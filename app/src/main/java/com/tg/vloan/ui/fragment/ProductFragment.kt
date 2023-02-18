package com.tg.vloan.ui.fragment

import androidx.viewbinding.ViewBinding
import com.tg.vloan.base.BaseFragment
import com.tg.vloan.databinding.FragmentProductBinding

class ProductFragment:BaseFragment<FragmentProductBinding>() {
    override fun generateBinding(): ViewBinding? {
        return FragmentProductBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initSubscription() {

    }
}