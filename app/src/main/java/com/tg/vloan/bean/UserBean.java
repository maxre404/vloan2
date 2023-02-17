package com.tg.vloan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by frcx-hb on 2022/12/3 10:51.
 */
public class UserBean {
    private String msg;

    @SerializedName("is_new")
    private  String isNew;

    @SerializedName("user_id")
    private String userId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
