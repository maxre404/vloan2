package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.bean.AppInfoBean
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.databinding.ActivityAboutBinding
import com.tg.vloan.utils.DeviceUtils
import com.tg.vloan.utils.ResourceUtils
import java.lang.String

class AboutActivity  : BaseActivity<ActivityAboutBinding>() {
    companion object {
        fun startActivity(context: Context?) {
            val starter = Intent(context, AboutActivity::class.java)
            context?.startActivity(starter)
        }
    }
    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }

    override fun initData() {
        binding!!.tvVersion.setText(
            String.format(
                ResourceUtils
                    .getString(R.string.base_version_code), DeviceUtils.getAppVersion()
            )
        )
        val appInfoBean = GlobalConfig.getConfig<AppInfoBean>(ConfigKeys.APP_INFO)
        binding!!.tvEmail.setText(
            String.format(
                ResourceUtils
                    .getString(R.string.base_customer_email),
                if (appInfoBean != null) appInfoBean.appMail else ""
            )
        )
    }

    override fun initSubscription() {
    }

    override fun generateBinding(): ViewBinding? {
        return ActivityAboutBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

}
