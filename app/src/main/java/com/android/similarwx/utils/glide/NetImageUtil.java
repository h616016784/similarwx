package com.android.similarwx.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.inteface.YCallBack;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by hanhuailong on 2018/7/30.
 */

public class NetImageUtil {
    public static void glideImageNormal(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .dontAnimate()
                .priority(Priority.HIGH);
//                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    public static void glideToBitmap(Context context, String url, YCallBack<Bitmap> callBack){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).asBitmap().load(url).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                callBack.callBack(resource);
            }
        });
    }
    public static void glideToBitmapWithSize(Context context, String url, YCallBack<Bitmap> callBack,int width,int height){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .override(width,height)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).asBitmap().load(url).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                callBack.callBack(resource);
            }
        });
    }
    public static void glideImageNormalListener(Context context, String url, ImageView imageView, RequestListener requestListener){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .listener(requestListener)
                .apply(options)
                .into(imageView);
    }
    public static void glideImageNormalListenerWithSize(Context context, String url, ImageView imageView, int width,int height,RequestListener requestListener){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .override(width,height)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .listener(requestListener)
                .apply(options)
                .into(imageView);
    }
    public static void glideImageNormalWithSize(Context context, String url, ImageView imageView,int width,int height){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .override(width,height)
                .priority(Priority.HIGH);
//                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    public static void glideImageCircle(Context context, String url, ImageView imageView){
        glideImageCircle(context,url,imageView,120,120);
    }
    public static void glideImageCircle(Context context, String url, ImageView imageView,int width,int height){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .transform(new CircleCrop(context))
                .override(width,height)
                .priority(Priority.HIGH);
//                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    public static void glideImageCorner(Context context, String url, ImageView imageView){
        glideImageCorner(context,url,imageView,120,120);
    }
    public static void glideImageCorner(Context context, String url, ImageView imageView,int width,int height){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.em_default_avatar)
                .error(R.drawable.nim_default_img_failed)
                .transform(new GlideRoundTransform(context))
                .override(width,height)
                .priority(Priority.HIGH);
//                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
