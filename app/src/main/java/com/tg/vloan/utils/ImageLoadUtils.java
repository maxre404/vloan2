package com.tg.vloan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tg.vloan.config.GlobalConfig;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by frcx-hb on 2022/12/2 15:12.
 */
public class ImageLoadUtils {
    private static final String TAG = "ImageLoadUtils";
    //正式
    private static final String ossProcess = "";//阿里云图片优化地址
    //test
//    private static final String ossProcess = "?x-oss-process=style/glz-mall";//阿里云图片优化地址


    public static void loadGif(ImageView imageView, String url, @DrawableRes int resId) {
        Glide.with(GlobalConfig.getApplicationContext())
                .asGif()
                .load(url)
                .error(resId)
                .into(imageView);
    }

    public static void loadGifWithWH(ImageView imageView, String url, @DrawableRes int resId, int width, int height) {
        Glide.with(GlobalConfig.getApplicationContext())
                .asGif()
                .load(url)
                .override(width, height)
                .error(resId)
                .into(imageView);

    }

    public static void loadGif(ImageView imageView1, ImageView imageView2, String url, @DrawableRes int resId) {
        Glide.with(GlobalConfig.getApplicationContext())
                .asGif()
                .load(url)
                .error(resId)
                .into(new CustomTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
//                        if (!resource.isRunning()) {
//                            resource.setLoopCount(GifDrawable.LOOP_FOREVER);
//                            resource.start();
//                        }
//                        GifDrawable resource2 = new GifDrawable(resource, resource.getFirstFrame(), resource.getFrameTransformation());
//                        if (!resource2.isRunning()) {
//                            resource2.setLoopCount(GifDrawable.LOOP_FOREVER);
//                            resource2.start();
//                        }
//                        imageView1.setImageDrawable(resource);
//                        imageView2.setImageDrawable(resource2);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public static void loadLocalGif(ImageView imageView, int resId) {
        Glide.with(GlobalConfig.getApplicationContext())
                .asGif()
                .load(resId)
                .into(imageView);
    }


    public static void loadImage(ImageView imageView, String url) {
        Glide.with(GlobalConfig.getApplicationContext())
                .load(url)
                .into(imageView);
    }


    public static void loadImage(ImageView imageView, @DrawableRes int resId) {
        Glide.with(GlobalConfig.getApplicationContext())
                .load(resId)
                .into(imageView);
    }


    public static void loadCircleImage(ImageView imageView, @DrawableRes int resId) {
        Glide.with(GlobalConfig.getApplicationContext())
                .load(resId)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    public static void loadRadiusImage(ImageView imageView, String url, int radius) {
        Glide.with(GlobalConfig.getApplicationContext())
                .load(url)
                .apply(new RequestOptions().bitmapTransform(new RoundedCorners(radius)))
                .into(imageView);
    }


    public static void loadLocalImage(ImageView imageView, Uri uri) {
        File file = new File(uri.getEncodedPath());
        if (!file.exists()) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
    }

    public static void loadLocalImage(ImageView imageView, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
    }

    public static void loadImageFile(ImageView imageView, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        } else {
            Log.e(TAG, file.getAbsolutePath() + "-----");
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
        /*Glide.with(GlobalConfig.getApplicationContext())
                .load(file)
                .asBitmap()
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .into(imageView);*/
    }

    public static void loadImageFile(ImageView imageView, Uri uri) {
        File file = new File(uri.getEncodedPath());
        if (!file.exists()) {
            return;
        } else {
            Log.e(TAG, file.getAbsolutePath() + "-----");
        }
        Glide.with(GlobalConfig.getApplicationContext())
                .asBitmap()
                .load(file)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public static void loadImageBlur(ImageView imageView, String url,
                                     @DrawableRes int resId) {
        Glide.with(GlobalConfig.getApplicationContext())
                .load(url)
                .placeholder(resId)
                .centerCrop()
                .transform(new BlurTransformation(GlobalConfig.getApplicationContext(),
                        20, 2))
                .into(imageView);

    }
}