package com.android.outbaselibrary;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;


/**
 * Created by hanhuailong on 2018/3/14.
 */

public class BaseApplication extends Application {

    //API 14以上有效
    private boolean isAppBackground = false;//标记app是否后台或者是否熄屏了

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志
        initLog();
        //初始化位捕获异常
        CrashHandler.getInstance().init(this);

    }


    private void initLog() {
        if(BuildConfig.DEBUG){
            Logger.addLogAdapter(new AndroidLogAdapter());
        }else{
            FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                    .tag("ErrorLogFile")
                .build();
            Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
        }
    }

    /**
     * 当终止应用程序对象时调用，不保证一定被调用，当程序被内核终止以便为其他应用程序释放资源，那么将不会提醒，
     * 并且不调用应用程序的对象的onTerminate方法而直接终止进程
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 清理内存时回调
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 当后台程序已经终止资源还匮乏时会调用这个方法。
     * 好的应用程序一般会在这个方法里面释放一些不必要的资源来应付当后台程序已经终止，前台应用程序内存还不够时的情况。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public boolean isBackground() {
        return isAppBackground;
    }

    public void setBackground(boolean isAppBackground) {
        this.isAppBackground = isAppBackground;
    }
}
