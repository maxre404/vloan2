package com.tg.vloan.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tg.vloan.R;
import com.tg.vloan.base.BaseRelativeLayout;
import com.tg.vloan.databinding.ViewHeaderBarBinding;
import com.tg.vloan.utils.ResourceUtils;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by frcx-hb on 2022/12/5 15:52.
 */
public class HeaderBar extends BaseRelativeLayout<ViewHeaderBarBinding> {

    private View.OnClickListener onBackClickListener;

    public HeaderBar(Context context) {
        this(context, null);
    }

    public HeaderBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);
        ColorStateList titleColor = typedArray.getColorStateList(R.styleable.HeaderBar_title_text_color);
        String title = typedArray.getString(R.styleable.HeaderBar_title);
        int bgColor = typedArray.getResourceId(R.styleable.HeaderBar_bg_color, R.color.white);
        typedArray.recycle();
        if (titleColor != null) {
            setTitleColor(titleColor);
        }
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        setContentBackgroundColor(ResourceUtils.getColor(bgColor));
        getBinding().ivBack.setOnClickListener(v -> onBackPressed());
    }

    public void setTitle(String title) {
        getBinding().tvTitle.setText(title);
    }

    public void setTitleColor(ColorStateList color) {
        getBinding().tvTitle.setTextColor(color);
    }

    public void setTitleColor(int color) {
        getBinding().tvTitle.setTextColor(color);
    }

    public void setContentBackgroundColor(int color) {
        getBinding().rltContent.setBackgroundColor(color);
    }

    private void onBackPressed() {
        if (onBackClickListener != null) {
            onBackClickListener.onClick(getBinding().ivBack);
        } else {
            onClose();
        }
    }

    private void onClose() {
        if (getContext() instanceof SupportActivity) {
            SupportActivity activity = (SupportActivity) getContext();
            activity.onBackPressed();
//            activity.onBackPressedSupport();
        } else if (getContext() instanceof Activity) {
            ((Activity) getContext()).onBackPressed();
        }
    }

    public void setOnBackClickListener(View.OnClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    @NonNull
    @Override
    public ViewHeaderBarBinding createViewBinding(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container) {
        return ViewHeaderBarBinding.inflate(layoutInflater, container, true);
    }
}
