package com.tg.vloan.viewmodel

import com.tg.vloan.base.BaseViewModel
import com.tg.vloan.net.ApiPath
import com.tg.vloan.utils.LogUtil
import com.tg.vloan.utils.safeLaunch

class SplashViewModel:BaseViewModel() {

    fun loadData(){
        safeLaunch {
//            var result = apiService?.getUrl(ApiPath.getBaseUrl)
//            LogUtil.log("获取结果:${result}")
            var result = apiService?.getAppInfo()
            LogUtil.log("获取结果:${result}")
        }.catch {
            it.printStackTrace()
            LogUtil.log("打印异常:${it.message}")
        }
    }

}