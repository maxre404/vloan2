package com.tg.vloan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.tg.vloan.base.BaseViewModel
import com.tg.vloan.bean.AdvertBean
import com.tg.vloan.bean.BaseResponse
import com.tg.vloan.utils.safeLaunch

class HomeViewModel:BaseViewModel() {
    var advertListLiveData:MutableLiveData<BaseResponse<List<AdvertBean?>?>?> = MutableLiveData()
    fun loadData(){
        safeLaunch {
            var advertListResponse = apiService?.getAdvertList()
            advertListLiveData.postValue(advertListResponse)
        }.catch {
            advertListLiveData.postValue(BaseResponse.generateFailResponse<List<AdvertBean?>?>(it.message))
        }
    }

}