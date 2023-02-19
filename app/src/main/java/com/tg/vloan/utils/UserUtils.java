package com.tg.vloan.utils;

import android.text.TextUtils;
import com.tg.vloan.config.GlobalConfig;
import com.tg.vloan.ui.activity.LoginActivity;

/**
 * Created by frcx-hb on 2022/12/5 16:55.
 */
public class UserUtils {

    public static boolean isLogin() {
        return !TextUtils.isEmpty(GlobalConfig.getUserId());
    }

    public static void login() {
        if (isLogin()) {
            return;
        }
        LoginActivity.Companion.startActivity(GlobalConfig.getCurrentActivity());
    }

    public static void logout() {
        if (!isLogin()) {
            return;
        }
        GlobalConfig.clearUserId();
        login();
        if (GlobalConfig.getCurrentActivity() != null) {
            GlobalConfig.getCurrentActivity().finishAffinity();
        }
    }


}
