package com.tg.vloan.ui.fragment

import androidx.viewbinding.ViewBinding
import com.tg.vloan.R
import com.tg.vloan.base.BaseFragment
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.Constants
import com.tg.vloan.config.StorageConfig
import com.tg.vloan.databinding.FragmentMineBinding
import com.tg.vloan.ui.activity.*
import com.tg.vloan.utils.UserUtils

class MineFragment:BaseFragment<FragmentMineBinding>() {
    override fun generateBinding(): ViewBinding? {
        return FragmentMineBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding?.tvLogout?.setOnClickListener {
            UserUtils.logout()
        }
        //用户协议
        binding!!.lytRegisterPolicy.setOnClickListener { v -> toPolicyPage(Constants.PolicyType.TYPE_REGISTER) }
        //隐私协议
        binding!!.lytPrivacyPolicy.setOnClickListener { v -> toPolicyPage(Constants.PolicyType.TYPE_PRIVACY) }
        //关于我们
        binding!!.lytAboutUs.setOnClickListener { v -> toAboutPage() }
        //个性化推送
        binding!!.lytPersonalizePush.setOnClickListener { v -> toPersonalizedPage() }
        //联系客服
        binding!!.lytFeedback.setOnClickListener { v -> toFeedbackPage() }
        //注销账户
        binding!!.lytUnregister.setOnClickListener { v -> toUnregisterPage() }
    }

    private fun toUnregisterPage() {
        AccountUnregisterActivity.startActivity(context)
    }

    private fun toFeedbackPage() {
        FeedbackActivity.startActivity(context)
    }

    private fun toPersonalizedPage() {
        PushActivity.startActivity(context)
    }

    private fun toAboutPage() {
        AboutActivity.startActivity(context)
    }

    override fun initData() {
        var phone = StorageConfig.getString(ConfigKeys.SP_PHONE, "")
        binding!!.tvPhone.text = phone
    }

    override fun initSubscription() {

    }

    private fun toPolicyPage(type: Int) {
        PolicyActivity.startActivity(context, type)
    }

    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }
}