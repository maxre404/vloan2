package com.tg.vloan.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * Created by frcx-hb on 2022/12/1 22:34.
 */
public interface IView<B extends ViewBinding> {
    B getBinding();


    @NonNull
    B createViewBinding(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container);
}
