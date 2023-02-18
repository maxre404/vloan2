package com.tg.vloan.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.gyf.barlibrary.ImmersionBar
import com.tg.vloan.bean.IsCheckBean
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.ui.dialog.LoadingDialog
import com.tg.vloan.utils.LogUtil
import me.yokeyword.fragmentation.SupportActivity


abstract class BaseActivity<V : ViewBinding> : SupportActivity() {
    var binding: V? = null

    //    val viewModel by viewModels<viewModel>()
    private var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding = generateBinding() as V?
        setSecurity()
        setContentView(binding?.root)
        initData()
        initView()
        initSubscription()
//        immersive()
        initImmersionBar()
        if (isImmersionBarEnabled() && setStatusBarView() != 0) {
            ImmersionBar.setStatusBarView(this, findViewById(setStatusBarView()))
        }
    }

    abstract fun initSubscription()

    abstract fun generateBinding(): ViewBinding?

    abstract fun initView()

    abstract fun initData()

    open fun setSecurity() {
        val isCheckBean: IsCheckBean? = GlobalConfig.getConfig(ConfigKeys.IS_CHECK_BEAN)
        if (isCheckBean == null || "0" == isCheckBean.isAudit) {
            //isCheckBean为空或者isCheck为0禁止截屏以及录屏
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    open fun setStatusBarView(): Int {
        return 0
    }

    open fun isImmersionBarEnabled(): Boolean {
        return true
    }

    open fun initImmersionBar() {
        ImmersionBar.with(this).navigationBarColorInt(Color.WHITE)
            .statusBarDarkFont(false).init()
    }

    open fun showToast(msg: String?) {
        try {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        } catch (e:Exception){
            e.printStackTrace()
            LogUtil.log("打印异常:${e.message}")
        }
    }
    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.createLoading(this, false)
        }
        if (progressHandle == null) {
            progressHandle = Handler(Looper.getMainLooper())
        }
        if (loadingDialog!!.isShowing) {
            return
        }
        progressHandle!!.post(progressRunnable)
    }

    private var progressHandle: Handler? = null

    private val progressRunnable: Runnable = object : Runnable {
        override fun run() {
            progressHandle!!.removeCallbacks(this)
            try {
                if (!isDestroyed && loadingDialog != null && !loadingDialog!!.isShowing) {
                    loadingDialog!!.show("正在加载")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val dismissRunnable: Runnable = object : Runnable {
        override fun run() {
            progressHandle!!.removeCallbacks(this)
            try {
                if (!isDestroyed && loadingDialog != null && loadingDialog!!.isShowing) {
                    loadingDialog!!.dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun dismissLoading() {
        if (progressHandle != null) {
            progressHandle!!.post(dismissRunnable)
        }
    }

}