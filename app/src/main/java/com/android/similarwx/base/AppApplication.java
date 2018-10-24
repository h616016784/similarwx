package com.android.similarwx.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.outbaselibrary.BaseApplication;
import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.LogUtil;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.config.NIMInitManager;
import com.android.similarwx.config.NimSDKOptionConfig;
import com.android.similarwx.config.UserPreferences;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.android.similarwx.service.mipush.DemoMixPushMessageHandler;
import com.google.gson.Gson;
import com.netease.nim.uikit.business.contact.core.util.ContactHelper;
import com.netease.nim.uikit.business.session.SessionHelper;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderRed;
import com.android.similarwx.inteface.message.CustomAttachParser;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.misdk.ScreenUtil;
import com.android.similarwx.misdk.StorageUtil;
import com.android.similarwx.misdk.model.RedCustomAttachParser;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.core.query.PinYin;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderRedTip;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderTrans;
import com.netease.nim.uikit.business.team.DemoCache;
import com.netease.nim.uikit.event.DemoOnlineStateContentProvider;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.NIMPushClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.IMMessageFilter;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class AppApplication extends BaseApplication {
    private static AppApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        DemoCache.setContext(this);
        StorageUtil.init(this, options().sdkStorageRootPath);
        ScreenUtil.init(this);
//        initYunXinSDK(this);
        initNIM(this);
    }
    private void initNIM(AppApplication appApplication) {
        NIMClient.init(appApplication, loginInfo(), NimSDKOptionConfig.getSDKOptions(appApplication));

        if (NIMUtil.isMainProcess(appApplication)) {
            // 在主进程中初始化UI组件，判断所属进程方法请参见demo源码。
            initUiKit();
        }
    }
    private void initUiKit() {
        // 注册自定义推送消息处理，这个是可选项
        NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());
        // init pinyin
        PinYin.init(this);
        PinYin.validate();

        // 初始化
        NimUIKit.init(this);
        // 会话窗口的定制: 示例代码可详见demo源码中的SessionHelper类。
        // 1.注册自定义消息附件解析器（可选）
        // 2.注册各种扩展消息类型的显示ViewHolder（可选）
        // 3.设置会话中点击事件响应处理（一般需要）
        SessionHelper.init();
        // 通讯录列表定制：示例代码可详见demo源码中的ContactHelper类。ContactHelper
        // 1.定制通讯录列表中点击事响应处理（一般需要，UIKit 提供默认实现为点击进入聊天界面)
//        ContactHelper.init();
        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());

        // 初始化消息提醒
//        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        NIMClient.toggleNotification(true);
        // 云信sdk相关业务初始化
        NIMInitManager.getInstance().init(true);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 将MultiDex注入到项目中
        MultiDex.install(this);
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
            accid=(String) SharePreferenceUtil.getObject(this,AppConstants.USER_ACCID,null);
            token=(String) SharePreferenceUtil.getObject(this,AppConstants.USER_TOKEN,null);

        }catch (Exception e){
            return null;
        }

        if (!TextUtils.isEmpty(accid)&&!TextUtils.isEmpty(token)){
            LoginInfo info=new LoginInfo(accid,token);
            DemoCache.setAccount(accid.toLowerCase());
            return  info;
        }
        return null;
    }
    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        return SDKOptions.DEFAULT;
    }
}
