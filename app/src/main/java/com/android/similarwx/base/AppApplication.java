package com.android.similarwx.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.outbaselibrary.BaseApplication;
import com.android.outbaselibrary.utils.LogUtil;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class AppApplication extends BaseApplication {
    private static AppApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        initYunXinSDK();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 将MultiDex注入到项目中
        MultiDex.install(this);
    }

    private void initYunXinSDK() {
        NIMClient.init(this,loginInfo(),options());
        // ... your codes
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.d("onTerminate");
    }
    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        String accid;
        String token;
        try {
            accid=(String) SharePreferenceUtil.getObject(this,AppConstants.USER_ACCID,"无");
            token=(String) SharePreferenceUtil.getObject(this,AppConstants.USER_TOKEN,"无");
        }catch (Exception e){
            return null;
        }

        if (!TextUtils.isEmpty(accid)&&!TextUtils.isEmpty(token))
            return  new LoginInfo(accid,token);
        return null;
    }
    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        return null;
    }
}
