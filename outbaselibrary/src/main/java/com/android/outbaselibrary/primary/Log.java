package com.android.outbaselibrary.primary;

import com.android.outbaselibrary.BuildConfig;

/**
 * Created by Albert
 * on 16-6-2.
 */
public class Log {

    // 默认值为BuildConfig.DEBUG
    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, "--------" + msg);
    }

    public static void v(String tag, String msg) {
        android.util.Log.v(tag, "--------" + msg);
    }

    public static void d(String tag, String msg) {
        android.util.Log.d(tag, "--------" + msg);
    }

    public static void d(String tag, String format, Object... args) {
        android.util.Log.d(tag, logFormat(format, args));
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, "--------" + msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        android.util.Log.w(tag, "--------" + msg, tr);
    }

    public static void w(String tag, String format, Object... args) {
        android.util.Log.w(tag, logFormat(format, args));
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, "--------" + msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return android.util.Log.e(tag, "--------" + msg, tr);
    }

    public static void e(String tag, String format, Object... args) {
        android.util.Log.e(tag, logFormat(format, args));
    }

    private static String prettyArray(String[] array) {
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        int len = array.length - 1;
        for (int i = 0; i < len; i++) {
            sb.append(array[i]);
            sb.append(", ");
        }
        sb.append(array[len]);
        sb.append("]");
        return sb.toString();
    }

    private static String logFormat(String format, Object... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String[]) {
                args[i] = prettyArray((String[]) args[i]);
            }
        }
        String s = String.format(format, args);
        s = "[" + Thread.currentThread().getId() + "] " + s;
        return s;
    }

}
