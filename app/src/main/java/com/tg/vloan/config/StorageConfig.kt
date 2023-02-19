package com.tg.vloan.config

import android.content.Context
import com.tencent.mmkv.MMKV


/**
 * Created by frcx-hb on 2022/12/1 22:28.
 */
object StorageConfig {
    private val storage = MMKV.defaultMMKV()

    @JvmStatic
    fun putString(key: String?, value: String?) {
//        storage.encode(key,value)
        storage.putString(key,value)
    }

    fun putInt(key: String?, value: Int) {
//        storage.encode(key,value)
        storage.putInt(key,value)
    }

    fun putLong(key: String?, value: Long) {
//        storage.encode(key,value)
        storage.putLong(key,value)
    }

    fun putBoolean(key: String?, value: Boolean) {
        storage.encode(key,value)
    }

    @JvmStatic
    fun getString(key: String?, dft: String?): String? {
        return storage.getString(key,dft)
    }

    fun getBoolean(key: String?, dft: Boolean): Boolean {
        return storage.getBoolean(key,dft)
    }
}