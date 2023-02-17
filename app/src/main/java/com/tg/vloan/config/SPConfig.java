package com.tg.vloan.config;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by frcx-hb on 2022/12/1 22:28.
 */
public class SPConfig {
    private static final SharedPreferences PREFERENCES =
            GlobalConfig.getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);

    public static SharedPreferences getPreferences() {
        return PREFERENCES;
    }

    public static void putString(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public static void putInt(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    public static void putLong(String key, long value) {
        getPreferences().edit().putLong(key, value).apply();
    }

    public static void putBoolean(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    public static void removeKey(String key){
        getPreferences().edit().remove(key).apply();
    }

    public static String getString(String key, String dft) {
        return getPreferences().getString(key, dft);
    }

    public static int getInt(String key, int dft) {
        return getPreferences().getInt(key, dft);
    }

    public static long getLong(String key, long dft) {
        return getPreferences().getLong(key, dft);
    }

    public static boolean getBoolean(String key, boolean dft) {
        return getPreferences().getBoolean(key, dft);
    }

    public static void clearAll() {
        getPreferences().edit().clear().apply();
    }

    public static void putStringSet(String key, Set<String> value){
        getPreferences().edit().putStringSet(key,value).apply();
    }

    public static Set<String> getStringSet(String key, Set<String> defValue){
        return getPreferences().getStringSet(key,defValue);
    }
}
