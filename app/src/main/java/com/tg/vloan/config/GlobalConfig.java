package com.tg.vloan.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.tg.vloan.App;

import java.lang.ref.WeakReference;

/**
 * Created by frcx-hb on 2022/12/1 22:22.
 * 全局配置类
 */
public final class GlobalConfig {
    private static WeakReference<Application> mContext;

    public static void init(Application application) {
        mContext = new WeakReference<>(application);
    }

    /**
     * 仅用于在运行时缓存配置，持久化缓存使用SPConfig
     */
    public static void putConfig(String key, Object object) {
        Configurator.getInstance().putConfig(key, object);
    }

    public static <T> T getConfig(String key) {
        return Configurator.getInstance().getConfig(key);
    }

    public static void setUserId(String userId) {
        Configurator.getInstance().putConfig(ConfigKeys.SP_USER_ID, userId);
        SPConfig.putString(ConfigKeys.SP_USER_ID, userId);
    }

    public static void clearUserId() {
        Configurator.getInstance().putConfig(ConfigKeys.SP_USER_ID, null);
        SPConfig.putString(ConfigKeys.SP_USER_ID, null);
    }

    public static String getUserId() {
        String userId = Configurator.getInstance().getConfig(ConfigKeys.SP_USER_ID);
        if (userId == null) {
            userId = SPConfig.getString(ConfigKeys.SP_USER_ID, null);
        }
        return userId;
    }

    public static Context getApplicationContext() {
         return mContext.get().getApplicationContext();
    }

    /**
     * 获取当前activity
     */
    public static Activity getCurrentActivity() {
        return ((App) mContext.get()).getCurrentActivity();
    }
}