package com.tg.vloan.utils;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.tg.vloan.R;
import com.tg.vloan.config.Constants;

/**
 * Created by frcx-hb on 2022/12/3 13:53.
 */
public class MyCountDownTimer extends CountDownTimer {

    private Button btn;
    private TextView tvBtn;

    public MyCountDownTimer(long millisInFuture,
                            long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    public MyCountDownTimer(long millisInFuture,
                            long countDownInterval, TextView btn) {
        super(millisInFuture, countDownInterval);
        this.tvBtn = btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String time = millisUntilFinished / Constants.COUNT_DOWN_INTERVAL + "秒后重发";
        if (btn != null) {
            btn.setEnabled(false);
            btn.setText(time);
            return;
        }
        if (tvBtn != null) {
            tvBtn.setEnabled(false);
            tvBtn.setText(time);
        }
    }

    @Override
    public void onFinish() {
        if (btn != null) {
            btn.setText(ResourceUtils.getString(R.string.base_send_captcha));
            btn.setEnabled(true);
            return;
        }
        if (tvBtn != null) {
            tvBtn.setText(ResourceUtils.getString(R.string.base_send_captcha));
            tvBtn.setEnabled(true);
        }
    }

}
