package com.tg.vloan.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.tg.vloan.base.BaseViewModel
import com.tg.vloan.net.AndroidScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


fun ViewModel.safeLaunch(dispatcher: CoroutineDispatcher = Dispatchers.Main,block: suspend CoroutineScope.() -> Unit): AndroidScope {
    return AndroidScope(this.viewModelScope, dispatcher = dispatcher).safeLaunch(block)
}
//fun BaseViewModel.safeLaunch(dispatcher: CoroutineDispatcher = Dispatchers.Main,block: suspend CoroutineScope.() -> Unit): AndroidScope {
//    return AndroidScope(this.viewModelScope, dispatcher = dispatcher).safeLaunch(block)
//}
fun AppCompatActivity.safeLaunch(dispatcher: CoroutineDispatcher = Dispatchers.Main, block: suspend CoroutineScope.() -> Unit): AndroidScope {
    return AndroidScope(this.lifecycleScope, dispatcher = dispatcher).safeLaunch(block)
}