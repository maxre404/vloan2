package com.tg.vloan.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.viewbinding.ViewBinding;

/**
 * Created by frcx-hb on 2022/12/1 23:06.
 */
public abstract class BaseRelativeLayout<B extends ViewBinding> extends RelativeLayout
        implements IView<B> {

    private B binding;

    public BaseRelativeLayout(Context context) {
        this(context, null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        binding = createViewBinding(LayoutInflater.from(getContext()), this);
    }

    @Override
    public B getBinding() {
        return binding;
    }

}
