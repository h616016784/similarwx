package com.android.similarwx.model;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspAccountDetail;
import com.android.similarwx.beans.response.RspAddGroupUser;
import com.android.similarwx.beans.response.RspBill;
import com.android.similarwx.beans.response.RspCanGrab;
import com.android.similarwx.beans.response.RspCashUser;
import com.android.similarwx.beans.response.RspConfig;
import com.android.similarwx.beans.response.RspDeleteGroup;
import com.android.similarwx.beans.response.RspDeleteGroupUser;
import com.android.similarwx.beans.response.RspGetApply;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspGroupApply;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.beans.response.RspGroupSave;
import com.android.similarwx.beans.response.RspGroupUser;
import com.android.similarwx.beans.response.RspInMoney;
import com.android.similarwx.beans.response.RspMoney;
import com.android.similarwx.beans.response.RspNotice;
import com.android.similarwx.beans.response.RspRedDetail;
import com.android.similarwx.beans.response.RspSendRed;
import com.android.similarwx.beans.response.RspService;
import com.android.similarwx.beans.response.RspSetPassword;
import com.android.similarwx.beans.response.RspSubUsers;
import com.android.similarwx.beans.response.RspTransfer;
import com.android.similarwx.beans.response.RspUpdateGroupUser;
import com.android.similarwx.beans.response.RspUpdateUserStatus;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.beans.response.VerifyCodeResponse;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.interceptor.LogInterceptor;
import com.android.similarwx.present.AcountPresent;
import com.android.similarwx.present.AddGroupPresent;
import com.android.similarwx.present.CashPresent;
import com.android.similarwx.present.ClientDetailInfoPresent;
import com.android.similarwx.present.GroupInfoPresent;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.present.InputMoneyPresent;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.present.MyBasePresent;
import com.android.similarwx.present.NoticePresent;
import com.android.similarwx.present.PhoneVerifyPresent;
import com.android.similarwx.present.RechargePresent;
import com.android.similarwx.present.RedDetailPresent;
import com.android.similarwx.present.RegisterPresent;
import com.android.similarwx.present.SearchPresent;
import com.android.similarwx.present.SendRedPresent;
import com.android.similarwx.present.ServicePresent;
import com.android.similarwx.present.SetPasswordPresent;
import com.android.similarwx.present.SubUsersPresent;
import com.android.similarwx.present.SysNoticePresent;
import com.android.similarwx.present.WxPresent;
import com.android.similarwx.utils.Strings.StringUtil;
import com.android.similarwx.widget.dialog.LoadingDialogN;
import com.netease.nim.uikit.business.session.activity.BaseMessageActivity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by puyafeng on 2017/9/12.
 */
public class API implements APIConstants {
    private static API sInstance;
    public static APIService apiService;
    boolean isCancle=true;
    private API() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(DOMAIN)
                .build();
        apiService = retrofit.create(APIService.class);

    }

    public static API getInstance() {
        if (sInstance == null) {
            synchronized (API.class) {
                if (sInstance == null) {
                    sInstance = new API();
                }
            }
        }
        return sInstance;
    }

    public void noNetTips(AppCompatActivity activity){
        if (activity instanceof BaseActivity){
            ((BaseActivity)activity).getmHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                    Toaster.toastShort("网络异常!");
                }
            },1000);
        }else {
            ((BaseMessageActivity)activity).getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                    Toaster.toastShort("网络异常!");
                }
            },1000);
        }

    }
    /**
     * 注册接口
     * @param account
     * @param weixinAccount
     * @param email
     * @param name
     * @param password
     * @param nick
     * @param present
     */
    public void register(AppCompatActivity activity,String account, String weixinAccount, String email, String name, String password, String nick,
                         String birth,String gender,String alipay,String personalitySignature,String verifyCode,final RegisterPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(account))
            map.put("accId",account);
        if (!TextUtils.isEmpty(password))
            map.put("passwdStr",password);
        if (!TextUtils.isEmpty(nick))
            map.put("name",nick);
        if (!TextUtils.isEmpty(email))
            map.put("email",email);
        if (!TextUtils.isEmpty(name)){
            map.put("accId",name);
            map.put("mobile",name);
        }

        if (!TextUtils.isEmpty(weixinAccount))
            map.put("wechatAccount",weixinAccount);
        if (!TextUtils.isEmpty(birth))
            map.put("birth",birth);
        if (!TextUtils.isEmpty(gender))
            map.put("gender",gender);
        if (!TextUtils.isEmpty(alipay))
            map.put("alipay",alipay);
        if (!TextUtils.isEmpty(personalitySignature))
            map.put("personalitySignature",personalitySignature);
        if (!TextUtils.isEmpty(verifyCode))
            map.put("verifyCode",verifyCode);

        Call<RspUser> user=apiService.registe(map);
        user.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                RspUser rspUser=response.body();
                present.analyzeRes(rspUser);
            }
            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void login(AppCompatActivity activity,String name, String password, String weixin, String mobile, final LoginPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(name))
            map.put("accId",name);
        if (!TextUtils.isEmpty(mobile))
            map.put("mobile",mobile);
        if (!TextUtils.isEmpty(weixin))
            map.put("wechatAccount",weixin);
        if (!TextUtils.isEmpty(password))
            map.put("passwdStr",password);
        Call<RspUser> user=apiService.login(map);
        user.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }


    public void WxLogin(AppCompatActivity activity,String unionid, WxPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("unionid",unionid);
        Call<RspUser> call=apiService.WxLogin(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void logout(AppCompatActivity activity,String userId,LoginPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        Call<RspUser> call=apiService.logout(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeResLogout(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getTotalBalance(AppCompatActivity activity,String userId,LoginPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("id",userId);
        Call<RspUser> call=apiService.getTotalBalance(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeTotalBalance(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void setInvitationCode(AppCompatActivity activity,String userId, String invitationCode, LoginPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("invitationCode",invitationCode);
        Call<RspUser> call=apiService.setInvitationCode(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeInvitationCode(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void updateUserByNick(AppCompatActivity activity,String id, String nick, MyBasePresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        if (!TextUtils.isEmpty(nick)){
            nick= StringUtil.replaceChar(nick);
            map.put("name",nick);
        }
        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void updateUserByPhone(AppCompatActivity activity,String id, String mobile, MyBasePresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("mobile",mobile);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void updateUserBySign(AppCompatActivity activity,String id, String personalitySignature, MyBasePresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        if (!TextUtils.isEmpty(personalitySignature)){
            personalitySignature= StringUtil.replaceChar(personalitySignature);
            map.put("personalitySignature",personalitySignature);
        }
        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void updateUserByGender(AppCompatActivity activity,String id, String gender, MyBasePresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("gender",gender);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void updateUserByUrl(AppCompatActivity activity,String id, String url, MyBasePresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("icon",url);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void updateUserByWX(AppCompatActivity activity,String id,String name, String url, String gender, YCallBack callBack){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        if (!TextUtils.isEmpty(name)){
            name= StringUtil.replaceChar(name);
            map.put("name",name);
        }
        if (!TextUtils.isEmpty(url))
            map.put("icon",url);
        if (!TextUtils.isEmpty(gender))
            map.put("gender",gender);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();

                    String result=rspUser.getResult();
                    if (result.equals("success")){
                        User user=rspUser.getData();
                        if (user!=null){
                            callBack.callBack(user);
                        }
                        else
                            Toaster.toastShort("数据解析异常");
                    }else {
                        Toaster.toastShort(rspUser.getErrorMsg());
                    }

                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getUserInfoByParams(AppCompatActivity activity,String userId, String accId, ClientDetailInfoPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(userId))
            map.put("userId",userId);
        if (!TextUtils.isEmpty(accId))
            map.put("accId",accId);

        Call<RspUser> call=apiService.getUserInfoByParams(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void setPaymentPasswd(AppCompatActivity activity,String mobile, String userId,String paymentPasswd, String passwdStr,String verifyCode, SetPasswordPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(userId))
            map.put("userId",userId);
        if (!TextUtils.isEmpty(mobile))
            map.put("mobile",mobile);
        if (!TextUtils.isEmpty(paymentPasswd))
            map.put("paymentPasswd",paymentPasswd);
        if (!TextUtils.isEmpty(passwdStr))
            map.put("passwdStr",passwdStr);
        if (!TextUtils.isEmpty(verifyCode))
            map.put("verifyCode",verifyCode);
        Call<RspSetPassword> call=apiService.setPaymentPasswd(map);
        call.enqueue(new Callback<RspSetPassword>() {
            @Override
            public void onResponse(Call<RspSetPassword> call, Response<RspSetPassword> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspSetPassword rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspSetPassword> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getMobileVerifyCode(AppCompatActivity activity,String mobile, PhoneVerifyPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        Call<VerifyCodeResponse> call=apiService.getMobileVerifyCode(map);
        call.enqueue(new Callback<VerifyCodeResponse>() {
            @Override
            public void onResponse(Call<VerifyCodeResponse> call, Response<VerifyCodeResponse> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    VerifyCodeResponse rspGroup=response.body();
                    present.analyzeRes(rspGroup);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<VerifyCodeResponse> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getCashUser(AppCompatActivity activity,CashPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        Call<RspCashUser> call=apiService.getCashUser(map);
        call.enqueue(new Callback<RspCashUser>() {
            @Override
            public void onResponse(Call<RspCashUser> call, Response<RspCashUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspCashUser rspGroup=response.body();
//                    present.analyzeRes(rspGroup);
                    present.analyzeRes(rspGroup);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspCashUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void reqGroupList(AppCompatActivity activity,String userId , final GroupPresent present){
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        Call<RspGroup> rspGroupCall=apiService.reqGroupList(map);
        rspGroupCall.enqueue(new Callback<RspGroup>() {
            @Override
            public void onResponse(Call<RspGroup> call, Response<RspGroup> response) {
                try {
                    RspGroup rspGroup=response.body();
                    present.analyzeRes(rspGroup);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroup> call, Throwable t) {
                present.doError("网络异常!");
//                noNetTips();
            }
        });
    }
    //申请入群
    public void doGroupAppley(AppCompatActivity activity,String groupId, String userId, final GroupPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        map.put("applyUserId",userId);
        map.put("applyInfo","Android App申请");
        Call<RspGroupApply> call=apiService.doGroupAppley(map);
        call.enqueue(new Callback<RspGroupApply>() {
            @Override
            public void onResponse(Call<RspGroupApply> call, Response<RspGroupApply> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspGroupApply rspGroupApply=response.body();
                    present.analyzeApplyRes(rspGroupApply);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupApply> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    //添加群组成员
    public void doAddGroupUser(AppCompatActivity activity,String grouId,String userId,NoticePresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",grouId);
        map.put("userId",userId);
        Call<RspAddGroupUser> call=apiService.doAddGroupUser(map);
        call.enqueue(new Callback<RspAddGroupUser>() {
            @Override
            public void onResponse(Call<RspAddGroupUser> call, Response<RspAddGroupUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspAddGroupUser rspGroupUser=response.body();
                    present.analyzeRes(rspGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspAddGroupUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    //解散群组
    public void doDeleteGroup(AppCompatActivity activity,String grouId , GroupInfoPresent groupInfoPresent){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",grouId);
        Call<RspDeleteGroup> call=apiService.doDeleteGroup(map);
        call.enqueue(new Callback<RspDeleteGroup>() {
            @Override
            public void onResponse(Call<RspDeleteGroup> call, Response<RspDeleteGroup> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspDeleteGroup rspDeleteGroup=response.body();
                    groupInfoPresent.analyzeDeleteGroup(rspDeleteGroup);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspDeleteGroup> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    //踢出群组或退出群组
    public void doDeleteGroupUser(AppCompatActivity activity,String grouId,String userId,ClientDetailInfoPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",grouId);
        map.put("userId",userId);
        Call<RspDeleteGroupUser> call=apiService.doDeleteGroupUser(map);
        call.enqueue(new Callback<RspDeleteGroupUser>() {
            @Override
            public void onResponse(Call<RspDeleteGroupUser> call, Response<RspDeleteGroupUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspDeleteGroupUser rspDeleteGroupUser=response.body();
                    present.analyzeDeleteGroupUser(rspDeleteGroupUser);

                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspDeleteGroupUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    //任命或者取消任命管理员
    public void doUpdateGroupUser(AppCompatActivity activity,String grouId,String userId,String groupUserRule,ClientDetailInfoPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",grouId);
        map.put("userId",userId);
        map.put("groupUserRule",groupUserRule);
        Call<RspUpdateGroupUser> call=apiService.doUpdateGroupUser(map);
        call.enqueue(new Callback<RspUpdateGroupUser>() {
            @Override
            public void onResponse(Call<RspUpdateGroupUser> call, Response<RspUpdateGroupUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUpdateGroupUser rspUpdateGroupUser=response.body();
                    present.analyzeUpdateGroupUser(rspUpdateGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUpdateGroupUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }


    //禁言/解禁群组用户
    public void doUpdateGroupUserStatus(AppCompatActivity activity,String grouId,String userId,String userStatus,ClientDetailInfoPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",grouId);
        map.put("userId",userId);
        map.put("userStatus",userStatus);
        Call<RspUpdateUserStatus> call=apiService.doUpdateGroupUserStatus(map);
        call.enqueue(new Callback<RspUpdateUserStatus>() {
            @Override
            public void onResponse(Call<RspUpdateUserStatus> call, Response<RspUpdateUserStatus> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUpdateUserStatus rspUpdateGroupUser=response.body();
                    present.analyzeUpdateGroupUserStatus(rspUpdateGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUpdateUserStatus> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void GroupInfoPresent(AppCompatActivity activity,String groupId, final GroupInfoPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        Call<RspGroupUser> call=apiService.getGroupUserList(map);
        call.enqueue(new Callback<RspGroupUser>() {
            @Override
            public void onResponse(Call<RspGroupUser> call, Response<RspGroupUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspGroupUser rspGroupUser=response.body();
                    present.analyzeRes(rspGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getGroupUser(AppCompatActivity activity,String groupId, String userId,final GroupInfoPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        map.put("userId",userId);
        Call<RspGroupUser> call=apiService.getGroupUser(map);
        call.enqueue(new Callback<RspGroupUser>() {
            @Override
            public void onResponse(Call<RspGroupUser> call, Response<RspGroupUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspGroupUser rspGroupUser=response.body();
                    present.analyzeRes(rspGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void getSearchUser(AppCompatActivity activity,String groupId, String userName,final SearchPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(groupId))
             map.put("groupId",groupId);
        if (!TextUtils.isEmpty(userName))
            map.put("userName",userName);
        Call<RspGroupUser> call=apiService.getGroupUserList(map);
        call.enqueue(new Callback<RspGroupUser>() {
            @Override
            public void onResponse(Call<RspGroupUser> call, Response<RspGroupUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspGroupUser rspGroupUser=response.body();
                    present.analyzeRes(rspGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getSearchUser(AppCompatActivity activity,String params,final SearchPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(params))
            map.put("params",params);
        Call<RspUser> call=apiService.getUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspUser rspGroupUser=response.body();
                    present.analyzeRes(rspGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getGroupByGroupId(AppCompatActivity activity,String id,String groupId, final SendRedPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(groupId))
            map.put("groupId",groupId);
        if (!TextUtils.isEmpty(id))
             map.put("userId",id);
        Call<RspGroupInfo> call=apiService.getGroupByGroupId(map);
        call.enqueue(new Callback<RspGroupInfo>() {
            @Override
            public void onResponse(Call<RspGroupInfo> call, Response<RspGroupInfo> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspGroupInfo rspGroupInfo=response.body();
                    present.analyzeRes(rspGroupInfo);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupInfo> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    //废弃了
    public void getGroupApplyList(String userId) {
        Map<String,String> map=new HashMap<>();
        map.put("handleUserId",userId);
        Call<RspGetApply> call=apiService.getGroupApplyList(map);
        call.enqueue(new Callback<RspGetApply>() {
            @Override
            public void onResponse(Call<RspGetApply> call, Response<RspGetApply> response) {
                try {
                    RspGetApply rspGetApply=response.body();
//                    present.analyzeApplyRes(rspGroupApply);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGetApply> call, Throwable t) {
                Toaster.toastShort("网络异常!");
            }
        });
    }

    public void sendRed(AppCompatActivity activity,String requestNum, String userId,String accid,String groupId,String amount,String type, String count,String thunder,String content,final MIPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("requestNum",requestNum );
        map.put("userId",userId);
        map.put("groupId",groupId);
        map.put("amount",amount);
        map.put("type",type);
        if (!TextUtils.isEmpty(content))
            map.put("cotent",content );
        if (TextUtils.isEmpty(type)){
            map.put("count",count);
        }else {
            if (type.equals("MINE")){
                map.put("thunder",thunder);
                map.put("count",count);
            }
            else {
                map.put("count",count);
            }
        }

        Call<RspSendRed> rspRedCall=apiService.sendRed(map);
        rspRedCall.enqueue(new Callback<RspSendRed>() {
            @Override
            public void onResponse(Call<RspSendRed> call, Response<RspSendRed> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspSendRed rspRed=response.body();
                    present.analyzeRes(rspRed,accid);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<RspSendRed> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void grabRed(String userId, String redId, MIPresent present, AppCompatActivity activity) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        map.put("redPacId",redId);
        Call<RspGrabRed> call=apiService.grabRed(map);
        call.enqueue(new Callback<RspGrabRed>() {
            @Override
            public void onResponse(Call<RspGrabRed> call, Response<RspGrabRed> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
//                RedLoadingDialogFragment.disMiss(activity);
                try {
                    RspGrabRed grabRed=response.body();
                    present.analyzeRes(grabRed);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                    present.showError("解析异常");
                }
            }

            @Override
            public void onFailure(Call<RspGrabRed> call, Throwable t) {
                noNetTips(activity);
                present.showError("网络异常");
            }
        });
    }

    public void getServicesList(AppCompatActivity activity,String serviceFlg, ServicePresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("serviceFlg",serviceFlg );
        Call<RspService> call=apiService.getServices(map);
        call.enqueue(new Callback<RspService>() {
            @Override
            public void onResponse(Call<RspService> call, Response<RspService> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspService rspService=response.body();
                    present.analyzeRes(rspService);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspService> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void getBill(AppCompatActivity activity,String userId,String type,String rebateToUserId,String startDate,String endDate,String page,String rows,AcountPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        if (!TextUtils.isEmpty(type))
            map.put("type",type );
        if (!TextUtils.isEmpty(rebateToUserId))
            map.put("rebateToUserId",rebateToUserId );
        map.put("beginDate",startDate );
        map.put("endDate",endDate );
        map.put("page",page );
        map.put("rows",rows );

        Call<RspBill> call=apiService.getBill(map);
        call.enqueue(new Callback<RspBill>() {
            @Override
            public void onResponse(Call<RspBill> call, Response<RspBill> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspBill rspService=response.body();
                    present.analyzeRes(rspService);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspBill> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void getSendRedBill(AppCompatActivity activity,String userId,String rebateToUserId,String type ,String startDate,String endDate,String page,String rows,AcountPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        if (!TextUtils.isEmpty(rebateToUserId))
            map.put("rebateUserId",rebateToUserId );
        if (!TextUtils.isEmpty(type))
            map.put("type",type );
        map.put("beginDate",startDate );
        map.put("endDate",endDate );
        map.put("page",page );
        map.put("rows",rows );
        Call<RspBill> call=apiService.getSendRedBill(map);
        call.enqueue(new Callback<RspBill>() {
            @Override
            public void onResponse(Call<RspBill> call, Response<RspBill> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspBill rspService=response.body();
                    present.analyzeRes(rspService);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspBill> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getAccountDetail(AppCompatActivity activity,String accountDetailId,AcountPresent present){
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("accountDetailId",accountDetailId );

        Call<RspAccountDetail> call=apiService.getAccountDetail(map);
        call.enqueue(new Callback<RspAccountDetail>() {
            @Override
            public void onResponse(Call<RspAccountDetail> call, Response<RspAccountDetail> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspAccountDetail rspService=response.body();
                    present.analyzeRes(rspService);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspAccountDetail> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void canGrab(AppCompatActivity activity,String userId, String redId, MIPresent miPresent) {
//        LoadingDialogMy loadingDialogMy=new LoadingDialogMy(AppContext.getsActivity(),"");
//        loadingDialogMy.show();
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        map.put("redPacId",redId);
        Call<RspCanGrab> call=apiService.canGrab(map);
        call.enqueue(new Callback<RspCanGrab>() {
            @Override
            public void onResponse(Call<RspCanGrab> call, Response<RspCanGrab> response) {
//                if (loadingDialogMy!=null)
//                    loadingDialogMy.close();
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspCanGrab rspCanGrab=response.body();
                    miPresent.analyzeCanRed(rspCanGrab);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspCanGrab> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void transfer(AppCompatActivity activity,String userId, String requestNum, String toUserId, String amount, RechargePresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        map.put("requestNum",requestNum );
        map.put("toUserId",toUserId );
        map.put("amount",amount );
        Call<RspTransfer> call=apiService.transfer(map);
        call.enqueue(new Callback<RspTransfer>() {
            @Override
            public void onResponse(Call<RspTransfer> call, Response<RspTransfer> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspTransfer transfer=response.body();
                    present.analyzeRes(transfer);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspTransfer> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void subUserList(AppCompatActivity activity,String userId, String sordType, String subUserIdenti, String page,String pageSize, SubUsersPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        if (!TextUtils.isEmpty(sordType))
            map.put("sordType",sordType );
        if (!TextUtils.isEmpty(subUserIdenti))
            map.put("subUserIdenti",subUserIdenti );
        if (!TextUtils.isEmpty(page))
            map.put("page",page );
        if (!TextUtils.isEmpty(pageSize))
            map.put("pageSize",pageSize );
        Call<RspSubUsers> call=apiService.subUserList(map);
        call.enqueue(new Callback<RspSubUsers>() {
            @Override
            public void onResponse(Call<RspSubUsers> call, Response<RspSubUsers> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspSubUsers transfer=response.body();
                    present.analyzeRes(transfer);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspSubUsers> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void redDetailList(AppCompatActivity activity,String redPacId, String groupId,RedDetailPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("redPacId",redPacId );
        map.put("groupId",groupId );
        Call<RspRedDetail> call=apiService.redDetailList(map);
        call.enqueue(new Callback<RspRedDetail>() {
            @Override
            public void onResponse(Call<RspRedDetail> call, Response<RspRedDetail> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspRedDetail rspRedDetail=response.body();
                    present.analyzeRes(rspRedDetail);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspRedDetail> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void inputMoney(AppCompatActivity activity,String pay_id,String type,String price ,InputMoneyPresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        map.put("pay_id",pay_id );
        map.put("type",type );
        map.put("price",price );
        Call<RspInMoney> call=apiService.inputMoney(map);
        call.enqueue(new Callback<RspInMoney>() {
            @Override
            public void onResponse(Call<RspInMoney> call, Response<RspInMoney> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspInMoney rspInMoney=response.body();
                    present.analyzeInputMoney(rspInMoney);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspInMoney> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void groupSave(AppCompatActivity activity,RspGroupInfo.GroupInfo reqGroup, AddGroupPresent present,String updateUser,int flag) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(reqGroup.getGroupName()))
             map.put("groupName",reqGroup.getGroupName());
        if (!TextUtils.isEmpty(reqGroup.getGroupId()))
            map.put("groupId",reqGroup.getGroupId());
        if (!TextUtils.isEmpty(reqGroup.getGroupType()))
            map.put("groupType",reqGroup.getGroupType());
        if (!TextUtils.isEmpty(reqGroup.getGameType()))
            map.put("gameType",reqGroup.getGameType());
        if (!TextUtils.isEmpty(reqGroup.getCreateId()))
            map.put("createId",reqGroup.getCreateId());
//        map.put("totalNumber",reqGroup.getCreateId());
        if (!TextUtils.isEmpty(reqGroup.getNotice()))
            map.put("notice",reqGroup.getNotice());
        if (!TextUtils.isEmpty(reqGroup.getRequirement()))
            map.put("requirement",reqGroup.getRequirement());
        map.put("multipleRate",reqGroup.getMultipleRate()+"");
        map.put("startRange",reqGroup.getStartRange()+"");
        map.put("endRange",reqGroup.getEndRange()+"");
        //"[{\"id\":831,\"groupId\":\"655877401\",\"amountReward\":\"1\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"}]"
        if (!TextUtils.isEmpty(reqGroup.getRewardRules()))
//            map.put("rewardRules","[{\"id\":831,\"groupId\":\"655877401\",\"amountReward\":\"1\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"},{\"id\":832,\"groupId\":\"655877401\",\"amountReward\":\"23\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":833,\"groupId\":\"655877401\",\"amountReward\":\"1.1\",\"rewardName\":\"奖励\",\"rewardValue\":\"12.00\"},{\"id\":834,\"groupId\":\"655877401\",\"amountReward\":\"1.5\",\"rewardName\":\"奖励\",\"rewardValue\":\"1.10\"},{\"id\":835,\"groupId\":\"655877401\",\"amountReward\":\"4\",\"rewardName\":\"奖励\",\"rewardValue\":\"4.00\"},{\"id\":836,\"groupId\":\"655877401\",\"amountReward\":\"5\",\"rewardName\":\"奖励\",\"rewardValue\":\"5.00\"}]");
            map.put("rewardRules",reqGroup.getRewardRules());
        if (!TextUtils.isEmpty(reqGroup.getHallDisplay()))
             map.put("hallDisplay",reqGroup.getHallDisplay());
        if (!TextUtils.isEmpty(reqGroup.getJoinmode()))
            map.put("joinmode",reqGroup.getJoinmode());
        map.put("grabBagNumber",reqGroup.getGrabBagNumber()+"");
        if (!TextUtils.isEmpty(updateUser))
            map.put("updateUserId",updateUser);
        Call<RspGroupSave> call=null;
        if (flag==0)
            call=apiService.groupSave(map);
        else if(flag==1)
            call=apiService.groupUpdate(map);
        call.enqueue(new Callback<RspGroupSave>() {
            @Override
            public void onResponse(Call<RspGroupSave> call, Response<RspGroupSave> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspGroupSave rspGroupSave=response.body();
                    present.analyzeAddGroup(rspGroupSave);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupSave> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getNotices(AppCompatActivity activity,String type, SysNoticePresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        Call<RspNotice> call=apiService.getNotices(map);
        call.enqueue(new Callback<RspNotice>() {
            @Override
            public void onResponse(Call<RspNotice> call, Response<RspNotice> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspNotice rspGroupSave=response.body();
                    present.analyze(rspGroupSave);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspNotice> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }

    public void getMoney(AppCompatActivity activity,String type, SysNoticePresent present) {
        LoadingDialogN.Loading_Show(activity.getSupportFragmentManager(),isCancle);
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        Call<RspMoney> call=apiService.getMoneyPic(map);
        call.enqueue(new Callback<RspMoney>() {
            @Override
            public void onResponse(Call<RspMoney> call, Response<RspMoney> response) {
                LoadingDialogN.Loading_Exit(activity.getSupportFragmentManager());
                try {
                    RspMoney rspMoney=response.body();
                    present.analyzeMoney(rspMoney);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspMoney> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
    public void getConfig(AppCompatActivity activity,String key, SysNoticePresent present) {
//        LoadingDialog.Loading_Show(AppContext.getActivitiesStack().peek().getSupportFragmentManager(),isCancle);
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        Call<RspConfig> call=apiService.getConfig(map);
        call.enqueue(new Callback<RspConfig>() {
            @Override
            public void onResponse(Call<RspConfig> call, Response<RspConfig> response) {
//                LoadingDialog.Loading_Exit(AppContext.getActivitiesStack().peek().getSupportFragmentManager());
                try {
                    RspConfig rspMoney=response.body();
                    present.analyzeConfig(rspMoney);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspConfig> call, Throwable t) {
                noNetTips(activity);
            }
        });
    }
}
