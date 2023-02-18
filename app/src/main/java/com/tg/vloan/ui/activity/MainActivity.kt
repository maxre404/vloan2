package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.tabs.TabLayout
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.bean.MainConfig
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.Constants
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.config.SPConfig
import com.tg.vloan.databinding.ActivityMainBinding
import com.tg.vloan.dto.DownloadRequest
import com.tg.vloan.ui.adapter.MainPageAdapter
import com.tg.vloan.ui.view.MainNavView
import com.tg.vloan.utils.EmptyUtils
import com.tg.vloan.viewmodel.MainViewModel
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var mainConfigList: List<MainConfig>? = null
    private val viewModel by viewModels<MainViewModel>()

    override fun initSubscription() {

    }

    override fun generateBinding(): ViewBinding? {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initPage()
        initTabs()
    }

    private fun initTabs() {
        for (i in mainConfigList!!.indices) {
            val mainConfig = mainConfigList!![i]
            val tab = binding!!.tbNav.newTab()
            val mainNavView: MainNavView = createNavCustomView(mainConfig)!!
            if (i == binding!!.vpFragment.currentItem) {
                mainNavView.onSelected()
            }
            tab.setCustomView(mainNavView)
            binding!!.tbNav.addTab(tab)
        }
        binding!!.tbNav.addOnTabSelectedListener(onTabSelectedListener)
    }

    private fun initPage() {
        val pageAdapter = MainPageAdapter(
            supportFragmentManager,
            lifecycle, mainConfigList!!
        )
        binding?.vpFragment?.adapter = pageAdapter
        if (!EmptyUtils.isEmpty(mainConfigList)) {
            binding!!.vpFragment.currentItem = 0
        }
        binding?.vpFragment?.isUserInputEnabled = false
        binding?.vpFragment?.offscreenPageLimit = mainConfigList!!.size
    }

    override fun initData() {
        mainConfigList = GlobalConfig.getConfig(ConfigKeys.MAIN_CONFIG)
        if (mainConfigList == null) {
            mainConfigList = ArrayList()
        }
        downloadReport()
    }
    fun createNavCustomView(mainConfig: MainConfig?): MainNavView? {
        val mainNavView = MainNavView(this)
        mainNavView.setMainConfig(mainConfig!!)
        return mainNavView
    }
    private fun downloadReport() {
        val isDownloadReport = SPConfig.getBoolean(ConfigKeys.SP_IS_DOWNLOAD_REPORT, false)
        if (isDownloadReport) {
            return
        }
        val downloadRequest = DownloadRequest(GlobalConfig.getUserId())
        viewModel.downloadReport(downloadRequest)
    }

    private val onTabSelectedListener: TabLayout.OnTabSelectedListener = object :
        TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            binding!!.vpFragment.setCurrentItem(tab.position, false)
            (Objects.requireNonNull(tab.customView) as MainNavView).onSelected()
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            (Objects.requireNonNull(tab.customView) as MainNavView).onUnselected()
        }

        override fun onTabReselected(tab: TabLayout.Tab) {}
    }
    private var pressTime: Long = 0

    override fun onBackPressedSupport() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - pressTime > Constants.PRESS_EXIT_TIME) {
            pressTime = currentTime
            showToast("再点一次退出app")
            return
        }
        super.onBackPressedSupport()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding!!.tbNav.removeOnTabSelectedListener(onTabSelectedListener)
    }


    companion object {
        fun startActivity(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}