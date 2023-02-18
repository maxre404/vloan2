package com.tg.vloan.utils;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by frcx-hb on 2022/12/1 23:04.
 */
public class EmptyUtils {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static String formatString(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }
}
