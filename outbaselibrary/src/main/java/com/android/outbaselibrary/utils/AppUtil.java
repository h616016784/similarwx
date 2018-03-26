package com.android.outbaselibrary.utils;

import android.content.pm.PackageInfo;
import android.os.Build;

import java.util.List;

/**
 * Created by hanhuailong on 2017/3/10.
 * 安装，卸载，版本信息，大小，名称等与App有关的操作
 *
 */

public class AppUtil {
    //是否是系统app
    public static boolean isSystemApp(String packageName){
        return false;
    }

    //获取所有已经安装的app
    public static List<PackageInfo> getInstallApps(){
        return null;
    }

    //判断是否安装某App
    public static void isInstall(String packageName){

    }
    //安装App
    public static void install(String packageName){

    }
    //卸载App
    public static void unInstall(String packageName){

    }
    //启动App
    public static void lauch(String packageName){

    }
    //退出App
    public static void exit(String packageName){

    }
    //

}
