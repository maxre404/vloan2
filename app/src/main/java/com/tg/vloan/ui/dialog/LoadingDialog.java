package com.tg.vloan.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tg.vloan.R;
import com.tg.vloan.base.BaseRelativeLayout;
import com.tg.vloan.databinding.ViewDialogBinding;
import com.tg.vloan.ui.view.CustomLoadingView;

/**
 * Created by frcx-hb on 2022/12/6 15:23.
 */
public class LoadingDialog extends Dialog {

    private DialogView dialogView;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.Dialog_Loading);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        WindowManager.LayoutParams attributes = getWindow().getAttributes();
    }

    @Override
    public void setContentView(@NonNull View view) {
        dialogView = new DialogView(getContext());
        dialogView.addContentView(view);
        super.setContentView(dialogView);
    }

    public void show(String text) {
        if (dialogView != null) {
            dialogView.setText(text);
        }
        super.show();
    }

    public static LoadingDialog createLoading(Context context, boolean cancelable) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.setCancelable(cancelable);
        CustomLoadingView loadingView = new CustomLoadingView(context);
        loadingDialog.setContentView(loadingView);
        return loadingDialog;
    }

    private static class DialogView extends BaseRelativeLayout<ViewDialogBinding> {

        public DialogView(Context context) {
            super(context);
        }

        public DialogView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public DialogView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        private void addContentView(View view) {
            getBinding().rltContainer.addView(view);
        }

        private void setText(String text) {
            getBinding().tvMsg.setText(text);
        }

        @NonNull
        @Override
        public ViewDialogBinding createViewBinding(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container) {
            return ViewDialogBinding.inflate(layoutInflater, container, true);
        }
    }
}
