package com.android.similarwx.model;

import com.android.similarwx.beans.AccToken;
import com.android.similarwx.beans.UserInfoWX;
import com.android.similarwx.beans.response.BaseResponse;
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

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/4/27.
 */

public interface APIService {

    @POST("resUsers/register")
    Call<RspUser> registe(@QueryMap Map<String,String> map);

    @POST("resUsers/login")
    Call<RspUser> login(@QueryMap Map<String,String> map);

    @POST("resUsers/weChatLogin")
    Call<RspUser> WxLogin(@QueryMap Map<String, String> map);

    @POST("resUsers/logout")
    Call<RspUser> logout(@QueryMap Map<String,String> map);

    @POST("resUsers/getTotalBalance")
    Call<RspUser> getTotalBalance(@QueryMap Map<String,String> map);

    @POST("resUsers/setInvitationCode")
    Call<RspUser> setInvitationCode(@QueryMap Map<String, String> map);

    @POST("resUsers/getCustomerServiceUserList")
    Call<RspService> getServices(@QueryMap Map<String, String> map);

    @POST("resUsers/setPaymentPasswd")
    Call<RspSetPassword> setPaymentPasswd(@QueryMap Map<String, String> map);

    @POST("resUsers/getMobileVerifyCode")
    Call<BaseResponse> getMobileVerifyCode(@QueryMap Map<String,String> map);

    @POST("resUsers/getCashUser")
    Call<RspCashUser> getCashUser(@QueryMap Map<String,String> map);

    @FormUrlEncoded
    @POST("resUsers/update")
    Call<RspUser> updateUser(@FieldMap Map<String, String> map);

    @POST("group/getGroupList")
    Call<RspGroup> reqGroupList(@QueryMap Map<String,String> map);

    @POST("group/doGroupApply")
    Call<RspGroupApply> doGroupAppley(@QueryMap Map<String,String> map);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("group/getGroupUserList")
    Call<RspGroupUser> getGroupUserList(@FieldMap Map<String,String> map);

    @POST("group/getGroupUser")
    Call<RspGroupUser> getGroupUser(@QueryMap Map<String,String> map);

    @POST("group/getGroupByGroupId")
    Call<RspGroupInfo> getGroupByGroupId(@QueryMap Map<String,String> map);

    @POST("sys/noticeList")
    Call<RspNotice> getNotices(@QueryMap Map<String, String> map);

    @POST("sys/moneyPic")
    Call<RspMoney> getMoneyPic(@QueryMap Map<String, String> map);
    @POST("sys/config")
    Call<RspConfig> getConfig(@QueryMap Map<String, String> map);

    @POST("redPac/send")
    Call<RspSendRed> sendRed(@QueryMap Map<String, String> map);

    @POST("redPac/canGrab")
    Call<RspCanGrab> canGrab(@QueryMap Map<String, String> map);

    @POST("redPac/grab")
    Call<RspGrabRed> grabRed(@QueryMap Map<String, String> map);

    @POST("trade/accountList")
    Call<RspBill> getBill(@QueryMap Map<String, String> map);

    @POST("trade/accountDetail")
    Call<RspAccountDetail> getAccountDetail(@QueryMap Map<String, String> map);

    @POST("trade/transfer")
    Call<RspTransfer> transfer(@QueryMap Map<String, String> map);
    @POST("trade/subUserList")
    Call<RspSubUsers> subUserList(@QueryMap Map<String, String> map);

    @POST("redPac/list")
    Call<RspRedDetail> redDetailList(@QueryMap Map<String, String> map);

    @POST("codePay/send")
    Call<RspInMoney>  inputMoney(@QueryMap Map<String, String> map);


    //创建群组信息
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("group/save")
    Call<RspGroupSave>  groupSave(@FieldMap Map<String, String> map);

    //更新群组信息
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("group/update")
    Call<RspGroupSave>  groupUpdate(@FieldMap Map<String, String> map);

    //添加群组成员
    @POST("group/doAddGroupUser")
    Call<RspAddGroupUser>  doAddGroupUser(@QueryMap Map<String, String> map);
    //解散群组
    @POST("group/doDeleteGroup")
    Call<RspDeleteGroup>  doDeleteGroup(@QueryMap Map<String, String> map);
    //踢出群组或退出群组
    @POST("group/doDeleteGroupUser")
    Call<RspDeleteGroupUser>  doDeleteGroupUser(@QueryMap Map<String, String> map);
    //任命或者取消任命管理员
    @POST("group/doUpdateGroupUser")
    Call<RspUpdateGroupUser>  doUpdateGroupUser(@QueryMap Map<String, String> map);

    //禁言/解禁群组用户
    @POST("group/doUpdateGroupUserStatus")
    Call<RspUpdateUserStatus>  doUpdateGroupUserStatus(@QueryMap Map<String, String> map);

    @POST("resUsers/getUserInfoByParams")
    Call<RspUser>  getUserInfoByParams(@QueryMap Map<String, String> map);

    @POST("group/getGroupApplyList")
    Call<RspGetApply> getGroupApplyList(@QueryMap Map<String,String> map);

    @GET("sns/oauth2/access_token")
    Call<AccToken> accessToken(@QueryMap Map<String,String> map);

    @GET("sns/userinfo")
    Call<UserInfoWX> userInfoWX(@QueryMap Map<String,String> map);


}
