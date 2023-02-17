package com.tg.vloan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by frcx-hb on 2022/12/3 18:37.
 */
public class AdvertBean {
    
    private String id;
    
    private String icon;
    
    private String title;
    
    private String desc;
    
    private String label;

    @SerializedName("min_price")
    private String minPrice;

    @SerializedName("max_price")
    private String maxPrice;

    @SerializedName("apple_count")
    private String appleCount;

    @SerializedName("success_rate")
    private String successRate;

    @SerializedName("day_rate")
    private String dayRate;
    
    private String url;

    @SerializedName("is_vip")
    private String isVip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getAppleCount() {
        return appleCount;
    }

    public void setAppleCount(String appleCount) {
        this.appleCount = appleCount;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public String getDayRate() {
        return dayRate;
    }

    public void setDayRate(String dayRate) {
        this.dayRate = dayRate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }
}
