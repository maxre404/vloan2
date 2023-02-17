package com.tg.vloan.utils;

import android.os.Build;

import com.tg.vloan.config.ConfigKeys;
import com.tg.vloan.config.SPConfig;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * Created by frcx-hb on 2022/12/3 11:01.
 */
public class DeviceIDUtil {

    /**
     * 仅当获取不到android_id或android_id不合法时使用该方法
     *
     * @return
     */
    public static String getUniqueDeviceID() {
        String uuid = SPConfig.getString(ConfigKeys.SP_UUID, null);
        if (uuid != null) {
            return uuid;
        }
        String part1 = null;
        part1 = getMac();
        String part2 = getM_SZDevIDShort();
        String m_szLongID = part1 + part2;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            if (b <= 0xF)
                m_szUniqueID += "0";
            m_szUniqueID += Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();
        SPConfig.putString(ConfigKeys.SP_UUID, m_szUniqueID);
        return m_szUniqueID;
    }

    private static String getMac() {
        String macAddress = null;
        try {
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface netWork = interfaces.nextElement();
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : by) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                String mac = builder.toString();
                if (netWork.getName().equals("wlan0")) {
                    macAddress = mac;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return macAddress;
    }

    private static String getM_SZDevIDShort() {
        String m_szDevIDShort =
                Build.BOARD +
                        Build.BRAND +
                        Build.CPU_ABI +
                        Build.DEVICE +
                        Build.DISPLAY +
                        Build.HOST +
                        Build.ID +
                        Build.MANUFACTURER +
                        Build.MODEL +
                        Build.PRODUCT +
                        Build.TAGS +
                        Build.TYPE;
        return m_szDevIDShort;
    }


}

