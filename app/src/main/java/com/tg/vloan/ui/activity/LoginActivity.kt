package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.viewbinding.ViewBinding
import com.tg.vloan.R
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.bean.IsCheckBean
import com.tg.vloan.callback.SimpleTextWatcher
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.Constants
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.databinding.ActivityLoginBinding
import com.tg.vloan.utils.MyCountDownTimer
import com.tg.vloan.utils.ResourceUtils

class LoginActivity: BaseActivity<ActivityLoginBinding>() {


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
//        safeLaunch {
//            val codeRequest = CodeRequest(getPhone(), type)
//            var sendCodeStr= Post<String>(Api.sendCode){
//                param("phone",codeRequest.phone)
//                param("type",codeRequest.type)
//                //json()
//            }.await()
//            var sendCodeResponse: BaseResponse<RenewUrlBean> = Gson().fromJson(sendCodeStr,BaseResponse::class.java) as BaseResponse<RenewUrlBean>
//            //val data: RenewUrlBean = sendCodeResponse.getData()
//            var msg = if (sendCodeResponse.data == null) {
//                sendCodeResponse.msg
//            } else {
//                sendCodeResponse.data!!.msg
//            }
//            showToast(msg!!)
//            onSendCodeSuccess()
//        }.catch {
//            showToast(it.message.toString())
//        }
    }

    /**
     * 登录
     */
    private fun login() {
//        if (TextUtils.isEmpty(getPhone())) {
//            showToast(ResourceUtils.getString(R.string.base_input_phone_tip))
//            return
//        }
//        if (TextUtils.isEmpty(getCode())) {
//            showToast(ResourceUtils.getString(R.string.base_input_captcha_tip))
//            return
//        }
//        if (!binding!!.ivCheck.isSelected()) {
//            showToast(ResourceUtils.getString(R.string.base_agree_policy_tip))
//            return
//        }
//        scopeDialog {
////            RequestBody: {"code":"1234","oaid":"8d9410df-8b94-4173-b24b-2e4f114daafb","phone":"18800001111","type":"oaid"}
//            val loginRequest = LoginRequest(getPhone(), getCode())
//            var loginResponse = Post<BaseResponse<UserBean>>(Api.login){
//                param("phone" , loginRequest.phone)
//                param("code",loginRequest.code)
//                param("oaid",loginRequest.oaid)
//                param("type",loginRequest.type)
//            }.await()
//            if (loginResponse.data == null){
//                showToast(loginResponse.msg);
//            }else{
//                GlobalConfig.setUserId(loginResponse.data.getUserId())
//                showToast(loginResponse.data.msg)
//            }
//            SPConfig.putString(ConfigKeys.SP_PHONE, getPhone())
//            onLoginSuccess()
//        }
    }

    private fun onLoginSuccess() {
        MainActivity.startActivity(this)
        finish()
    }

    fun onSendCodeSuccess() {
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

    fun getPhone(): String? {
        return binding?.etAccount?.text.toString()
    }

    fun getCode(): String? {
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
    }
}