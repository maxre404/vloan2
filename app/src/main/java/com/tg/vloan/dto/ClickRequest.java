package com.tg.vloan.dto;

/**
 * Created by frcx-hb on 2022/12/3 21:41.
 */
public class ClickRequest {

    public ClickRequest(String id, String type) {
        this.id = id;
        this.type = type;
    }

    private String id;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
