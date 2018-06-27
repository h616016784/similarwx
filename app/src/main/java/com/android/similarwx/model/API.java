package com.android.similarwx.model;

import android.app.Activity;
import android.text.TextUtils;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.request.ReqGroup;
import com.android.similarwx.beans.response.RspBill;
import com.android.similarwx.beans.response.RspCanGrab;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspGroupApply;
import com.android.similarwx.beans.response.RspGroupSave;
import com.android.similarwx.beans.response.RspGroupUser;
import com.android.similarwx.beans.response.RspInMoney;
import com.android.similarwx.beans.response.RspNotice;
import com.android.similarwx.beans.response.RspRed;
import com.android.similarwx.beans.response.RspRedDetail;
import com.android.similarwx.beans.response.RspSendRed;
import com.android.similarwx.beans.response.RspService;
import com.android.similarwx.beans.response.RspTransfer;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.model.interceptor.LogInterceptor;
import com.android.similarwx.present.AcountPresent;
import com.android.similarwx.present.AddGroupPresent;
import com.android.similarwx.present.ClientDetailInfoPresent;
import com.android.similarwx.present.GroupInfoPresent;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.present.InputMoneyPresent;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.present.MyBasePresent;
import com.android.similarwx.present.NoticePresent;
import com.android.similarwx.present.RechargePresent;
import com.android.similarwx.present.RedDetailPresent;
import com.android.similarwx.present.RegisterPresent;
import com.android.similarwx.present.ServicePresent;
import com.android.similarwx.widget.dialog.RedLoadingDialogFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Cache;
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
    private static APIService apiService;
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
    public void register(String account, String weixinAccount, String email, String name, String password, String nick, final RegisterPresent present){
        Map<String,String> map=new HashMap<>();
        map.put("accId",account);
        map.put("passwdStr",password);
        map.put("email",email);
        map.put("mobile",name);
        map.put("wechatAccount",weixinAccount);
        map.put("name",nick);
        Call<RspUser> user=apiService.registe(map);
        user.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                RspUser rspUser=response.body();
                present.analyzeRes(rspUser);
            }
            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {

            }
        });
    }
    public void login(String name, String password, String weixin, String mobile, final LoginPresent present){
        Map<String,String> map=new HashMap<>();
        map.put("accId",name);
        map.put("mobile",mobile);
        map.put("wechatAccount",weixin);
        map.put("passwdStr",password);
        Call<RspUser> user=apiService.login(map);
        user.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void updateUserByNick(String id, String nick, MyBasePresent present){
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("name",nick);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
    public void updateUserBySign(String id, String personalitySignature, MyBasePresent present){
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("personalitySignature",personalitySignature);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
    public void updateUserByGender(String id, String gender, MyBasePresent present){
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("gender",gender);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
    public void updateUserByUrl(String id, String url, MyBasePresent present){
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("icon",url);

        Call<RspUser> call=apiService.updateUser(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
    public void getUserInfoByParams(String userId, String accId, ClientDetailInfoPresent present){
        Map<String,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(userId))
            map.put("userId",userId);
        if (!TextUtils.isEmpty(accId))
            map.put("accId",accId);

        Call<RspUser> call=apiService.getUserInfoByParams(map);
        call.enqueue(new Callback<RspUser>() {
            @Override
            public void onResponse(Call<RspUser> call, Response<RspUser> response) {
                try {
                    RspUser rspUser=response.body();
                    present.analyzeRes(rspUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
    public void reqGroupList(String userId , final GroupPresent present){
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
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void doGroupAppley(String groupId, String userId, final GroupPresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        map.put("applyUserId",userId);
        map.put("applyInfo","Android App申请");
        Call<RspGroupApply> call=apiService.doGroupAppley(map);
        call.enqueue(new Callback<RspGroupApply>() {
            @Override
            public void onResponse(Call<RspGroupApply> call, Response<RspGroupApply> response) {
                try {
                    RspGroupApply rspGroupApply=response.body();
                    present.analyzeApplyRes(rspGroupApply);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupApply> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
    public void GroupInfoPresent(String groupId, final GroupInfoPresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        Call<RspGroupUser> call=apiService.getGroupUserList(map);
        call.enqueue(new Callback<RspGroupUser>() {
            @Override
            public void onResponse(Call<RspGroupUser> call, Response<RspGroupUser> response) {
                try {
                    RspGroupUser rspGroupUser=response.body();
                    present.analyzeRes(rspGroupUser);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupUser> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void getNotices(final NoticePresent present) {
        Map<String,String> map=new HashMap<>();
        Call<RspNotice> rspNoticeCall=apiService.getNotices(map);
        rspNoticeCall.enqueue(new Callback<RspNotice>() {
            @Override
            public void onResponse(Call<RspNotice> call, Response<RspNotice> response) {
                try {
                    RspNotice rspNotice=response.body();
                    present.analyzeRes(rspNotice);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspNotice> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void sendRed(String requestNum, String userId,String groupId,String amount,String type, String thunder,final MIPresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("requestNum",requestNum );
        map.put("userId",userId);
        map.put("groupId",groupId);
        map.put("amount",amount);
        map.put("type",type);
        map.put("thunder",thunder);
        Call<RspSendRed> rspRedCall=apiService.sendRed(map);
        rspRedCall.enqueue(new Callback<RspSendRed>() {
            @Override
            public void onResponse(Call<RspSendRed> call, Response<RspSendRed> response) {
                try {
                    RspSendRed rspRed=response.body();
                    present.analyzeRes(rspRed);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<RspSendRed> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void grabRed(String userId, String redId, MIPresent present, Activity activity) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        map.put("redPacId",redId);
        Call<RspGrabRed> call=apiService.grabRed(map);
        call.enqueue(new Callback<RspGrabRed>() {
            @Override
            public void onResponse(Call<RspGrabRed> call, Response<RspGrabRed> response) {
                RedLoadingDialogFragment.disMiss(activity);
                try {
                    RspGrabRed grabRed=response.body();
                    present.analyzeRes(grabRed);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGrabRed> call, Throwable t) {
                RedLoadingDialogFragment.disMiss(activity);
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void getServicesList(String userType, ServicePresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("userType",userType );
        Call<RspService> call=apiService.getServices(map);
        call.enqueue(new Callback<RspService>() {
            @Override
            public void onResponse(Call<RspService> call, Response<RspService> response) {
                try {
                    RspService rspService=response.body();
                    present.analyzeRes(rspService);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspService> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void getBill(String userId,String type,String startDate,String endDate,AcountPresent present){
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        map.put("type",type );
        map.put("startDate",startDate );
        map.put("endDate",endDate );

        Call<RspBill> call=apiService.getBill(map);
        call.enqueue(new Callback<RspBill>() {
            @Override
            public void onResponse(Call<RspBill> call, Response<RspBill> response) {
                try {
                    RspBill rspService=response.body();
                    present.analyzeRes(rspService);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspBill> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void canGrab(String userId, String redId, MIPresent miPresent, Activity activity) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        map.put("redPacId",redId);
        Call<RspCanGrab> call=apiService.canGrab(map);
        call.enqueue(new Callback<RspCanGrab>() {
            @Override
            public void onResponse(Call<RspCanGrab> call, Response<RspCanGrab> response) {
                RedLoadingDialogFragment.disMiss(activity);
                try {
                    RspCanGrab rspCanGrab=response.body();
                    miPresent.analyzeCanRed(rspCanGrab);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspCanGrab> call, Throwable t) {
                RedLoadingDialogFragment.disMiss(activity);
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void transfer(String userId, String requestNum, String toUserId, String amount, RechargePresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId );
        map.put("requestNum",requestNum );
        map.put("toUserId",toUserId );
        map.put("amount",amount );
        Call<RspTransfer> call=apiService.transfer(map);
        call.enqueue(new Callback<RspTransfer>() {
            @Override
            public void onResponse(Call<RspTransfer> call, Response<RspTransfer> response) {
                try {
                    RspTransfer transfer=response.body();
                    present.analyzeRes(transfer);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspTransfer> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void redDetailList(String redPacId, String groupId,RedDetailPresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("redPacId",redPacId );
        map.put("groupId",groupId );
        Call<RspRedDetail> call=apiService.redDetailList(map);
        call.enqueue(new Callback<RspRedDetail>() {
            @Override
            public void onResponse(Call<RspRedDetail> call, Response<RspRedDetail> response) {
                try {
                    RspRedDetail rspRedDetail=response.body();
                    present.analyzeRes(rspRedDetail);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspRedDetail> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void inputMoney(String pay_id,String type,String price ,InputMoneyPresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("pay_id",pay_id );
        map.put("type",type );
        map.put("price",price );
        Call<RspInMoney> call=apiService.inputMoney(map);
        call.enqueue(new Callback<RspInMoney>() {
            @Override
            public void onResponse(Call<RspInMoney> call, Response<RspInMoney> response) {
                try {
                    RspInMoney rspInMoney=response.body();
                    present.analyzeInputMoney(rspInMoney);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspInMoney> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }

    public void groupSave(ReqGroup reqGroup, AddGroupPresent present) {
        Map<String,String> map=new HashMap<>();
        map.put("groupName",reqGroup.getGroupName());
        map.put("groupType",reqGroup.getGroupType());
        map.put("createId",reqGroup.getCreateId());
        map.put("notice",reqGroup.getNotice());
        map.put("requirement",reqGroup.getRequirement());
        map.put("multipleRate",reqGroup.getMultipleRate());
        map.put("startRange",reqGroup.getStartRange());
        map.put("endRange",reqGroup.getEndRange());
        map.put("rewardRules",reqGroup.getRewardRules());
        Call<RspGroupSave> call=apiService.groupSave(map);
        call.enqueue(new Callback<RspGroupSave>() {
            @Override
            public void onResponse(Call<RspGroupSave> call, Response<RspGroupSave> response) {
                try {
                    RspGroupSave rspGroupSave=response.body();
                    present.analyzeAddGroup(rspGroupSave);
                }catch (Exception e){
                    Toaster.toastShort(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<RspGroupSave> call, Throwable t) {
                Toaster.toastShort(t.getMessage());
            }
        });
    }
}
