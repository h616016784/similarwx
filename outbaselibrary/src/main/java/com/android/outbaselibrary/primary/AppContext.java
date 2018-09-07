package com.android.outbaselibrary.primary;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import com.android.outbaselibrary.utils.ViewUtil;

import java.io.File;
import java.util.Stack;

/**
 * Created by Albert
 * on 16-6-2.
 */
public class AppContext {

    private static Context sContext;
    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private static Stack<AppCompatActivity> activitiesStack=new Stack<>();
    public static AppCompatActivity getsActivity() {
        return sActivity;
    }

    public static void setsActivity(AppCompatActivity sActivity) {
        AppContext.sActivity = sActivity;
    }
    public static Stack<AppCompatActivity> getActivitiesStack () {
        return activitiesStack;
    }
    private static AppCompatActivity sActivity;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context appContext) {
        sContext = appContext;
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int id) {
        return getResources().getString(id);
    }

    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    public static String getQuantityString(int id, int quantity, Object... formatArgs) {
        return getResources().getQuantityString(id, quantity, formatArgs);
    }

    public static int getColor(int id) {
        return getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(int id) {
        return getResources().getColorStateList(id);
    }

    public static int getInteger(int id) {
        return getResources().getInteger(id);
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    public static File getCacheDir() {
        return getContext().getCacheDir();
    }

    public static int getScreenWidth() {
        if (screenWidth <= 0) {
            screenWidth = ViewUtil.getScreenWidthPixels();
        }
        return screenWidth;
    }

    public static void setScreenWidth(int width) {
        screenWidth = width;
    }

    public static int getScreenHeight() {
        if (screenHeight <= 0) {
            screenHeight = ViewUtil.getScreenHeightPixels();
        }
        return screenHeight;
    }

    public static void setScreenHeight(int height) {
        screenHeight = height;
    }
}
