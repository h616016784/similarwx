package com.android.similarwx.present;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.MyBaseViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;

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
                mView.reFreshUser(user);
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

    }
}
