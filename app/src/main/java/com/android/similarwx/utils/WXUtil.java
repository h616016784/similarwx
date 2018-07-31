package com.android.similarwx.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.similarwx.base.AppConstants;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by hanhuailong on 2018/7/18.
 */

public class WXUtil {
    private static final int THUMB_SIZE = 150;

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

    /**
     * 发送文本分享
     * @param text
     * @param mTargetScene    发送到聊天界面——WXSceneSession       发送到朋友圈——WXSceneTimeline
     */
    public void WxShareText(String text,int mTargetScene){
        WXTextObject textObject=new WXTextObject();
        textObject.text=text;

        WXMediaMessage msg=new WXMediaMessage();
        msg.mediaObject=textObject;
        msg.description=text;

        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=buildTransaction(text);
        req.message = msg;
        req.scene = mTargetScene;

        api.sendReq(req);
    }

    public void WxShareImage(String text,Bitmap bmp,int mTargetScene){
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = mTargetScene;
        api.sendReq(req);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
