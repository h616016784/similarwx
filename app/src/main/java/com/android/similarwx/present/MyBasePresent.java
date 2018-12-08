package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.MyBaseViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nim.uikit.business.team.DemoCache;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanhuailong on 2018/6/14.
 */

public class MyBasePresent extends BasePresent {
    private MyBaseViewInterface mView;
    AppCompatActivity activity;
    public MyBasePresent(MyBaseViewInterface view,AppCompatActivity activity){
        this.mView=view;
        this.activity=activity;
    }
    public void updateUserByNick(String nick){
        String id= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().updateUserByNick(activity,id,nick,this);
    }
    public void updateUserBySign(String personalitySignature){
        String id= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().updateUserBySign(activity,id,personalitySignature,this);
    }
    public void updateUserByGender(String gender){
        String id= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().updateUserByGender(activity,id,gender,this);
    }
    public void updateUserByUrl(String url){
        String id= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().updateUserByUrl(activity,id,url,this);
    }
    public void updateUserByPhone(String mobile){
        String id= SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ID,"无");
        API.getInstance().updateUserByPhone(activity,id,mobile,this);
    }
    public void analyzeRes(RspUser rspUser) {
        String result=rspUser.getResult();
        if (result.equals("success")){
            User user=rspUser.getData();
            if (user!=null){
                saveUser(user);

            }
            else
                Toaster.toastShort("数据解析异常");
        }else {
            Toaster.toastShort(rspUser.getErrorMsg());
        }
    }
    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user) {
        if (user!=null){
            SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(),AppConstants.USER_OBJECT,user);
        }
        if (user.getAccId()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(), AppConstants.USER_ACCID,user.getAccId());
        if (user.getToken()!=null)
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,user.getToken());
        else
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_TOKEN,"");
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

        //跟新本地用户资料
        doUpdateLocalYunxin(user);
    }

    private void doUpdateLocalYunxin(User user) {
        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
        if (!TextUtils.isEmpty(user.getName()))
            fields.put(UserInfoFieldEnum.Name, user.getName());//昵称
        if (!TextUtils.isEmpty(user.getIcon()))
            fields.put(UserInfoFieldEnum.AVATAR, user.getIcon());//头像
        if (!TextUtils.isEmpty(user.getPersonalitySignature()))
            fields.put(UserInfoFieldEnum.SIGNATURE, user.getPersonalitySignature());//签名
        if (!TextUtils.isEmpty(user.getGender()))
            fields.put(UserInfoFieldEnum.GENDER, Integer.parseInt(user.getGender()));//性别
        if (!TextUtils.isEmpty(user.getEmail()))
            fields.put(UserInfoFieldEnum.EMAIL, user.getEmail());//电子邮箱

        if (!TextUtils.isEmpty(user.getBirth()))
            fields.put(UserInfoFieldEnum.BIRTHDAY, user.getBirth());//生日
        else
            fields.put(UserInfoFieldEnum.BIRTHDAY, "1988-10-01");//生日
        if (!TextUtils.isEmpty(user.getMobile()))
            fields.put(UserInfoFieldEnum.MOBILE, user.getMobile());//手机

        NIMClient.getService(UserService.class).updateUserInfo(fields).setCallback(new RequestCallbackWrapper<Void>() {
            @Override
            public void onResult(int code, Void result, Throwable exception) {

                if (code == ResponseCode.RES_SUCCESS) {
                    LogUtil.i("basePresent", "update userInfo success, update fields count=" + fields.size());
                    mView.reFreshUser(user);
                } else {
                    if (exception != null) {
                        Toast.makeText(DemoCache.getContext(), R.string.user_info_update_failed, Toast.LENGTH_SHORT).show();
                        LogUtil.i("basePresent", "update userInfo failed, exception=" + exception.getMessage());
                    }
                }
                DemoCache.setAccount(user.getAccId());
            }
        });
    }
}
