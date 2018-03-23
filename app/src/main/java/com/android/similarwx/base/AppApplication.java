package com.android.similarwx.base;

import com.android.outbaselibrary.BaseApplication;
import com.android.outbaselibrary.utils.LogUtil;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class AppApplication extends BaseApplication {
    private static AppApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.d("onTerminate");
    }
}
