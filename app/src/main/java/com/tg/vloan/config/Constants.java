package com.tg.vloan.config;

import com.tg.vloan.net.ApiPath;

/**
 * Created by frcx-hb on 2022/12/1 22:20.
 */
public class Constants {
    public static final int TIME_OUT = 15;

    public static final int SPLASH_COUNT_DOWN_TIME = 3000;

    public static final int SEND_CAPTCHA_COUNT_DOWN_TIME = 60000;

    public static final int COUNT_DOWN_INTERVAL = 1000;

    public static final int PRESS_EXIT_TIME = 2000;

    public static String BASE_URL = ApiPath.getBaseUrl;

    public static final String SUCCESS = "success";

    public static final String TYPE = "type";

    public static final String URL = "url";
    public static final String TITLE = "title";

    public static final String IS_AUDIO = "IS_AUDIO";


    /**
     * 协议类型
     */
    public static class PolicyType {
        public static final int TYPE_PRIVACY = 0;   //隐私协议
        public static final int TYPE_REGISTER = 1;  //注册协议
    }

    /**
     * 配置文件名
     */
    public static class ConfigFile {
        public static final String JSON_MAIN_CONFIG = "main_config.json";
        public static final String JSON_GUIDE_RES_CONFIG = "guide_image_res_config.json";
    }
}
