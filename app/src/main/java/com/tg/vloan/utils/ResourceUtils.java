package com.tg.vloan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.tg.vloan.config.GlobalConfig;

/**
 * Created by frcx-hb on 2022/12/3 14:05.
 */
public class ResourceUtils {

    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getDrawable(int drawableResId) {
        return GlobalConfig.getApplicationContext().getResources().getDrawable(drawableResId);
    }

    public static int getDrawableResId(String resName, Context context) {
        return context.getResources()
                .getIdentifier(resName, "drawable", context.getPackageName());
    }

    public static int getMipmapResId(String resName) {
        Context context = GlobalConfig.getApplicationContext();
        return  context.getResources().getIdentifier(resName, "mipmap", context.getPackageName());
    }

    public static String getString(int resId) {
        return GlobalConfig.getApplicationContext().getString(resId);
    }

    public static String getString(String resName) {
        Context context = GlobalConfig.getApplicationContext();
        int resId = context.getResources()
                .getIdentifier(resName, "string", context.getPackageName());
        if (resId > 0) {
            return GlobalConfig.getApplicationContext().getString(resId);
        } else {
            return resName;
        }
    }

    public static int getStringResId(String resName, Context context) {
        return context.getResources()
                .getIdentifier(resName, "string", context.getPackageName());
    }

    public static int getColorResId(String resName) {
        Context context = GlobalConfig.getApplicationContext();
        return context.getResources().getIdentifier(resName, "color", context.getPackageName());
    }

    public static int getColor(int resId) {
        return GlobalConfig.getApplicationContext().getResources().getColor(resId);
    }

    public static int getColorAttr(Context context, int resId, boolean fromResource) {
        if (fromResource) {
            return getColor(resId);
        }
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(resId, typedValue, true);
        return typedValue.data;
    }

    public static String getColorString(int resId) {
        StringBuilder stringBuilder = new StringBuilder();
        int color = getColor(resId);
        stringBuilder.append("#");
        stringBuilder.append(Integer.toHexString(Color.red(color)));
        stringBuilder.append(Integer.toHexString(Color.green(color)));
        stringBuilder.append(Integer.toHexString(Color.blue(color)));
        return stringBuilder.toString();
    }

    public static int getResIdAttr(Context context, int resId, boolean fromResource) {
        if (fromResource) {
            return getColor(resId);
        }
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(resId, typedValue, true);
        return typedValue.resourceId;
    }

    public static int getDimen(int resId) {
        return GlobalConfig.getApplicationContext().getResources().getDimensionPixelSize(resId);
    }

    public static int getDimen(String resName) {
        int resId = GlobalConfig.getCurrentActivity().getResources()
                .getIdentifier(resName, "dimen", GlobalConfig.getCurrentActivity().getPackageName());
        if (resId > 0) {
            return getDimen(resId);
        } else {
            return -1;
        }
    }

    public static float getDimenF(int resId) {
        return GlobalConfig.getApplicationContext().getResources().getDimension(resId);
    }
}
