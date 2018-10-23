package com.android.similarwx.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.android.similarwx.misdk.ScreenUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hanhuailong on 2018/8/2.
 */

public class BitmapUtil {
    public static String saveBitmap(Bitmap mBitmap, File file) {
        if (mBitmap == null) {
            return null;
        }
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            parentFile.mkdirs();
            try {
                file.createNewFile();
            } catch (Exception e) {
                return null;
            }

        } else {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getPath();
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap addWatermarkBitmap(Bitmap bitmap,String str,int putDestWidth,int putDestHeight) {
        int destWidth= putDestWidth;
        int destHeight=putDestHeight;
//        int destWidth = bitmap.getWidth();   //此处的bitmap已经限定好宽高
//        int destHeight = bitmap.getHeight();
        Bitmap icon = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888); //定好宽高的全彩bitmap
        Canvas canvas = new Canvas(icon);//初始化画布绘制的图像到icon上

        Paint photoPaint = new Paint(); //建立画笔
        photoPaint.setDither(true); //获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);//过滤一些

        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());//创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, destWidth, destHeight);//创建一个指定的新矩形的坐标
        canvas.drawBitmap(bitmap, src, dst, photoPaint);//将photo 缩放或则扩大到 dst使用的填充区photoPaint

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//设置画笔
        textPaint.setTextSize(destWidth/20);//字体大小
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度
        textPaint.setAntiAlias(true);  //抗锯齿
        textPaint.setStrokeWidth(3);
        textPaint.setAlpha(15);
        textPaint.setStyle(Paint.Style.FILL); //实心
        textPaint.setColor(Color.BLACK);//采用的颜色
        textPaint.setShadowLayer(1f, 0f, 3f, Color.LTGRAY);
//        textPaint.setShadowLayer(3f, 1, 1,getResources().getColor(android.R.color.white));//影音的设置
        canvas.drawText(str, destWidth/2, destHeight-45, textPaint);//绘制上去字，开始未知x,y采用那只笔绘制
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return icon; //
    }

}
