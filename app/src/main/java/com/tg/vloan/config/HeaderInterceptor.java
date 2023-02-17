package com.tg.vloan.config;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by frcx-hb on 2022/12/1 22:21.
 * 请求头拦截器
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = chain.request().url().uri().toString();
        if (!url.contains(".json")) {
            Request.Builder builder = chain.request().newBuilder();
            HttpUrl oldHttpUrl = chain.request().url();
            HttpUrl baseUrl = HttpUrl.parse(Constants.BASE_URL);
            HttpUrl newHttpUrl = oldHttpUrl.newBuilder()
                    .scheme(Objects.requireNonNull(baseUrl).scheme())
                    .host(baseUrl.host())
                    .port(baseUrl.port()).build();
            Request.Builder newBuilder = builder.url(newHttpUrl);

            if (url.startsWith(Constants.BASE_URL)) {
                builder.addHeader("Content-Type", "application/json");
            }

//            int responseCode = response.code();

//            if (!TextUtils.isEmpty(GlobalConfig.getAccessToken())) {
//                builder.addHeader("Authorization", "bearer " + GlobalConfig.getAccessToken());
//            }

            //当token失效时判断当前页面不是登陆页面，则跳转只登陆页面
//            if (responseCode == 401) {
//                if (GlobalConfig.getCurrentActivity() != null &&
//                        GlobalConfig.getCurrentActivity() instanceof ILoginActivity) {
//                    ILoginActivity activity = (ILoginActivity) Hippius.getCurrentActivity();
//
//                } else {
//                    logOut();
//                }
//            }

            return chain.proceed(newBuilder.build());
        }
        return chain.proceed(chain.request());
    }

    private Handler handler;

    private boolean isLoginStatus = false;

    //多接口同时报错是要避免多次进入login界面
    private void logOut() {
        if (isLoginStatus) {
            return;
        }
        isLoginStatus = true;
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.postDelayed(() -> isLoginStatus = false, 1000);
    }

}
