package com.tg.vloan.ui.activity

import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.google.gson.reflect.TypeToken
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.bean.MainConfig
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.Constants
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.config.StorageConfig
import com.tg.vloan.databinding.ActivitySplashBinding
import com.tg.vloan.net.ApiPath
import com.tg.vloan.ui.dialog.PolicyDialog
import com.tg.vloan.utils.ResourceUtils
import com.tg.vloan.utils.Utils
import com.tg.vloan.utils.safeLaunch
import com.tg.vloan.viewmodel.SplashViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var countDownTimer: CountDownTimer? = null
    private val PAGE_GUIDE = 0
    private val PAGE_LOGIN = 1
    private val PAGE_HOME = 2
    private val viewModel by viewModels<SplashViewModel>()

    override fun generateBinding(): ViewBinding? {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding?.tvDuration?.setOnClickListener {
            onConfigComplete()
        }
    }

    override fun initData() {
        if (GlobalConfig.getConfig<Any>(ConfigKeys.MAIN_CONFIG) == null) {
            initConfig()
        } else {
            getBaseUrl()
        }

    }
    override fun initSubscription() {
        viewModel.isCheckLiveData.observe(this){ response->
            if (response.isSuccess){
                onCheckUser(true)
            }else{
                showToast(response.msg)
            }
        }
    }

    private fun initConfig() {
        safeLaunch {
            val mainConfigList: List<MainConfig> = Utils.getDataFromJsonFile(
                Constants.ConfigFile.JSON_MAIN_CONFIG,
                object : TypeToken<List<MainConfig?>?>() {}.type
            )
            GlobalConfig.putConfig(ConfigKeys.MAIN_CONFIG, mainConfigList)
            getBaseUrl()
        }
    }

    private fun getBaseUrl(){
        Constants.BASE_URL = ApiPath.getBaseUrl
        checkUser()
    }

    private fun checkUser() {
            viewModel.checkUser()
    }
    private fun onCheckUser(success: Boolean) {
        if (success) {
            checkPolicy()
        } else {
            getBaseUrl()
        }
    }
    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }
    private fun checkPolicy() {
        val agreePolicy: Boolean = StorageConfig.getBoolean(ConfigKeys.SP_AGREE_POLICY, false)
        if (!agreePolicy) {
            showAgreePolicyDialog()
        } else {
            onConfigComplete()
            //startCountDown()
        }
    }
    private fun getPageType(): Int {
        val isFirstLaunch = StorageConfig.getBoolean(ConfigKeys.SP_FIRST_LAUNCH, true)
        return if (isFirstLaunch) {
            StorageConfig.putBoolean(ConfigKeys.SP_FIRST_LAUNCH, false)
            PAGE_GUIDE
        } else if (TextUtils.isEmpty(GlobalConfig.getUserId())) {
            PAGE_LOGIN
        } else {
            PAGE_HOME
        }
    }
    private fun onConfigComplete() {
        val pageType = getPageType()
        when (pageType) {
            PAGE_GUIDE, PAGE_LOGIN -> LoginActivity.startActivity(this)
            PAGE_HOME -> MainActivity.startActivity(this)
            else -> {}
        }
        finish()
    }
    private val onPolicyClickListener: PolicyDialog.OnPolicyClickListener = object : PolicyDialog.OnPolicyClickListener {
        override fun onAgree() {
            startCountDown()
        }

        override fun onDisagree() {
            Utils.exitApp()
        }
    }
    private fun startCountDown() {
        binding?.tvDuration?.visibility = View.VISIBLE
        if (countDownTimer == null) {
            countDownTimer = object :
                CountDownTimer(Constants.SPLASH_COUNT_DOWN_TIME.toLong(), Constants.COUNT_DOWN_INTERVAL.toLong()) {
                override fun onTick(millisUntilFinished: Long) {
                    binding?.tvDuration?.setText(
                        java.lang.String.format(
                            ResourceUtils.getString(
                                R.string.base_count_down_skip
                            ), millisUntilFinished / Constants.COUNT_DOWN_INTERVAL
                        )
                    )
                }

                override fun onFinish() {
                    onConfigComplete()
                }
            }
        }
        countDownTimer?.start()
    }
    private fun showAgreePolicyDialog() {
        val policyDialog: PolicyDialog? = PolicyDialog.newInstance()
        policyDialog?.setOnPolicyClickListener(onPolicyClickListener)
        policyDialog?.show(supportFragmentManager, "policyDialog")
    }
}