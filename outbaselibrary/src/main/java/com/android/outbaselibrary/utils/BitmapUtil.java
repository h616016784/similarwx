package com.android.outbaselibrary.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class BitmapUtil {

    /**
     * Resize bitmap
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);

        return resizedBitmap;
    }

    public static Bitmap fetchImage(Uri uri) {

        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = AppContext.getContext().getContentResolver().openInputStream(uri);
            // 得到屏幕的宽和高

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(is, null, opts);
            int bitmapWidth = opts.outWidth;
            int windowWidth = 640;
            // 比较原图大小和屏幕宽高，大于屏幕的图片，将缩小显示到屏幕宽度
            if (bitmapWidth > windowWidth) {
                int scaleX = bitmapWidth / windowWidth;
                opts.inSampleSize = scaleX;
            } else {
                opts.inSampleSize = 1;
            }
            opts.inJustDecodeBounds = false;
            is = AppContext.getContext().getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is, null, opts);

            final Matrix matrix = new Matrix();
            String fileName = FileUtil.getImagePath(uri);
            if (TextUtils.isEmpty(fileName)) {
                return bitmap;
            }
            ExifInterface exif = new ExifInterface(FileUtil.getImagePath(uri));
            final int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            int rotate = -1;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            if (rotate != -1) {
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                        matrix, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeSilently(is);
        }

        return bitmap;
    }

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

    public static ByteArrayInputStream compressedInputStream(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static byte[] resToByteArray(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(AppContext.getResources(), resId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, stream);
        bitmap.recycle();
        bitmap = null;
        return stream.toByteArray();
    }

    public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 保持长宽比缩小Bitmap
     *
     * @param bitmap
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        // no need to resize
        if (originWidth < maxWidth && originHeight < maxHeight) {
            return bitmap;
        }

        int width = originWidth;
        int height = originHeight;

        // 若图片过宽, 则保持长宽比缩放图片
        if (originWidth > maxWidth) {
            width = maxWidth;

            double i = originWidth * 1.0 / maxWidth;
            height = (int) Math.floor(originHeight / i);

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }

        // 若图片过长, 则从上端截取
        if (height > maxHeight) {
            height = maxHeight;
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        }

        return bitmap;
    }

    public static Bitmap blur(Bitmap bitmap, float radiusF) {

        int radius = (int) radiusF;

        if (radius < 1) {
            return null;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int temp = 256 * divsum;
        int dv[] = new int[temp];
        for (i = 0; i < temp; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return bitmap;
    }

    public static Bitmap fullBlur(Bitmap sentBitmap, int width, int height) {

        Bitmap bitmap = null;

        if (width == sentBitmap.getWidth() && height == sentBitmap.getHeight()) {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        } else {
            bitmap = Bitmap.createBitmap(sentBitmap, (sentBitmap.getWidth() - width) / 2, 0, width,
                    height);
        }

        return blur(bitmap, 80F);
    }

    public static Bitmap fullBlur(Bitmap sentBitmap, int width, int height, float radiusF) {

        Bitmap bitmap = null;

        if (width == sentBitmap.getWidth() && height == sentBitmap.getHeight()) {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        } else {
            bitmap = Bitmap.createBitmap(sentBitmap, (sentBitmap.getWidth() - width) / 2, 0, width,
                    height);
        }

        return blur(bitmap, radiusF);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Bitmap betterBlur(Bitmap bitmap) {

        if (Build.VERSION.SDK_INT > 16) {
            bitmap = bitmap.copy(bitmap.getConfig(), true);
            final RenderScript rs = RenderScript.create(AppContext.getContext());
            final Allocation input = Allocation.createFromBitmap(rs, bitmap,
                    Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(14 /* e.g. 3.f */);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);
            return bitmap;
        }

        return null;
    }

}
