package com.tg.vloan.ui.dialog

import android.app.Application
import androidx.viewbinding.ViewBinding
import com.github.gzuliyujiang.oaid.DeviceIdentifier
import com.tg.vloan.base.BaseDialogFragment
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.Constants
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.config.SPConfig
import com.tg.vloan.databinding.DialogPolicyBinding
import com.tg.vloan.ui.activity.PolicyActivity

/**
 * Created by frcx-hb on 2022/12/5 17:12.
 */
class PolicyDialog : BaseDialogFragment<DialogPolicyBinding>(){


    private var onPolicyClickListener: OnPolicyClickListener? = null


     override fun initView() {
        binding?.let {
            it.lytPrivacyPolicy?.setOnClickListener { v -> toPolicyPage(Constants.PolicyType.TYPE_PRIVACY) }
            it.lytRegisterPolicy.setOnClickListener { v -> toPolicyPage(Constants.PolicyType.TYPE_REGISTER) }
            it.tvCancel.setOnClickListener { v -> onDisagree() }
            it.tvAgree.setOnClickListener { v -> onAgree() }
        }
    }

    private fun onDisagree() {
        if (onPolicyClickListener != null) {
            onPolicyClickListener!!.onDisagree()
        }
    }

    private fun onAgree() {
        SPConfig.putBoolean(ConfigKeys.SP_AGREE_POLICY, true)
        DeviceIdentifier.register(GlobalConfig.getApplicationContext() as Application)
        dismiss()
        if (onPolicyClickListener != null) {
            onPolicyClickListener!!.onAgree()
        }
    }

    private fun toPolicyPage(type: Int) {
        context?.let { PolicyActivity.startActivity(it, type) }
    }

    override fun isCancelable(): Boolean {
        return false
    }

    fun setOnPolicyClickListener(onPolicyClickListener: OnPolicyClickListener?) {
        this.onPolicyClickListener = onPolicyClickListener
    }

    interface OnPolicyClickListener {
        fun onAgree()
        fun onDisagree()
    }

    companion object{
        fun newInstance(): PolicyDialog? {
            return PolicyDialog()
        }
    }

    override fun generateBinding(): ViewBinding? {
        return DialogPolicyBinding.inflate(layoutInflater)
    }

}