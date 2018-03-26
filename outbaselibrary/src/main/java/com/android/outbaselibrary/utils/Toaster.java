package com.android.outbaselibrary.utils;

import android.widget.Toast;

import com.android.outbaselibrary.primary.AppContext;

/**
 * Created by Albert
 * on 16-6-2.
 */
public class Toaster {

    public static void toastShort(int resId) {
        toast(AppContext.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void toastShort(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    public static void toastLong(int resId) {
        toast(AppContext.getString(resId), Toast.LENGTH_LONG);
    }

    public static void toastLong(String msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    public static void toast(int resId, int duration) {
        toast(AppContext.getString(resId), duration);
    }

    public static void toast(CharSequence charSequence, int duration) {
        Toast.makeText(AppContext.getContext(), charSequence, duration).show();
    }

    public static void toast(String format, Object[] args, int duration) {
        toast(String.format(format, args), duration);
    }
}
