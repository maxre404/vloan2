package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.databinding.ActivityAccountUnregisterBinding
import com.tg.vloan.utils.ResourceUtils

class AccountUnregisterActivity :
    BaseActivity<ActivityAccountUnregisterBinding>() {
    companion object {
        fun startActivity(context: Context?) {
            val starter = Intent(context, AccountUnregisterActivity::class.java)
            context?.startActivity(starter)
        }
    }
    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }

    override fun initData() {
        binding!!.tvSummit.setOnClickListener {
            showToast(ResourceUtils.getString(R.string.base_submit_success))
            finish()
        }
    }

    override fun initSubscription() {

    }

    override fun generateBinding(): ViewBinding? {
        return ActivityAccountUnregisterBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }
}