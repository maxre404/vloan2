package com.tg.vloan.net;


import com.google.gson.GsonBuilder;
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor;
import com.tg.vloan.config.Constants;
import com.tg.vloan.config.HeaderInterceptor;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by frcx-hb on 2022/12/1 22:15.
 * retrofit网络请求框架
 */
public class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    private final Retrofit mRetrofit;
    private final OkHttpClient.Builder builder;

    private static class SingletonHolder {
        private static final RetrofitClient instance = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.instance;
    }

    private RetrofitClient() {
        builder = RetrofitUrlManager.getInstance()
                .with(new OkHttpClient.Builder())
                .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new OkHttpProfilerInterceptor())
                .addInterceptor(new UrlParamInterceptor());

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    /**
     * 设置超时时间
     *
     * @param time
     */
    public void setConnectTimeOut(int time) {
        try {
            Field callFactoryField = mRetrofit.getClass().getDeclaredField("callFactory");
            callFactoryField.setAccessible(true);
            OkHttpClient client = (OkHttpClient) callFactoryField.get(mRetrofit);

            Field connectTimeoutField = client.getClass().getDeclaredField("connectTimeout");
            connectTimeoutField.setAccessible(true);
            connectTimeoutField.setInt(client, time * 1000);

            Field readTimeoutField = client.getClass().getDeclaredField("readTimeout");
            readTimeoutField.setAccessible(true);
            readTimeoutField.setInt(client, time * 1000);

            Field writeTimeoutField = client.getClass().getDeclaredField("writeTimeout");
            writeTimeoutField.setAccessible(true);
            writeTimeoutField.setInt(client, time * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
    }
}

