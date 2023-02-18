package com.tg.vloan.base

import androidx.lifecycle.ViewModel
import com.tg.vloan.dto.ClickRequest
import com.tg.vloan.net.ApiService
import com.tg.vloan.net.RetrofitClient
import com.tg.vloan.utils.safeLaunch

open class BaseViewModel : ViewModel() {
    var apiService: ApiService? = null

    init {
        apiService = RetrofitClient.getInstance().createService(ApiService::class.java)
    }

    fun click(request: ClickRequest) {
        safeLaunch {
            apiService?.click(request)
        }
    }
}