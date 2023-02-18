package com.tg.vloan.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.tg.vloan.R;
import com.tg.vloan.utils.ResourceUtils;

/**
 * Created by frcx-hb on 2022/12/7 10:36.
 */
public class CustomLoadingView extends View {


    private int mSize;
    private int mPaintColor;

    private Paint mPaint;

    private int mAnimationValue = 0;

    private ValueAnimator mAnimator;

    private final int LINE_COUNT = 12;
    private final int DEGREE_PER_LINE = 360 / LINE_COUNT;

    private int mDuration = 500;

    public CustomLoadingView(Context context) {
        this(context, null);
    }

    public CustomLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mPaintColor);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLoadingView);
        mSize = typedArray.getDimensionPixelSize(R.styleable.CustomLoadingView_loading_view_size, ResourceUtils.getDimen(R.dimen.dp_30));
        mPaintColor = typedArray.getInt(R.styleable.CustomLoadingView_android_color, Color.WHITE);
        typedArray.recycle();
    }

    public void setPaintColor(int paintColor) {
        this.mPaintColor = paintColor;
        requestLayout();
    }

    public void setSize(int size) {
        this.mSize = size;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLoading(canvas, mAnimationValue * DEGREE_PER_LINE);
    }

    private void drawLoading(Canvas canvas, int rotateDegree) {
        int width = getWidth() / LINE_COUNT;
        int height = getHeight() / 4;
        mPaint.setStrokeWidth(width);
        int centerPoint = getWidth() / 2;
        canvas.rotate(rotateDegree, centerPoint, centerPoint);
        canvas.translate(centerPoint, centerPoint);

        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.rotate(DEGREE_PER_LINE);
            mPaint.setAlpha(255 * (i + 1) / LINE_COUNT);
            canvas.translate(0, -centerPoint);
            canvas.drawLine(0, 0, 0, height, mPaint);
            canvas.translate(0, centerPoint);
        }
    }

    private void startAnimation() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1);
            mAnimator.addUpdateListener(updateListener);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setDuration(500);
        }
        if (!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }

    private void stopAnimation() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator.removeAllUpdateListeners();
        }
    }

    private final ValueAnimator.AnimatorUpdateListener updateListener
            = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mAnimationValue = (int) animation.getAnimatedValue();
            invalidate();
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }
}
