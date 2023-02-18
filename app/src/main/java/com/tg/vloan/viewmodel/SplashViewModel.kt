package com.tg.vloan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.tg.vloan.base.BaseViewModel
import com.tg.vloan.bean.BaseResponse
import com.tg.vloan.bean.IsCheckBean
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.utils.safeLaunch

class SplashViewModel : BaseViewModel() {
    var isCheckLiveData: MutableLiveData<BaseResponse<IsCheckBean?>> = MutableLiveData()

    fun checkUser() {
        safeLaunch {
            var appInfoBean = apiService?.getAppInfo()
            GlobalConfig.putConfig(ConfigKeys.APP_INFO, appInfoBean?.data)
            var isCheckResponse = apiService?.isCheck()
            GlobalConfig.putConfig(ConfigKeys.IS_CHECK_BEAN, isCheckResponse?.data)
            isCheckLiveData.postValue(isCheckResponse)
        }.catch {
            it.printStackTrace()
            isCheckLiveData.postValue(BaseResponse.generateFailResponse<IsCheckBean>(it.message))
        }
    }


}