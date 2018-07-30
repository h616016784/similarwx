package com.android.similarwx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.AccToken;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.UserInfoWX;
import com.android.similarwx.inteface.WxViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.API;
import com.android.similarwx.model.WXAPI;
import com.android.similarwx.present.WxPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
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

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, WxViewInterface {

    private IWXAPI api;
    private WxPresent present;
    private UserInfoWX userInfoWXTemp;

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
        present=new WxPresent(this);
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
                                        userInfoWXTemp=userInfoWX;
                                        String unionId=userInfoWX.getUnionid();
                                        doServiceLogin(unionId);
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

    /**
     * 根据微信的id来进行登录操作
     * @param unionId
     */
    private void doServiceLogin(String unionId) {
        present.WxLogin(unionId);
    }

    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user){
        if (user!=null){
            SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(), AppConstants.USER_OBJECT,user);
        }
        if (user.getAccId()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_ACCID,user.getAccId());
        if (user.getToken()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,user.getToken());
        else
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,"a170417844a19c6bfebb4ab1a137fc31");
        if (user.getName()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_NICK,user.getName());
        if (user.getEmail()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_EMAIL,user.getEmail());
        if (user.getMobile()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PHONE,user.getMobile());
        if (user.getWechatAccount()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_WEIXIN,user.getWechatAccount());
        if (user.getGender()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_SEX,user.getGender());
        if (user.getId()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_ID,user.getId());
        if (user.getPaymentPasswd()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PAYPASSWORD,user.getPaymentPasswd());
        if (user.getPasswd()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_LOGIN_PASSWORD,user.getPasswd());
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshWxLogin(User user) {
        if (user!=null){
            saveUser(user);
            MainChartrActivity.start(this);
        }
    }

    @Override
    public void refreshWxUpdate(User user) {
        if (user!=null){
            if (userInfoWXTemp!=null){
//                user.setName(userInfoWXTemp.getNickname());
//                user.setName(userInfoWXTemp.getSex()+"");
//                user.setName(userInfoWXTemp.getHeadimgurl()+"");
                API.getInstance().updateUserByWX(userInfoWXTemp.getNickname(), userInfoWXTemp.getHeadimgurl(), userInfoWXTemp.getSex() + "", new YCallBack<User>() {

                    @Override
                    public void callBack(User user) {
                        if (user!=null){
                            saveUser(user);
                            MainChartrActivity.start(WXEntryActivity.this);
                        }
                    }
                });
            }
        }
    }
}
