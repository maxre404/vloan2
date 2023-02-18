package com.tg.vloan.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by frcx-hb on 2022/12/3 18:37.
 */
class AdvertBean {
    var id: String? = null
    var icon: String? = null
    var title: String? = null
    var desc: String? = null
    var label: String? = null

    @SerializedName("min_price")
    var minPrice: String? = null

    @SerializedName("max_price")
    var maxPrice: String? = null

    @SerializedName("apple_count")
    var appleCount: String? = null

    @SerializedName("success_rate")
    var successRate: String? = null

    @SerializedName("day_rate")
    var dayRate: String? = null
    var url: String? = null

    @SerializedName("is_vip")
    var isVip: String? = null

    //1正常产品不用加APP名字
    @SerializedName("is_audit")
    var isAudit: Int = 0
}