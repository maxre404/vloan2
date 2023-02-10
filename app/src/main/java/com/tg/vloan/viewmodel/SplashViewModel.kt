package com.tg.vloan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tg.vloan.utils.LogUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel:ViewModel() {

    fun loadData(){
        viewModelScope.launch {
            LogUtil.log("开始执行")
            delay(12*1000)
            LogUtil.log("携程执行完毕+++++++++")
        }
    }

}