package com.tg.vloan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by frcx-hb on 2022/12/3 14:34.
 */
public class AppInfoBean {
    @SerializedName("app_name")
    private String appName;

    private String version;

    @SerializedName("app_company")
    private String appCompany;

    @SerializedName("app_address")
    private String appAddress;

    @SerializedName("app_mail")
    private String appMail;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppCompany() {
        return appCompany;
    }

    public void setAppCompany(String appCompany) {
        this.appCompany = appCompany;
    }

    public String getAppAddress() {
        return appAddress;
    }

    public void setAppAddress(String appAddress) {
        this.appAddress = appAddress;
    }

    public String getAppMail() {
        return appMail;
    }

    public void setAppMail(String appMail) {
        this.appMail = appMail;
    }
}
