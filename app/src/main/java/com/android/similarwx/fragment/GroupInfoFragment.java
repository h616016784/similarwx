package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.RewardRule;
import com.android.similarwx.inteface.GroupInfoViewInterface;
import com.android.similarwx.present.GroupInfoPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class GroupInfoFragment extends BaseFragment implements GroupInfoViewInterface{
    @BindView(R.id.group_info_member_rv)
    RecyclerView groupInfoMemberRv;
    @BindView(R.id.group_info_member_num_tv)
    TextView groupInfoMemberNumTv;
    @BindView(R.id.group_info_name_tv)
    TextView groupInfoNameTv;
    @BindView(R.id.group_info_code_iv)
    TextView groupInfoCodeIv;
    @BindView(R.id.group_info_code_rl)
    RelativeLayout groupInfoCodeRl;
    @BindView(R.id.group_info_notice_tv)
    TextView groupInfoNoticeTv;
    @BindView(R.id.group_info_know_tv)
    TextView groupInfoKnowTv;
    @BindView(R.id.group_info_quit_bt)
    TextView groupInfoQuitBt;
    @BindView(R.id.group_info_rule_rv)
    RecyclerView groupInfoRuleRv;
    Unbinder unbinder;
    @BindView(R.id.group_info_member_ll)
    LinearLayout groupInfoMemberLl;

    private BaseQuickAdapter groupAdapter;
    private List<GroupUser.ListBean> groupList;

    private BaseQuickAdapter ruleAdapter;
    private List<RewardRule> ruleList;
    protected GroupMessageBean.ListBean listBean;
    private boolean isHost=false;
    private GroupInfoPresent groupInfoPresent;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_group_info;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        Bundle bundle=getArguments();
        if (bundle!=null){
            listBean= (GroupMessageBean.ListBean) bundle.getSerializable(AppConstants.CHAT_GROUP_BEAN);
        }
        mActionbar.setTitle("群信息");
        unbinder = ButterKnife.bind(this, contentView);
        groupInfoPresent=new GroupInfoPresent(this);

        groupInfoRuleRv.setLayoutManager(new LinearLayoutManager(activity));
        ruleAdapter = new BaseQuickAdapter<RewardRule, BaseViewHolder>(R.layout.item_group_info_rule, ruleList) {
            @Override
            protected void convert(BaseViewHolder helper, RewardRule item) {
                helper.setText(R.id.item_group_rule_name_tv, item.getRewardName());
                helper.setText(R.id.item_group_rule_num1_tv, item.getRewardValue());
                helper.setText(R.id.item_group_rule_num2_tv, item.getAmountReward());
            }
        };
        groupInfoRuleRv.setAdapter(ruleAdapter);

        initDataAndView();
        groupInfoMemberRv.setLayoutManager(new GridLayoutManager(activity, 4));
        groupAdapter = new BaseQuickAdapter<GroupUser.ListBean, BaseViewHolder>(R.layout.item_group_member, groupList) {
            @Override
            protected void convert(BaseViewHolder helper, GroupUser.ListBean item) {
                String icon=item.getUserIcon();
                if (!TextUtils.isEmpty(icon)){
                    Glide.with(activity).load(icon).into((ImageView) helper.getView(R.id.item_group_member_iv));
                }
                helper.setText(R.id.item_group_member_tv, item.getUserName());
            }
        };
        groupInfoMemberRv.setAdapter(groupAdapter);
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (groupList!=null){
                    Bundle bundle=new Bundle();
                    String id=groupList.get(position).getUserId();
                    bundle.putString(AppConstants.TRANSFER_AWARDRULE,id);
                    bundle.putSerializable(AppConstants.TRANSFER_AWARDRULE,groupList.get(position));
                    bundle.putBoolean(AppConstants.TRANSFER_ISHOST,isHost);
                    FragmentUtils.navigateToNormalActivity(getActivity(),new ClientDetailInfoFragment(),bundle);
                }
            }
        });
    }

    private void initDataAndView() {
        if (listBean!=null){
            groupInfoCodeIv.setText(listBean.getGroupId());
            groupInfoMemberNumTv.setText(""+listBean.getPx());
            groupInfoNameTv.setText(listBean.getGroupName());
            groupInfoNoticeTv.setText(listBean.getNotice());
            groupInfoKnowTv.setText(listBean.getDescription());

            String id= SharePreferenceUtil.getString(activity,AppConstants.USER_ACCID,"无");
            String hostId=listBean.getCreateId();
            if (id.equals(hostId)){
                isHost=true;
                groupInfoQuitBt.setText("解散群聊");
            }else {
                isHost=false;
                groupInfoQuitBt.setText("退出群聊");
            }
            String rules=listBean.getRewardRules();
            Gson gson=new Gson();
            ruleList=gson.fromJson(rules,new TypeToken<List<RewardRule>>() {
            }.getType());
            if (ruleList!=null)
                ruleAdapter.addData(ruleList);
        }
        doGroupUserList();
    }

    private void doGroupUserList() {
        if (listBean!=null)
            groupInfoPresent.getGroupUserList(listBean.getGroupId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.group_info_member_ll,R.id.group_info_quit_bt})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.group_info_code_rl:
//                FragmentUtils.navigateToNormalActivity(activity,new GroupCodeFragment(),null);
                break;
            case R.id.group_info_member_ll://全部成员

                break;
            case R.id.group_info_quit_bt://退出
                if (isHost){

                }else {
                    Toaster.toastShort("请联系群管理者");
                }
                break;
        }
    }
    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshUserlist(GroupUser list) {
        if (list==null){
            groupInfoMemberNumTv.setText("0");
        }else {
            groupList=list.getList();
            if (groupList==null){
                groupInfoMemberNumTv.setText("0");
            }else {
                groupAdapter.addData(groupList);
                groupInfoMemberNumTv.setText("("+groupList.size()+")");
            }
        }
    }
}
