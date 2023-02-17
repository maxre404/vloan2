package com.tg.vloan.dto;

/**
 * Created by frcx-hb on 2022/12/5 14:58.
 */
public class DownloadRequest {

    private String uniq;

    public DownloadRequest(String uniq) {
        this.uniq = uniq;
    }

    public String getUniq() {
        return uniq;
    }

    public void setUniq(String uniq) {
        this.uniq = uniq;
    }
}
