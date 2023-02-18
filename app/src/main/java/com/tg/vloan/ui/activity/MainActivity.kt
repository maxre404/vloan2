package com.tg.vloan.ui.activity

import android.content.Context
import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.tg.vloan.base.BaseActivity
import com.tg.vloan.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun initSubscription() {

    }

    override fun generateBinding(): ViewBinding? {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun initData() {
    }
    companion object {
        fun startActivity(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}