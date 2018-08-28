package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.TimeUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChartFragment extends BaseFragment {

    @BindView(R.id.chart_rv)
    RecyclerView chartRv;
    Unbinder unbinder;
    @BindView(R.id.chart_name_tv)
    TextView chartNameTv;
    @BindView(R.id.chart_content_tv)
    TextView chartContentTv;
    @BindView(R.id.chart_role_tv)
    TextView chartRoleTv;
    @BindView(R.id.team_chart_un_read_tv)
    TextView teamChartUnReadTv;
    @BindView(R.id.chart_iv)
    ImageView chartIv;
    @BindView(R.id.chart_ll)
    LinearLayout chartLl;
    private BaseQuickAdapter baseQuickAdapter;
    private List<RecentContact> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_chart;
    }

    @Override
    protected void onInitView(View contentView) {
        mActionbar.setTitle("消息");
        unbinder = ButterKnife.bind(this, contentView);
        chartRv.setLayoutManager(new LinearLayoutManager(activity));
        baseQuickAdapter = new BaseQuickAdapter<RecentContact, BaseViewHolder>(R.layout.item_chart, null) {
            @Override
            protected void convert(BaseViewHolder helper, RecentContact item) {
                String account=item.getContactId();
                NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(account);//用户详情
                helper.setText(R.id.item_chart_name_tv, user.getName());
                helper.setText(R.id.item_chart_content_tv, item.getContent());
                helper.setText(R.id.item_chart_role_tv, TimeUtil.timestampToString(item.getTime()));
                String icon =user.getAvatar();
                if (!TextUtils.isEmpty(icon)) {
                    NetImageUtil.glideImageCircle(activity,icon,(ImageView) helper.getView(R.id.item_chart_iv));
                }
                int unReadCount=item.getUnreadCount();
                if (unReadCount>0){
                    helper.setGone(R.id.item_chart_un_read_tv,true);
                    if (unReadCount>=10)
                        helper.setText(R.id.item_chart_un_read_tv,"9+");
                    else
                        helper.setText(R.id.item_chart_un_read_tv,unReadCount+"");
                }else {
                    helper.setGone(R.id.item_chart_un_read_tv,false);
                }
            }
        };
        chartRv.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<RecentContact> list = baseQuickAdapter.getData();
                if (list != null && list.size() > 0) {
                    RecentContact recentContact = list.get(position);
                    if (TextUtils.isEmpty(NimUIKit.getAccount())){
                        String accid= SharePreferenceUtil.getString(activity,AppConstants.USER_ACCID,"");
                        String token=SharePreferenceUtil.getString(activity,AppConstants.USER_TOKEN,"");
                        LoginInfo loginInfo=new LoginInfo(accid,token);
                        doNimLogin(loginInfo,recentContact);
                    }else {
                        if (recentContact.getSessionType()==SessionTypeEnum.P2P){
                            NimUIKit.startP2PSession(activity, recentContact.getContactId());
                        }else if (recentContact.getSessionType()==SessionTypeEnum.Team){
                            // 打开群聊界面
                            NimUIKit.startTeamSession(activity, recentContact.getContactId());
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        queryRecentContacts();
        querySystemMessages();
    }


    private void querySystemMessages() {
        APIYUNXIN.getTeamNotice(new YCallBack<List<SystemMessage>>() {
            @Override
            public void callBack(List<SystemMessage> systemMessages) {
                Log.e("callBack", systemMessages.size() + "");
                if (systemMessages != null && systemMessages.size() > 0) {
                    chartLl.setVisibility(View.VISIBLE);
                    SystemMessage message = systemMessages.get(0);
                    NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(message.getFromAccount());//用户详情
                    if (user!=null){
                        switch (message.getType().getValue()){
                            case 0:
                                chartContentTv.setText(user.getName()+" 入群申请");
                                break;
                            case 1:
                                chartContentTv.setText(user.getName()+" 拒绝入群");
                                break;
                            case 2:
                                chartContentTv.setText(user.getName()+" 邀请入群");
                                break;
                            case 3:
                                chartContentTv.setText(user.getName()+" 拒绝邀请");
                                break;
                            case 5:
                                chartContentTv.setText(user.getName()+" 入群申请");
                                break;
                            default:
                                chartContentTv.setText("未知");
                        }
                        chartRoleTv.setText(TimeUtil.timestampToString(message.getTime()));
                    }
                    int unread = NIMClient.getService(SystemMessageService.class)
                            .querySystemMessageUnreadCountBlock();
                    if (unread>0){
                        teamChartUnReadTv.setVisibility(View.VISIBLE);
                        if (unread>=10)
                            teamChartUnReadTv.setText("9+");
                        else
                            teamChartUnReadTv.setText(unread);

                    }else
                        teamChartUnReadTv.setVisibility(View.GONE);
                }else {
                    chartLl.setVisibility(View.GONE);
                }
            }
        });
//        String userid= SharePreferenceUtil.getString(activity,AppConstants.USER_ID,"");
//        API.getInstance().getGroupApplyList(userid);
    }

    private void queryRecentContacts() {
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> result, Throwable exception) {
                        if (result != null && result.size() > 0) {
                            baseQuickAdapter.getData().clear();
                            for (RecentContact recentContact:result){
                                SessionTypeEnum sessionTypeEnum=recentContact.getSessionType();
                                if (sessionTypeEnum==SessionTypeEnum.P2P){
                                    MsgTypeEnum msgTypeEnum=recentContact.getMsgType();
                                    if (!(msgTypeEnum==MsgTypeEnum.notification)){
                                        baseQuickAdapter.addData(recentContact);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private void doNimLogin(LoginInfo loginInfo,RecentContact recentContact) {
        NimUIKit.login(loginInfo, new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                if (recentContact.getSessionType()==SessionTypeEnum.P2P){
                    NimUIKit.startP2PSession(activity, recentContact.getContactId());
                }else if (recentContact.getSessionType()==SessionTypeEnum.Team){
                    // 打开群聊界面
                    NimUIKit.startTeamSession(activity, recentContact.getContactId());
                }
                NIMClient.getService(AuthService.class).openLocalCache(loginInfo.getAccount());
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
                    Toaster.toastShort("登录失败");
                } else {
                    Toaster.toastShort("登录失败");
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toaster.toastShort("登录异常");
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.chart_ll)
    public void onViewClicked() {
        FragmentUtils.navigateToNormalActivity(activity, new NoticeFragment(), null);
    }
}
