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
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.TimeUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

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
                String account=item.getFromAccount();
                NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(account);//用户详情
                helper.setText(R.id.item_chart_name_tv, user.getName());
                helper.setText(R.id.item_chart_content_tv, item.getContent());
                helper.setText(R.id.item_chart_role_tv, TimeUtil.timestampToString(item.getTime(),"yyyy-MM-dd"));
                String icon =user.getAvatar();
                if (!TextUtils.isEmpty(icon)) {
                    Glide.with(activity).load(icon)
//                            .error(R.drawable.rp_avatar)
//                            .override(1200,120)
//                            .transform(new CircleCrop(activity))
//                            .placeholder(R.drawable.rp_avatar)
                            .into((ImageView) helper.getView(R.id.item_chart_iv));
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
                    if (recentContact.getSessionType()==SessionTypeEnum.P2P){
                        NimUIKit.startP2PSession(activity, recentContact.getContactId());
                    }else if (recentContact.getSessionType()==SessionTypeEnum.Team){
                        // 打开群聊界面
                        NimUIKit.startTeamSession(activity, recentContact.getContactId());
                    }
//                    if (recentContact != null) {
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_THREE);
//                        bundle.putSerializable(AppConstants.CHAT_TYPE, SessionTypeEnum.P2P);
//                        bundle.putString(AppConstants.CHAT_ACCOUNT_ID, recentContact.getContactId());
//                        bundle.putString(AppConstants.CHAT_ACCOUNT_NAME, recentContact.getContactId());
//                        FragmentUtils.navigateToNormalActivity(activity, new MIFragmentNew(), bundle);
//                    }
                }
            }
        });
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


                    Gson gson=new Gson();
                    User user = null;
                    String content=message.getContent();
                    try {
                        user =gson.fromJson(content, User.class);
                    }catch (Exception e){

                    }
                    if (user!=null){
                        chartContentTv.setText(user.getName());
                        chartRoleTv.setText(user.getPasswd());
                    }

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
                            for (RecentContact recentContact:result){
                                MsgTypeEnum msgTypeEnum=recentContact.getMsgType();
                                if (!(msgTypeEnum==MsgTypeEnum.notification)){
                                    baseQuickAdapter.addData(recentContact);
                                }
                            }
                        }
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
