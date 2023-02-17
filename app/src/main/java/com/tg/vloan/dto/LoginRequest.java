package com.tg.vloan.dto;

import android.text.TextUtils;

import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.tg.vloan.config.GlobalConfig;
import com.tg.vloan.utils.DeviceIDUtil;

/**
 * Created by frcx-hb on 2022/12/3 10:53.
 */
public class LoginRequest {

    private String phone;

    private String code;

    private String type;

    private String oaid;

    private String imei;

    public String getPhone() {
        return phone;
    }

    public LoginRequest() {
        this(null, null);
    }

    public LoginRequest(String phone, String code) {
        this.phone = phone;
        this.code = code;
        //eb32994553654d65
        String oaid = DeviceIdentifier.getOAID(GlobalConfig.getApplicationContext());
        String imei = DeviceIDUtil.getUniqueDeviceID();
        if (!TextUtils.isEmpty(oaid)) {
            type = "oaid";
            this.oaid = oaid;
        } else if (!TextUtils.isEmpty(imei)) {
            this.type = "imei";
            this.imei = imei;
        }
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
