package com.tg.vloan.net;

import com.tg.vloan.config.GlobalConfig;
import com.tg.vloan.utils.DeviceUtils;
import com.tg.vloan.utils.LogUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by frcx-hb on 2022/12/3 16:42.
 * 参数拦截器，拦截并添加公共参数
 */
public class UrlParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtil.log("bbbbbbb");
        HttpUrl paramsUrl = chain.request().url().newBuilder()
                .addQueryParameter("main_channel", "1")
                .addQueryParameter("t", String.valueOf(System.currentTimeMillis()))
                .addQueryParameter("app_version", DeviceUtils.getAppVersion())
                .addQueryParameter("platform", "1")
                .addQueryParameter("ming", "1")
                .addQueryParameter("user_id", GlobalConfig.getUserId()).build();
        Request.Builder builder = chain.request().newBuilder().url(paramsUrl);
        LogUtil.log("bbbbb");
        return chain.proceed(builder.build());
    }
}
