package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupMemberBean;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.RuleBean;
import com.android.similarwx.inteface.GroupInfoViewInterface;
import com.android.similarwx.present.GroupInfoPresent;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.input.sessions.Extras;
import com.android.similarwx.widget.input.sessions.SessionCustomization;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

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
    ImageView groupInfoCodeIv;
    @BindView(R.id.group_info_code_rl)
    RelativeLayout groupInfoCodeRl;
    @BindView(R.id.group_info_notice_tv)
    TextView groupInfoNoticeTv;
    @BindView(R.id.group_info_know_tv)
    TextView groupInfoKnowTv;
    @BindView(R.id.group_info_rule_rv)
    RecyclerView groupInfoRuleRv;
    Unbinder unbinder;
    @BindView(R.id.group_info_member_ll)
    LinearLayout groupInfoMemberLl;

    private BaseQuickAdapter groupAdapter;
    private List<GroupUser.ListBean> groupList;

    private BaseQuickAdapter ruleAdapter;
    private List<RuleBean> ruleList;
    protected GroupMessageBean.ListBean listBean;

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
        initDataAndView();

        groupInfoMemberRv.setLayoutManager(new GridLayoutManager(activity, 4));
        groupAdapter = new BaseQuickAdapter<GroupUser.ListBean, BaseViewHolder>(R.layout.item_group_member, groupList) {
            @Override
            protected void convert(BaseViewHolder helper, GroupUser.ListBean item) {
                helper.setText(R.id.item_group_member_tv, item.getUserName());
            }
        };
        groupInfoMemberRv.setAdapter(groupAdapter);

        groupInfoRuleRv.setLayoutManager(new LinearLayoutManager(activity));
        ruleAdapter = new BaseQuickAdapter<RuleBean, BaseViewHolder>(R.layout.item_group_info_rule, ruleList) {
            @Override
            protected void convert(BaseViewHolder helper, RuleBean item) {
                helper.setText(R.id.item_group_rule_name_tv, item.getName());
                helper.setText(R.id.item_group_rule_num1_tv, item.getNum1());
                helper.setText(R.id.item_group_rule_num2_tv, item.getNum1());
            }
        };
        groupInfoRuleRv.setAdapter(ruleAdapter);
    }

    private void initDataAndView() {
        groupList = new ArrayList<>();
        ruleList = new ArrayList<>();

        RuleBean ruleBean = new RuleBean();
        ruleBean.setName("奖励");
        ruleBean.setNum1("1.11");
        ruleBean.setNum2("2.22");
        ruleList.add(ruleBean);
        doGroupUserList();

        groupInfoMemberNumTv.setText(""+listBean.getPx());
        groupInfoNameTv.setText(listBean.getGroupName());
        groupInfoNoticeTv.setText(listBean.getNotice());
        groupInfoKnowTv.setText(listBean.getDescription());
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


    @OnClick({R.id.group_info_member_ll,R.id.group_info_code_rl})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.group_info_code_rl:
                FragmentUtils.navigateToNormalActivity(activity,new GroupCodeFragment(),null);
                break;
            case R.id.group_info_member_ll://全部成员

                break;
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshUserlist(GroupUser list) {
        groupList=list.getList();
        groupAdapter.addData(groupList);
    }
}
