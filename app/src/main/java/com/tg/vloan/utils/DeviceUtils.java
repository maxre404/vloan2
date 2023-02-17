package com.tg.vloan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.tg.vloan.config.GlobalConfig;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by frcx-hb on 2022/12/3 11:00.
 */
public class DeviceUtils {

    public static String getDeviceID() {
        String ANDROID_ID = Settings.System.getString(GlobalConfig.getApplicationContext()
                .getContentResolver(), Settings.Secure.ANDROID_ID);
        if (ANDROID_ID != null && !ANDROID_ID.equals("9774d56d682e549c")) {
            try {
                String uuid = UUID.nameUUIDFromBytes(ANDROID_ID.getBytes("utf8"))
                        .toString();
                return uuid;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return DeviceIDUtil.getUniqueDeviceID();
            }
        }
        return DeviceIDUtil.getUniqueDeviceID();
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getDeviceType() {
        return Build.MODEL;
    }

    public static String getOperationSystem() {
        return "Android";
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getAppVersion() {
        String version = "";
        PackageManager packageManager = GlobalConfig.getApplicationContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager
                    .getPackageInfo(GlobalConfig.getApplicationContext().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getAppName(){
        PackageManager packageManager = GlobalConfig.getApplicationContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager
                    .getPackageInfo(GlobalConfig.getApplicationContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return GlobalConfig.getApplicationContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "UNKNOWN";
    }

    public static Point getScreenSize(){
        Point point = new Point();
        Context context = GlobalConfig.getApplicationContext();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        if(windowMgr!=null) {
            windowMgr.getDefaultDisplay().getRealMetrics(dm);
            // 获取高度
            point.y = dm.heightPixels;
            // 获取宽度
            point.x = dm.widthPixels;
        }
        return point;
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        int statusHeight = 0;
        Resources resource = GlobalConfig.getApplicationContext().getResources();
        int identifier = resource.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            statusHeight = resource.getDimensionPixelSize(identifier);
        }
        return statusHeight;
    }

}
