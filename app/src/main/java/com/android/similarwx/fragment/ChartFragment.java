package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        unbinder = ButterKnife.bind(this, view);
        chartRv.setLayoutManager(new LinearLayoutManager(activity));
        baseQuickAdapter = new BaseQuickAdapter<RecentContact, BaseViewHolder>(R.layout.item_chart, null) {
            @Override
            protected void convert(BaseViewHolder helper, RecentContact item) {
                helper.setText(R.id.item_chart_name_tv, item.getFromNick());
                helper.setText(R.id.item_chart_content_tv, item.getContent());
            }
        };
        chartRv.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<RecentContact> list = baseQuickAdapter.getData();
                if (list != null && list.size() > 0) {
                    RecentContact recentContact = list.get(position);
                    if (recentContact != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_THREE);
                        bundle.putSerializable(AppConstants.CHAT_TYPE, SessionTypeEnum.P2P);
                        bundle.putString(AppConstants.CHAT_ACCOUNT_ID, recentContact.getContactId());
                        bundle.putString(AppConstants.CHAT_ACCOUNT_NAME, recentContact.getContactId());
                        FragmentUtils.navigateToNormalActivity(activity, new MIFragmentNew(), bundle);
                    }
                }
            }
        });
        queryRecentContacts();
        querySystemMessages();
        return view;
    }

    private void querySystemMessages() {
        APIYUNXIN.getTeamNotice(new YCallBack<List<SystemMessage>>() {
            @Override
            public void callBack(List<SystemMessage> systemMessages) {
                Log.e("callBack", systemMessages.size() + "");
                if (systemMessages != null && systemMessages.size() > 0) {

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
                            baseQuickAdapter.addData(result);
//                            for (RecentContact recentContact:result){
//
//                            }
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
