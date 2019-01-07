package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.GroupRule;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.RewardRule;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.inteface.GroupInfoViewInterface;
import com.android.similarwx.inteface.SendRedViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.GroupInfoPresent;
import com.android.similarwx.present.SendRedPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Util;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.android.similarwx.widget.dialog.CancelDialogBuilder;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;
import com.android.similarwx.widget.toggle.ToggleButton;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.uikit.business.team.activity.AdvancedTeamInfoActivity;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.MenuDialog;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamMessageNotifyTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class GroupInfoFragment extends BaseFragment implements GroupInfoViewInterface, SendRedViewInterface {
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
    @BindView(R.id.group_info_message_set_tg)
    ToggleButton groupInfoSetTg;
    Unbinder unbinder;
    @BindView(R.id.group_info_member_ll)
    LinearLayout groupInfoMemberLl;


    private BaseQuickAdapter groupAdapter;
    private List<GroupUser.ListBean> groupList;

    private BaseQuickAdapter ruleAdapter;
    private List<GroupRule> ruleList;
    private boolean isHost=false;
    private GroupInfoPresent groupInfoPresent;

    public RspGroupInfo.GroupInfo listBean;
    private String accountId;
    private SendRedPresent present;
    private User mUser;

    private MenuDialog teamNotifyDialog;
    private int isClose=0;//0是开   1是关
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_group_info;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        Bundle bundle=getArguments();
        if (bundle!=null){
            accountId=  bundle.getString(AppConstants.TRANSFER_ACCOUNT);
        }
        mActionbar.setTitle("群信息");
        unbinder = ButterKnife.bind(this, contentView);
        isClose=SharePreferenceUtil.getInt(activity,AppConstants.USER_MSSAGE_SET+accountId);
        groupInfoPresent=new GroupInfoPresent(this,activity);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        groupInfoRuleRv.setLayoutManager(mLayoutManager);
        ruleAdapter = new BaseQuickAdapter<GroupRule, BaseViewHolder>(R.layout.item_group_info_rule, ruleList) {
            @Override
            protected void convert(BaseViewHolder helper, GroupRule item) {
                helper.setText(R.id.item_group_rule_name_tv, item.getRewardName());
                helper.setText(R.id.item_group_rule_num1_tv, item.getRewardValue());
                helper.setText(R.id.item_group_rule_num2_tv, item.getAmountReward());
            }
        };
        groupInfoRuleRv.setNestedScrollingEnabled(false);
        groupInfoRuleRv.setAdapter(ruleAdapter);

        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity,AppConstants.USER_OBJECT);
        present=new SendRedPresent(this,activity);


        groupInfoMemberRv.setLayoutManager(new GridLayoutManager(activity, 4));
        groupAdapter = new BaseQuickAdapter<GroupUser.ListBean, BaseViewHolder>(R.layout.item_group_member, groupList) {
            @Override
            protected void convert(BaseViewHolder helper, GroupUser.ListBean item) {
                String icon=item.getUserIcon();
                if (!TextUtils.isEmpty(icon)){
                    NetImageUtil.glideImageNormalWithSize(activity,icon,(ImageView) helper.getView(R.id.item_group_member_iv),120,120);
                }
                helper.setText(R.id.item_group_member_tv, item.getUserName());
            }
        };
        groupInfoMemberRv.setAdapter(groupAdapter);
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (groupList!=null){
                    String id=groupList.get(position).getUserId();
                    if (mUser.getAccId().equals(id)){
                        Toaster.toastShort("不能对自己操作!");
                        return;
                    }
                    Bundle bundle=new Bundle();
                    bundle.putString(AppConstants.TRANSFER_AWARDRULE,id);
                    bundle.putSerializable(AppConstants.TRANSFER_AWARDRULE,groupList.get(position));
                    bundle.putString(AppConstants.TRANSFER_GROUP_USER_ROLE,listBean.getGroupUserRule());
                    FragmentUtils.navigateToNormalActivity(getActivity(),new ClientDetailInfoFragment(),bundle);
                }
            }
        });
        groupInfoCodeRl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String text=groupInfoCodeIv.getText().toString();
                if (!TextUtils.isEmpty(text)){
                    Util.copy(text,activity);
                    Toaster.toastShort("已复制到粘贴板上");
                }
                return true;
            }
        });

        if (isClose==0){
            groupInfoSetTg.setToggleOn();
        }else {
            groupInfoSetTg.setToggleOff();
        }
    }

    private void initDataAndView() {
        if (listBean!=null){
            String groupUserRule= listBean.getGroupUserRule();
            if (!TextUtils.isEmpty(groupUserRule)){
                if (groupUserRule.equals("2") || groupUserRule.equals("3")){//群组或者管理员
                    mActionbar.setRightText("编辑");
                    mActionbar.setRightOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putSerializable(AppConstants.TRANSFER_GROUP_INFO,listBean);
                            bundle.putString(AppConstants.TRANSFER_GROUP_USER_ROLE,groupUserRule);
                            FragmentUtils.navigateToNormalActivity(activity, new AddGroupFragment(), bundle);
                        }
                    });
                }else {
                    mActionbar.setRightText("");
                }
            }

            groupInfoCodeIv.setText(listBean.getGroupId());
            groupInfoMemberNumTv.setText(""+listBean.getPx());
            groupInfoNameTv.setText(listBean.getGroupName());
            groupInfoNoticeTv.setText(listBean.getNotice());
            groupInfoKnowTv.setText(listBean.getRequirement());

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
            ruleList=gson.fromJson(rules,new TypeToken<List<GroupRule>>() {
            }.getType());
            if (ruleList!=null){
                ruleAdapter.getData().clear();
                ruleAdapter.addData(ruleList);
            }

        }

    }

    private void doGroupUserList() {
        if (listBean!=null)
            groupInfoPresent.getGroupUserList(listBean.getGroupId(),10,1);
    }

    @Override
    public void onResume() {
        super.onResume();
        present.getGroupByIdOrGroupId(mUser.getAccId(),accountId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.group_info_member_ll,R.id.group_info_quit_bt,R.id.group_info_message_set_tg})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.group_info_code_rl:
//                FragmentUtils.navigateToNormalActivity(activity,new GroupCodeFragment(),null);
                break;
            case R.id.group_info_message_set_tg://群消息设置
                if (isClose==0){
                    doMessage(TeamMessageNotifyTypeEnum.All,isClose);
                }else if (isClose==1){
                    doMessage(TeamMessageNotifyTypeEnum.Mute,isClose);
                }

                break;
            case R.id.group_info_member_ll://全部成员
                Bundle bundle=new Bundle();
                bundle.putString(AppConstants.TRANSFER_ACCOUNT,listBean.getGroupId());
                bundle.putSerializable(AppConstants.TRANSFER_AWARDRULE_BEAN,listBean);
                FragmentUtils.navigateToNormalActivity(activity, new GroupSearchFragment(), bundle);
                break;
            case R.id.group_info_quit_bt://退出
                if (isHost){
                    showDialog();
                }else {
                    Toaster.toastShort("请联系群管理者");
                }
                break;
        }
    }

    private void showDialog() {
        final CancelDialogBuilder cancel_dialogBuilder = CancelDialogBuilder
                .getInstance(getActivity());

        cancel_dialogBuilder.setTitleText("确定解散该群？");
        cancel_dialogBuilder.setDetermineText("确定");

        cancel_dialogBuilder.isCancelableOnTouchOutside(true)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_dialogBuilder.dismiss();
                    }
                }).setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_dialogBuilder.dismiss();
                        APIYUNXIN.dismissTeam(listBean.getGroupId(), new YCallBack<Void>() {
                            @Override
                            public void callBack(Void aVoid) {
                                Toaster.toastShort("解散群成功,调用本地代码 ！");
                                groupInfoPresent.doDeleteGroup(listBean.getGroupId());
                            }
                        });
                    }
        }).show();
    }
    public void doMessage(TeamMessageNotifyTypeEnum type,int clicked){
        DialogMaker.showProgressDialog(activity, getString(R.string.empty), true);
        NIMClient.getService(TeamService.class).queryTeam(accountId).setCallback(new RequestCallbackWrapper<Team>() {
            @Override
            public void onResult(int code, Team team, Throwable exception) {
                if (code == ResponseCode.RES_SUCCESS) {
                    if (type == null) {
                        return;
                    }
                    NIMClient.getService(TeamService.class).muteTeam(accountId, type).setCallback(new RequestCallback<Void>() {
                        @Override
                        public void onSuccess(Void param) {
                            DialogMaker.dismissProgressDialog();
                            if (clicked==0){
                                isClose=1;
                                groupInfoSetTg.setToggleOff();
                            }else {
                                isClose=0;
                                groupInfoSetTg.setToggleOn();
                            }
                            SharePreferenceUtil.putObject(activity,AppConstants.USER_MSSAGE_SET+accountId,isClose);
                        }

                        @Override
                        public void onFailed(int code) {
                            DialogMaker.dismissProgressDialog();
                            Toaster.toastShort(code);
                        }

                        @Override
                        public void onException(Throwable exception) {
                            DialogMaker.dismissProgressDialog();
                        }
                    });
                } else {
                    DialogMaker.dismissProgressDialog();
                    Toaster.toastShort(code);
                    // 失败，错误码见code
                }
                if (exception != null) {
                    DialogMaker.dismissProgressDialog();
                    // error
                }
            }
        });
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
                List<GroupUser.ListBean> userList=groupAdapter.getData();
                if (userList!=null && userList.size()>0)
                    groupAdapter.getData().clear();
                groupAdapter.addData(groupList);
                groupInfoMemberNumTv.setText("("+list.getCount()+")");
            }
        }
    }

    @Override
    public void refreshDeleteGroup() {
        Toaster.toastShort("群解散成功");
        MainChartrActivity.start(activity);
    }

    @Override
    public void reFreshSendRed(RspGroupInfo.GroupInfo groupInfo) {
        if (groupInfo!=null){
            listBean=groupInfo;
            initDataAndView();
            doGroupUserList();
        }
    }
}
