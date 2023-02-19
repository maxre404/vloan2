package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.StorageConfig
import com.tg.vloan.databinding.ActivityPushBinding

class PushActivity : BaseActivity<ActivityPushBinding>() {
    companion object {
        fun startActivity(context: Context?) {
            val starter = Intent(context, PushActivity::class.java)
            context?.startActivity(starter)
        }
    }
    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }

    override fun initData() {
        val isNotify = StorageConfig.getBoolean(ConfigKeys.SP_IS_NOTIFY, true)
        binding!!.cbPush.isChecked = isNotify
        binding!!.cbPush.setOnCheckedChangeListener { buttonView, isChecked ->
            StorageConfig.putBoolean(
                ConfigKeys.SP_IS_NOTIFY,
                isChecked
            )
        }
    }

    override fun initSubscription() {
    }

    override fun generateBinding(): ViewBinding? {
        return ActivityPushBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

}
