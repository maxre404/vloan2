package com.tg.vloan.utils;

import android.util.Log;

public class LogUtil {
    public static String TAG = "test";
    public static void log(String message){
        Log.d(TAG, ""+message);
    }
}
