package com.android.similarwx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.AccToken;
import com.android.similarwx.beans.UserInfoWX;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.WXAPI;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by hanhuailong on 2018/7/18.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api =  WXAPIFactory.createWXAPI(this, AppConstants.WX_APP_ID,false);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        int result = 0;
    }

    @Override
    public void onResp(BaseResp baseResp) {
        int result = 0;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK://成功
                result = R.string.errcode_success;
                if (baseResp.getType()==ConstantsAPI.COMMAND_SENDAUTH){
                    SendAuth.Resp resp= (SendAuth.Resp) baseResp;
                    //获取acctoken
                    WXAPI.getInstance().accessToken(AppConstants.WX_APP_ID, AppConstants.WX_APP_SECRET, resp.code, "authorization_code", new YCallBack<AccToken>() {
                        @Override
                        public void callBack(AccToken accToken) {
                            if (accToken!=null){
                                WXAPI.getInstance().userInfoWX(accToken.getAccess_token(), accToken.getOpenid(), new YCallBack<UserInfoWX>() {
                                    @Override
                                    public void callBack(UserInfoWX userInfoWX) {
                                        userInfoWX.getOpenid();
                                        userInfoWX.getNickname();
                                    }
                                });
                            }
                        }
                    });
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        finish();
    }
}
