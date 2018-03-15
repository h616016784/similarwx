package com.android.outbaselibrary.utils;

import com.android.outbaselibrary.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class LogUtil {
    //一般的打印，dubug模式下打印数据，不是debug模式下就不打印
    public static void d(String msg,Object object){
        if(BuildConfig.DEBUG){
            Logger.d(msg,object);
        }
    }
    public static void d(Object object){
        if(BuildConfig.DEBUG){
            Logger.d(object);
        }
    }
    public static void e(String msg,Object object){
        if(BuildConfig.DEBUG){
            Logger.e(msg,object);
        }
    }

    //对于异常错误的打印，如果是debug模式就打印，不是就保存错误信息到本地
    public static void e(Throwable throwable,String msg,Object object){
        Logger.e(throwable,msg,object);
    }
}
