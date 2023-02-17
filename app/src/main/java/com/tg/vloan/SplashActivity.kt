package com.tg.vloan

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.databinding.ActivitySplashBinding
import com.tg.vloan.utils.safeLaunch
import com.tg.vloan.viewmodel.SplashViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val viewModel by viewModels<SplashViewModel>()

    override fun createBinding(): ViewBinding? {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding?.let { it->
            it.button.setOnClickListener {
                viewModel.loadData()
                lifecycleScope
            }
        }
    }

    override fun initData() {
        safeLaunch {

        }

    }
}