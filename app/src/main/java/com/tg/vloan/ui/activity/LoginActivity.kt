package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.bean.IsCheckBean
import com.tg.vloan.callback.SimpleTextWatcher
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.Constants
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.config.StorageConfig
import com.tg.vloan.databinding.ActivityLoginBinding
import com.tg.vloan.dto.CodeRequest
import com.tg.vloan.dto.LoginRequest
import com.tg.vloan.utils.MyCountDownTimer
import com.tg.vloan.utils.ResourceUtils
import com.tg.vloan.viewmodel.LoginViewModel

class LoginActivity: BaseActivity<ActivityLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>()

    companion object {
        fun startActivity(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var timer: MyCountDownTimer? = null
    private var isCode = true

    private val DEFAULT_CODE = "1234"


     override fun initView() {
        val isCheckBean: IsCheckBean = GlobalConfig.getConfig(ConfigKeys.IS_CHECK_BEAN)
         binding?.let { getBinding->
             if (isCheckBean != null) {
                 getBinding.ivCheck.setSelected("1" == isCheckBean.getIsCheck())
                 isCode = "1" == isCheckBean.getVerifyCode()
             }
             getBinding.rltCode.setVisibility(if (isCode) View.VISIBLE else View.GONE)
             if (!isCode) {
                 getBinding.etCode.setText(DEFAULT_CODE)
             }
             getBinding.tvPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance())
             getBinding.tvPrivacyPolicy.setText(getPrivacyPolicyBuilder())
             getBinding.tvGetCode.setOnClickListener { v -> sendCode() }
             getBinding.tvLogin.setOnClickListener { v -> login() }
             getBinding.ivCheck.setOnClickListener { v -> v.setSelected(!v.isSelected()) }
             getBinding.etAccount.addTextChangedListener(object : SimpleTextWatcher() {
                 override fun afterTextChanged(s: Editable) {
                     if (s.length == 11 && isCode) {
                         getBinding.etCode.setFocusable(true)
                         getBinding.etCode.requestFocus()
                     }
                 }
             })
         }

    }

    private fun getPrivacyPolicyBuilder(): SpannableStringBuilder? {
        val builder = SpannableStringBuilder(
            ResourceUtils
                .getString(R.string.base_privacy_policy_tip)
        )
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                PolicyActivity.startActivity(this@LoginActivity, Constants.PolicyType.TYPE_PRIVACY)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }, builder.length - 6, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        builder.setSpan(
            ForegroundColorSpan(ResourceUtils.getColor(R.color.login_protocol)),
            builder.length - 6, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                PolicyActivity.startActivity(this@LoginActivity, Constants.PolicyType.TYPE_REGISTER)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }, builder.length - 12, builder.length - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        builder.setSpan(
            ForegroundColorSpan(ResourceUtils.getColor(R.color.login_protocol)),
            builder.length - 12, builder.length - 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        builder.setSpan(
            ForegroundColorSpan(ResourceUtils.getColor(R.color.login_txt)),
            0, builder.length - 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return builder
    }

    /**
     * 发送验证码
     */
    private fun sendCode() {
        if (TextUtils.isEmpty(getPhone())) {
            showToast(ResourceUtils.getString(R.string.base_input_phone_tip))
            return
        }
        sendCode("1")
    }
    /**
     * 发送验证码
     */
    private fun  sendCode(type:String) {
        val codeRequest = CodeRequest(getPhone(), type)
        viewModel.sendCode(codeRequest)
    }

    /**
     * 登录
     */
    private fun login() {
        if (TextUtils.isEmpty(getPhone())) {
            showToast(ResourceUtils.getString(R.string.base_input_phone_tip))
            return
        }
        if (TextUtils.isEmpty(getCode())) {
            showToast(ResourceUtils.getString(R.string.base_input_captcha_tip))
            return
        }
        if (!binding!!.ivCheck.isSelected) {
            showToast(ResourceUtils.getString(R.string.base_agree_policy_few_tip))
            return
        }
        val loginRequest = LoginRequest(getPhone(), getCode())
        viewModel.login(loginRequest)
    }

    private fun onLoginSuccess() {
        MainActivity.startActivity(this)
        finish()
    }

    private fun onSendCodeSuccess() {
        timer?.cancel()
        if (timer == null) {
            timer = MyCountDownTimer(
                Constants.SEND_CAPTCHA_COUNT_DOWN_TIME.toLong(),
                Constants.COUNT_DOWN_INTERVAL.toLong(), binding?.tvGetCode
            )
        }
        timer?.start()
    }
    override fun setStatusBarView(): Int {
        return R.id.status_bar_view
    }

    private fun getPhone(): String? {
        return binding?.etAccount?.text.toString()
    }

    private fun getCode(): String? {
        return binding?.etCode?.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer?.cancel()
        }
    }

    override fun initSubscription() {

    }

    override fun generateBinding(): ViewBinding? {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initData() {
        viewModel.loginLiveData.observe(this){response->
            if (response.isSuccess){
                GlobalConfig.setUserId(response.data?.userId)
                showToast(response.data?.msg)
                StorageConfig.putString(ConfigKeys.SP_PHONE, getPhone())
                onLoginSuccess()
            }else{
                showToast(response.msg)
            }
        }
        viewModel.verificationCodeLiveData.observe(this){response->
            if (response.isSuccess){
                showToast(response.msg)
                onSendCodeSuccess()
            }else{
                showToast(response.msg)
            }
        }
    }
}