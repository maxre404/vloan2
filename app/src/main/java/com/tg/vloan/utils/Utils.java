package com.tg.vloan.utils;

import android.os.Process;

import com.google.gson.Gson;
import com.tg.vloan.config.GlobalConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by frcx-hb on 2022/12/2 11:20.
 */
public class Utils {

    /**
     * 通过文件获取数据
     */
    public static <T> T getDataFromJsonFile(String fileName, Type type) {
        T data = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            assert GlobalConfig.getApplicationContext() != null;
            InputStream inputStream = GlobalConfig.getApplicationContext().getAssets().open(fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            String config = stringBuilder.toString();
            inputStream.close();
            bf.close();
            data = new Gson().fromJson(config, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @SafeVarargs
    public static <T> List<T> asList(T... t) {
        return new ArrayList<>(Arrays.asList(t));
    }

    /**
     * 根据className反射获取实例
     */
    public static <T> T getInstanceByClassName(String className) {
        T instance = null;
        try {
            Class<T> tClass = (Class<T>) Class.forName(className);
            instance = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 手机号中间四位设为*
     */
    public static String formatPhone(String phone) {
        String prePhone = phone.substring(0, 3);
        String endPhone = phone.substring(phone.length() - 4);
        return prePhone.concat("****").concat(endPhone);
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
