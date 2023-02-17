package com.tg.vloan.dto;

/**
 * Created by frcx-hb on 2022/12/3 13:48.
 */
public class CodeRequest {

    private String phone;

    private String type;

    public CodeRequest(String phone, String type) {
        this.phone = phone;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
