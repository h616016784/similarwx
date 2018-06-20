package com.android.similarwx.model;

import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.User;
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

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface APIService {

    @POST("resUsers/register")
    Call<RspUser> registe(@QueryMap Map<String,String> map);

    @POST("resUsers/login")
    Call<RspUser> login(@QueryMap Map<String,String> map);

    @POST("resUsers/getCustomerServiceUserList")
    Call<RspService> getServices(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("resUsers/update")
    Call<RspUser> updateUser(@FieldMap Map<String, String> map);

    @POST("group/getGroupList")
    Call<RspGroup> reqGroupList(@QueryMap Map<String,String> map);

    @POST("group/doGroupApply")
    Call<RspGroupApply> doGroupAppley(@QueryMap Map<String,String> map);

    @POST("group/getGroupUserList")
    Call<RspGroupUser> getGroupUserList(@QueryMap Map<String,String> map);

    @POST("sys/noticeList")
    Call<RspNotice> getNotices(@QueryMap Map<String, String> map);

    @POST("redPac/send")
    Call<RspSendRed> sendRed(@QueryMap Map<String, String> map);

    @POST("redPac/canGrab")
    Call<RspCanGrab> canGrab(@QueryMap Map<String, String> map);

    @POST("redPac/grab")
    Call<RspGrabRed> grabRed(@QueryMap Map<String, String> map);

    @POST("trade/accountList")
    Call<RspBill> getBill(@QueryMap Map<String, String> map);

    @POST("trade/transfer")
    Call<RspTransfer> transfer(@QueryMap Map<String, String> map);

    @POST("redPac/list")
    Call<RspRedDetail> redDetailList(@QueryMap Map<String, String> map);

    @POST("codePay/send")
    Call<RspInMoney>  inputMoney(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("group/save")
    Call<RspGroupSave>  groupSave(@FieldMap Map<String, String> map);
}
