package com.tg.vloan.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gyf.barlibrary.ImmersionBar
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment<V : ViewBinding> :SupportFragment() {
    var binding:V?=null
    abstract fun generateBinding():ViewBinding?

    abstract fun initView()

    abstract fun initData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = generateBinding() as V?
        initData()
        initView()
        initSubscription()
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isImmersionBarEnabled() && setStatusBarView() != 0 && activity != null) {
            val statusBarView = view.findViewById<View>(setStatusBarView())
            ImmersionBar.setStatusBarView(activity, statusBarView)
        }
    }

    abstract fun initSubscription()

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (isImmersionBarEnabled()) {
            initImmersionBar()
        }
    }

    open fun setStatusBarView(): Int {
        return 0
    }

    open fun isImmersionBarEnabled(): Boolean {
        return true
    }

    open fun initImmersionBar() {
        ImmersionBar.with(this)
            .navigationBarColorInt(Color.WHITE)
            .statusBarDarkFont(false).init()
    }

}