package com.tg.vloan.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<B :ViewBinding>() : DialogFragment() {

    var binding: B? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = generateBinding() as B?
        initView()
        return binding!!.root
    }
    abstract fun initView()

    abstract fun generateBinding(): ViewBinding?
}