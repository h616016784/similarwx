package com.android.outbaselibrary.utils;

import android.os.Build;

/**
 * Created by puyafeng on 2017/3/10.
 * 获取与系统有关的信息
 */

public class SystemUtil {
    public static final int IO_BUFFER_SIZE = 8 * 1024;
    //系统是否root过
    public static boolean isRoot(){
        return false;
    }

    //

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }
    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= 19/*Build.VERSION_CODES.KITKAT*/;
    }
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }
}
