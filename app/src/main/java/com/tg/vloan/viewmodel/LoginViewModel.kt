package com.tg.vloan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.tg.vloan.base.BaseViewModel
import com.tg.vloan.bean.BaseResponse
import com.tg.vloan.bean.RenewUrlBean
import com.tg.vloan.bean.UserBean
import com.tg.vloan.dto.CodeRequest
import com.tg.vloan.dto.LoginRequest
import com.tg.vloan.utils.safeLaunch

class LoginViewModel:BaseViewModel() {
    var loginLiveData: MutableLiveData<BaseResponse<UserBean?>> = MutableLiveData()

    var verificationCodeLiveData:MutableLiveData<BaseResponse<RenewUrlBean?>> = MutableLiveData()

    fun login(request: LoginRequest){
        safeLaunch {
           var loginResponse = apiService?.login(request)
            loginLiveData.postValue(loginResponse)
        }.catch {
            loginLiveData.postValue(BaseResponse.generateFailResponse<UserBean>(it.message))
        }
    }
    fun sendCode(request: CodeRequest){
        safeLaunch {
            var sendCodeResponse = apiService?.sendCode(request)
            if ("1" == sendCodeResponse?.code){
                verificationCodeLiveData.postValue(sendCodeResponse)
            }else{
                verificationCodeLiveData.postValue(BaseResponse.generateFailResponse<RenewUrlBean>(sendCodeResponse?.msg))
            }
        }.catch {
            verificationCodeLiveData.postValue(BaseResponse.generateFailResponse<RenewUrlBean>(it.message))
        }
    }
}