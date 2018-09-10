package com.android.similarwx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.netease.nim.uikit.business.uinfo.UserInfoHelper;
import com.netease.nim.uikit.common.badger.Badger;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChartFragment extends BaseFragment {

    // 置顶功能可直接使用，也可作为思路，供开发者充分利用RecentContact的tag字段
    public static final long RECENT_TAG_STICKY = 1; // 联系人置顶tag

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
                List<RecentContact> personlist = baseQuickAdapter.getData();
                if (personlist != null && personlist.size() > 0) {
                    RecentContact recentContact = personlist.get(position);
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
        baseQuickAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                showLongClickMenu((RecentContact) baseQuickAdapter.getData().get(position),position);
                return false;
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
                            teamChartUnReadTv.setText(unread+"");

                    }else
                        teamChartUnReadTv.setVisibility(View.GONE);
                }else {
                    chartLl.setVisibility(View.GONE);
                }
            }
        },0);
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
                            list=baseQuickAdapter.getData();
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

    private void showLongClickMenu(final RecentContact recent, final int position) {
        CustomAlertDialog alertDialog = new CustomAlertDialog(getActivity());
        alertDialog.setTitle(UserInfoHelper.getUserTitleName(recent.getContactId(), recent.getSessionType()));
        String title = getString(R.string.main_msg_list_delete_chatting);
        alertDialog.addItem(title, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                // 删除会话，删除后，消息历史被一起删除
                NIMClient.getService(MsgService.class).deleteRecentContact(recent);
                NIMClient.getService(MsgService.class).clearChattingHistory(recent.getContactId(), recent.getSessionType());
                baseQuickAdapter.remove(position);

                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        refreshMessages(true);
                    }
                });
            }
        });

        title = (isTagSet(recent, RECENT_TAG_STICKY) ? getString(R.string.main_msg_list_clear_sticky_on_top) : getString(R.string.main_msg_list_sticky_on_top));
        alertDialog.addItem(title, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                if (isTagSet(recent, RECENT_TAG_STICKY)) {
                    removeTag(recent, RECENT_TAG_STICKY);
                } else {
                    addTag(recent, RECENT_TAG_STICKY);
                }
                NIMClient.getService(MsgService.class).updateRecent(recent);

                refreshMessages(false);
            }
        });

//        alertDialog.addItem("删除该聊天（仅服务器）", new CustomAlertDialog.onSeparateItemClickListener() {
//            @Override
//            public void onClick() {
//                NIMClient.getService(MsgService.class)
//                        .deleteRoamingRecentContact(recent.getContactId(), recent.getSessionType())
//                        .setCallback(new RequestCallback<Void>() {
//                            @Override
//                            public void onSuccess(Void param) {
//                                Toast.makeText(getActivity(), "delete success", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onFailed(int code) {
//                                Toast.makeText(getActivity(), "delete failed, code:" + code, Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onException(Throwable exception) {
//
//                            }
//                        });
//            }
//        });
        alertDialog.show();
    }
    private void addTag(RecentContact recent, long tag) {
        tag = recent.getTag() | tag;
        recent.setTag(tag);
    }

    private void removeTag(RecentContact recent, long tag) {
        tag = recent.getTag() & ~tag;
        recent.setTag(tag);
    }
    private boolean isTagSet(RecentContact recent, long tag) {
        return (recent.getTag() & tag) == tag;
    }
    private void refreshMessages(boolean unreadChanged) {
        sortRecentContacts(list);
        baseQuickAdapter.notifyDataSetChanged();

        if (unreadChanged) {

            // 方式一：累加每个最近联系人的未读（快）

            int unreadNum = 0;
            for (RecentContact r : list) {
                unreadNum += r.getUnreadCount();
            }

            // 方式二：直接从SDK读取（相对慢）
            //int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();

//            if (callback != null) {
//                callback.onUnreadCountChange(unreadNum);
//            }

            Badger.updateBadgerCount(unreadNum);
        }
    }
    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContacts(List<RecentContact> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }
    private static Comparator<RecentContact> comp = new Comparator<RecentContact>() {

        @Override
        public int compare(RecentContact o1, RecentContact o2) {
            // 先比较置顶tag
            long sticky = (o1.getTag() & RECENT_TAG_STICKY) - (o2.getTag() & RECENT_TAG_STICKY);
            if (sticky != 0) {
                return sticky > 0 ? -1 : 1;
            } else {
                long time = o1.getTime() - o2.getTime();
                return time == 0 ? 0 : (time > 0 ? -1 : 1);
            }
        }
    };
    protected final void postRunnable(final Runnable runnable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
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
