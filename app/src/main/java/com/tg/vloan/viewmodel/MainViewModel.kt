package com.tg.vloan.viewmodel

import com.tg.vloan.base.BaseViewModel
import com.tg.vloan.dto.DownloadRequest
import com.tg.vloan.utils.safeLaunch

class MainViewModel:BaseViewModel() {


    fun downloadReport(request: DownloadRequest){
        safeLaunch {
            apiService?.download(request)
        }
    }
}