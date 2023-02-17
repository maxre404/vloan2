package com.tg.vloan.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : ViewBinding> :Fragment() {
    var binding:V?=null
    abstract fun createBinding():ViewBinding?

    abstract fun initView()

    abstract fun initData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = createBinding() as V?
        initView()
        initData()
        return binding?.root
    }

}