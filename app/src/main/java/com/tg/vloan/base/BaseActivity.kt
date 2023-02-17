package com.tg.vloan.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import me.yokeyword.fragmentation.SupportActivity

abstract class BaseActivity<V : ViewBinding> : SupportActivity() {
    var binding:V?=null
//    val viewModel by viewModels<viewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding() as V?
        setContentView(binding?.root)
        initView()
        initData()
    }

    abstract fun createBinding():ViewBinding?

    abstract fun initView()

    abstract fun initData()
 }