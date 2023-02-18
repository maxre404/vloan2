package com.tg.vloan.bean;

/**
 * Created by frcx-hb on 2022/12/2 17:49.
 */
public class BaseResponse<T> {
    private String code;

    private String msg;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return "200".equals(code)||"1".equals(code);
    }
    public static <T> BaseResponse<T> generateFailResponse(String errorMsg){
        BaseResponse response = new BaseResponse<T>();
        response.code = "-99";
        response.data = null;
        response.msg = errorMsg;
        return response;
    }
}
