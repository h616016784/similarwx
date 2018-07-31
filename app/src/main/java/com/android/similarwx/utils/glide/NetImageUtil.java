package com.android.similarwx.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by hanhuailong on 2018/7/30.
 */

public class NetImageUtil {
    public static void glideImageNormal(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.nim_default_img_failed)
                .error(R.drawable.nim_default_img_failed)
                .priority(Priority.HIGH);
//                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    public static void glideImageNormalWithSize(Context context, String url, ImageView imageView,int width,int height){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.nim_default_img_failed)
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
                .placeholder(R.drawable.nim_default_img_failed)
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
}
