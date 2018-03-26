package com.android.outbaselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtil {

    private static final String TAG = "ViewUtils";

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Align the first gallery item to the left.
     *
     * @param parentView The view containing the gallery widget (we assume
     *                   the gallery width is set to match_parent)
     * @param gallery    The gallery we have to change
     */
    public static void alignGalleryToLeft(View parentView, Gallery gallery, int galleryItemWidth,
                                          int gallerySpacing) {
        int galleryWidth = parentView.getWidth();

        // The offset is how much we will pull the gallery to the left in order to simulate
        // left alignment of the first item
        int offset = 0;
        if (galleryWidth <= galleryItemWidth) {
            offset = galleryWidth / 2 - galleryItemWidth / 2 - gallerySpacing;
        } else {
            offset = galleryWidth - galleryItemWidth - 2 * gallerySpacing;
        }

        // Now update the layout parameters of the gallery in order to set the left margin
        MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
        mlp.setMargins(-offset, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
    }

    /**
     * 手机信息类型：1屏幕宽度
     */
    public static final int TYPE_SCREEN_WIDTH = 1;

    /**
     * 手机信息类型：2屏幕高度
     */
    public static final int TYPE_SCREEN_HEIGHT = 2;

    public static float dpToPx(float dpValue) {
        DisplayMetrics metrics = AppContext.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, metrics);
    }

    public static float spToPx(float spValue) {
        DisplayMetrics metrics = AppContext.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics);
    }

    public static int px2dip(float pxValue) {
        final float scale = AppContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenDensity() {
        return AppContext.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getDimenPx(int ResId) {
        return AppContext.getResources().getDimensionPixelSize(ResId);
    }

    public static int getScreenHeightPixels() {
        return AppContext.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidthPixels() {
        WindowManager wm = (WindowManager) AppContext.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
//        return AppContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static boolean isHdpi() {
        if (getScreenDensity() == 240) {
            return true;
        }

        return false;
}

    public static boolean isLdpi() {
        if (getScreenDensity() == 120) {
            return true;
        }

        return false;
    }

    public static boolean isMdpi() {
        if (getScreenDensity() == 160) {
            return true;
        }

        return false;
    }

    public static boolean isXhdpi() {
        if (getScreenDensity() == 320) {
            return true;
        }

        return false;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition 
            return;
        }

        int totalHeight = 0;
        int screenHeight = getScreenHeightPixels();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight() + screenHeight;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 判断点是否落在view内
     */
    public static boolean isPointInView(int rawX, int rawY, View view) {
        if (view == null) {
            return false;
        }

        int coordinates[] = {0, 0};
        view.getLocationOnScreen(coordinates);
        int left = coordinates[0];
        int right = left + view.getWidth();
        int top = coordinates[1];
        int bottom = top + view.getHeight();
        if (rawX >= left && rawX <= right && rawY >= top && rawY <= bottom) {
            return true;
        } else {
            return false;
        }
    }

    public static Bitmap drawViewToCanvas(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static int getTitleBarHeight(Activity activity) {
        if (activity == null) {
            return 0;
        } else {
            int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
            int titleBarHeight = contentTop - getStatusBarHeight(activity);
            return titleBarHeight;
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        if (activity == null) {
            return 0;
        } else {
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            return statusBarHeight;
        }
    }

    public static int getStatusHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getDimenPx(x);
        } catch (Exception e) {
            Log.e(TAG, "get status bar height fail");
            e.printStackTrace();
        }

        return sbar;
    }


    public static Bitmap adjustImageRotation(Bitmap bm, int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
