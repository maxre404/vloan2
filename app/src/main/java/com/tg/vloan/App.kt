package com.tg.vloan

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.github.gzuliyujiang.oaid.DeviceIdentifier
import com.tencent.mmkv.MMKV
import com.tg.vloan.config.ConfigKeys
import com.tg.vloan.config.GlobalConfig
import com.tg.vloan.config.StorageConfig
import java.lang.ref.WeakReference

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        application=this
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
        GlobalConfig.init(this)
        MMKV.initialize(this)
        if (StorageConfig.getBoolean(ConfigKeys.SP_AGREE_POLICY, false)) {
            DeviceIdentifier.register(this)
        }
    }
    private val lifecycleCallbacks: ActivityLifecycleCallbacks =
        object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
                mCurrentActivity = WeakReference(activity)
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        }

    fun getCurrentActivity(): Activity? {
        return if (mCurrentActivity != null) mCurrentActivity!!.get() else null
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(lifecycleCallbacks)
    }
    companion object{
        var mCurrentActivity: WeakReference<Activity>? = null
        var application:Application?=null
    }
}