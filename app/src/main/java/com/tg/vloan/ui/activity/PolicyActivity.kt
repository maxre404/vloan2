package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.viewbinding.ViewBinding
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.config.Constants
import com.tg.vloan.databinding.ActivityPolicyBinding
import com.tg.vloan.net.ApiPath
import com.tg.vloan.utils.ResourceUtils
import com.tg.vloan.utils.safeLaunch

class PolicyActivity : BaseActivity<ActivityPolicyBinding>(){

    companion object{
        fun startActivity(context: Context?, type: Int) {
            val starter = Intent(context, PolicyActivity::class.java)
            starter.putExtra(Constants.TYPE, type)
            context?.startActivity(starter)
        }
    }

    private var type = 0

    override fun initData() {
        readIntent(intent)
        binding?.headerBar?.setTitle(
            if (type == Constants.PolicyType.TYPE_PRIVACY) ResourceUtils.getString(
                R.string.base_privacy_policy
            ) else ResourceUtils.getString(R.string.base_register_policy)
        )
        //binding?.tvContent?.setText(if (type == Constants.PolicyType.TYPE_PRIVACY) getPrivacyPolicy() else getRegisterPolicy())
        initWebSetting()
        binding!!.webView.scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY

        var url = if (type == Constants.PolicyType.TYPE_PRIVACY) ApiPath.getBaseUrl+"policy.html"+"?push_str="+ApiPath.pushUrl else ApiPath.getBaseUrl+"agreement.html"+"?push_str="+ApiPath.pushUrl
        if (!TextUtils.isEmpty(url)) {
            safeLaunch {
                binding?.webView?.loadUrl(url)
            }
        }
    }

    fun initWebSetting(){
        val settings = binding!!.webView.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.loadWithOverviewMode = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.blockNetworkImage = false
        settings.useWideViewPort = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    }

    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }


    private fun readIntent(intent: Intent) {
        type = intent.getIntExtra(Constants.TYPE, Constants.PolicyType.TYPE_PRIVACY)
    }

    override fun initSubscription() {

    }

    override fun generateBinding(): ViewBinding? {
        return ActivityPolicyBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }
}