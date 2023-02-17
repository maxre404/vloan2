package com.tg.vloan.bean;

public class PersonalCenterEntity {
    private int imgRes;

    private String itemTitle;

    public PersonalCenterEntity(int imgRes, String itemTitle) {
        this.imgRes = imgRes;
        this.itemTitle = itemTitle;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}
