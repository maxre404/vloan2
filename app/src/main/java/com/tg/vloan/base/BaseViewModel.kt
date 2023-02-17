package com.tg.vloan.base

import androidx.lifecycle.ViewModel
import com.tg.vloan.net.ApiService
import com.tg.vloan.net.RetrofitClient

open class BaseViewModel:ViewModel() {
    var apiService:ApiService?=null
    init {
        apiService = RetrofitClient.getInstance().createService(ApiService::class.java)
    }
}