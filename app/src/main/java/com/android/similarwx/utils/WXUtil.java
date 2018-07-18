package com.android.similarwx.utils;


import android.content.Context;

import com.android.similarwx.base.AppConstants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by hanhuailong on 2018/7/18.
 */

public class WXUtil {
    private static WXUtil wxUtil;
    private IWXAPI api;
    private Context context;
    private WXUtil(Context context){
        this.context=context;
        api = WXAPIFactory.createWXAPI(context, AppConstants.WX_APP_ID,false);
        api.registerApp(AppConstants.WX_APP_ID);
    }
    public static WXUtil getInstance(Context context) {
        if (wxUtil == null) {
            synchronized (WXUtil.class) {
                if (wxUtil == null) {
                    wxUtil = new WXUtil(context);
                }
            }
        }
        return wxUtil;
    }
    public IWXAPI getApi(){
        return api;
    }
}
