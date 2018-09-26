package com.android.similarwx.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspConfig;
import com.android.similarwx.inteface.ClientDetailInfoViewInterface;
import com.android.similarwx.inteface.GroupInfoViewInterface;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.API;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.ClientDetailInfoPresent;
import com.android.similarwx.present.GroupInfoPresent;
import com.android.similarwx.present.SysNoticePresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.android.similarwx.widget.dialog.BottomBaseDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ClientDetailInfoFragment extends BaseFragment implements ClientDetailInfoViewInterface, GroupInfoViewInterface {

    @BindView(R.id.client_detail_account_iv)
    ImageView clientDetailAccountIv;
    @BindView(R.id.client_detail_name_tv)
    TextView clientDetailNameTv;
    @BindView(R.id.client_detail_account_tv)
    TextView clientDetailAccountTv;
    @BindView(R.id.client_detail_id_tv)
    TextView clientDetailIdTv;
    @BindView(R.id.client_detail_id_rl)
    RelativeLayout clientDetailIdRl;
    @BindView(R.id.client_detail_set_tv)
    TextView clientDetailSetTv;
    @BindView(R.id.client_detail_set_rl)
    RelativeLayout clientDetailSetRl;
    @BindView(R.id.client_detail_sent_bt)
    Button clientDetailSentBt;
    @BindView(R.id.client_detail_quit_bt)
    Button clientDetailQuitBt;
    Unbinder unbinder;
    private GroupUser.ListBean bean;
    private List<PopMoreBean> list;

    ClientDetailInfoPresent mPresent;
    String rule;

    private GroupInfoPresent groupInfoPresent;
    String toAccid;
    String fromAccid;
    String teamId;
    int flag=0;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_client_detail_info;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mPresent=new ClientDetailInfoPresent(this);
        mActionbar.setTitle("群名片");
        unbinder = ButterKnife.bind(this, contentView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String groupUserType = bundle.getString(AppConstants.TRANSFER_GROUP_USER_ROLE);//发起用户的群角色
            bean = (GroupUser.ListBean) bundle.getSerializable(AppConstants.TRANSFER_AWARDRULE);
            if (bean != null) {//从群信息界面跳转过来的
                initUserView(groupUserType,bean);
            }else{//从聊天界面跳转过来的
                toAccid= bundle.getString(AppConstants.TRANSFER_ACCOUNT);
                fromAccid = SharePreferenceUtil.getString(activity,AppConstants.USER_ACCID,"");
                teamId=bundle.getString(AppConstants.TRANSFER_TEAMID);
                groupInfoPresent=new GroupInfoPresent(this);
                groupInfoPresent.getGroupUser(teamId,toAccid);
            }
        }

    }

    /**
     *
     * @param groupUserType  操作者的角色
     * @param bean      被操作人的信息
     */
    private void initUserView(String groupUserType, GroupUser.ListBean bean) {
        clientDetailNameTv.setText(bean.getUserName());
        clientDetailAccountTv.setText("泡泡ID: "+bean.getId());
        rule=bean.getGroupUserRule();
        if (rule.equals("1")){//
            clientDetailIdTv.setText("普通用户");
        }else if (rule.equals("2")){
            clientDetailIdTv.setText("管理员");
        }else if (rule.equals("3")){
            clientDetailIdTv.setText("群主");
        }else if (rule.equals("4")){
            clientDetailIdTv.setText("系统用户");
        }else if (rule.equals("1")){
            clientDetailIdTv.setText("普通用户");
        }
        if (!TextUtils.isEmpty(groupUserType)){
            if(groupUserType.equals("2")){
                if (rule.equals("1")){//普通用户
                    clientDetailIdRl.setVisibility(View.VISIBLE);
                    clientDetailSetRl.setVisibility(View.VISIBLE);
                    clientDetailQuitBt.setVisibility(View.VISIBLE);
                }
            } if(groupUserType.equals("3")){
                if (rule.equals("1") || rule.equals("2")){//普通用户
                    clientDetailIdRl.setVisibility(View.VISIBLE);
                    clientDetailSetRl.setVisibility(View.VISIBLE);
                    clientDetailQuitBt.setVisibility(View.VISIBLE);
                }
            }
        }
        //设置用户是否禁言
        String state=bean.getUserStatus();
        if (!TextUtils.isEmpty(state)){
            if (state.equals("3")){
            clientDetailSetTv.setText("禁言");
        }
    }

        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        //获取用户信息
        mPresent.getUserInfoByParams("",bean.getUserId());
    }

    @Override
    protected void fetchData() {
        super.fetchData();
    }

    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.client_detail_id_rl, R.id.client_detail_set_rl, R.id.client_detail_sent_bt, R.id.client_detail_quit_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.client_detail_id_rl:
                doAdiminOpterator();
                break;
            case R.id.client_detail_set_rl:
                EasyAlertDialog mDialog= EasyAlertDialogHelper.createOkCancelDiolag(activity,bean.getUserName(),"是否对该成员禁言?","是","否",true, new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {
                        mPresent.doUpdateGroupUserStatus(bean.getGroupId(), bean.getUserId(),"1");
                        clientDetailSetTv.setText("否");
//                        APIYUNXIN.muteTeamMember(bean.getGroupId(), bean.getUserId(), false, new YCallBack<Void>() {
//                            @Override
//                            public void callBack(Void aVoid) {
//
//                            }
//                        });
                    }
                    @Override
                    public void doOkAction() {
                        mPresent.doUpdateGroupUserStatus(bean.getGroupId(), bean.getUserId(),"3");
                        clientDetailSetTv.setText("是");
//                        APIYUNXIN.muteTeamMember(bean.getGroupId(), bean.getUserId(), true, new YCallBack<Void>() {
//                            @Override
//                            public void callBack(Void aVoid) {
//
//                            }
//                        });
                    }
                });
                mDialog.show();
                break;
            case R.id.client_detail_sent_bt:
                NimUIKit.startP2PSession(activity, bean.getUserId());
//                if (bean != null) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_THREE);
//                    bundle.putSerializable(AppConstants.CHAT_TYPE, SessionTypeEnum.P2P);
//                    bundle.putString(AppConstants.CHAT_ACCOUNT_ID, bean.getUserId());//
//                    bundle.putString(AppConstants.CHAT_ACCOUNT_NAME, bean.getUserName());//
//                    FragmentUtils.navigateToNormalActivity(activity, new MIFragmentNew(), bundle);
//                }
                break;
            case R.id.client_detail_quit_bt:
                String role=bean.getGroupUserRule();
                if (role.equals("3") || role.equals("4")){
                    Toaster.toastShort("您的权限不足");
                    return;
                }
                APIYUNXIN.removeMember(bean.getGroupId(), bean.getUserId(), new YCallBack<Void>() {
                    @Override
                    public void callBack(Void aVoid) {
                        Toaster.toastShort("该用户已移除");
                        mPresent.doDeleteGroupUser(bean.getGroupId(), bean.getUserId());
                    }
                });
                break;
        }
    }

    /**
     * 进行管理员和取消管理员的操作
     */
    private void doAdiminOpterator() {
        if (TextUtils.isEmpty(rule)){
            clientDetailIdTv.setText("普通用户");
            list.clear();
            PopMoreBean beanPop=new PopMoreBean();
            beanPop.setId("1");
            beanPop.setName("设为管理员");
            list.add(beanPop);

            BottomBaseDialog dialog=new BottomBaseDialog(activity);
            dialog.setTitle("管理员操作");
            dialog.setList(list);
            dialog.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                        PopMoreBean bean= list.get(position);
                    List<String> myList=new ArrayList<>();
                    myList.add(bean.getUserId());
                    APIYUNXIN.addManagers(bean.getGroupId(), myList, new YCallBack<List<TeamMember>>() {
                        @Override
                        public void callBack(List<TeamMember> teamMembers) {
//                            Toaster.toastShort("已提升为管理员");
                            mPresent.doUpdateGroupUser(bean.getGroupId(),bean.getUserId(),"2");
                        }
                    });
                }
            });
            dialog.show();
        }else {
            if (rule.equals("1")){
                clientDetailIdTv.setText("普通用户");
                list.clear();
                PopMoreBean beanPop=new PopMoreBean();
                beanPop.setId("1");
                beanPop.setName("设为管理员");
                list.add(beanPop);

                BottomBaseDialog dialog=new BottomBaseDialog(activity);
                dialog.setTitle("管理员操作");
                dialog.setList(list);
                dialog.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        PopMoreBean bean= list.get(position);
                        List<String> myList=new ArrayList<>();
                        myList.add(bean.getUserId());
                        APIYUNXIN.addManagers(bean.getGroupId(), myList, new YCallBack<List<TeamMember>>() {
                            @Override
                            public void callBack(List<TeamMember> teamMembers) {
//                                Toaster.toastShort("已提升为管理员");
                                mPresent.doUpdateGroupUser(bean.getGroupId(),bean.getUserId(),"2");
                            }
                        });
                    }
                });
                dialog.show();
            }else if (rule.equals("2")){
                clientDetailIdTv.setText("管理员");
                list.clear();
                PopMoreBean beanPop=new PopMoreBean();
                beanPop.setId("-1");
                beanPop.setName("取消管理员");
                list.add(beanPop);

                BottomBaseDialog dialog=new BottomBaseDialog(activity);
                dialog.setTitle("管理员操作");
                dialog.setList(list);
                dialog.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        PopMoreBean bean= list.get(position);
                        List<String> myList=new ArrayList<>();
                        myList.add(bean.getUserId());
                        APIYUNXIN.removeManagers(bean.getGroupId(), myList, new YCallBack<List<TeamMember>>() {
                            @Override
                            public void callBack(List<TeamMember> teamMembers) {
//                                Toaster.toastShort("取消管理员");
                                mPresent.doUpdateGroupUser(bean.getGroupId(),bean.getUserId(),"1");
                            }
                        });
                    }
                });
                dialog.show();
            }
        }
    }


    private void sendEmptyAudioMessage(String userStatus) {
        // 该帐号为示例，请先注册
        String account = bean.getGroupId();
        SessionTypeEnum sessionType = SessionTypeEnum.Team;
        Map<String, Object> content = new HashMap<>(1);
        content.put("accId", bean.getUserId());
        content.put("status", userStatus);
        content.put("redPacTipMessageType","emptyTipsMessage");
// 创建tip消息，teamId需要开发者已经存在的team的teamId
        IMMessage msg = MessageBuilder.createTipMessage(account, sessionType);
        msg.setRemoteExtension(content);
// 自定义消息配置选项
        CustomMessageConfig config = new CustomMessageConfig();
// 消息不计入未读
        config.enableUnreadCount = false;
        config.enablePush=false;
        msg.setConfig(config);
// 消息发送状态设置为success
        msg.setStatus(MsgStatusEnum.success);
        // 发送给对方
        NIMClient.getService(MsgService.class).sendMessage(msg, false);
    }
    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshUserInfo(User user) {
        if (user!=null){
            String icon=user.getIcon();
            if (!TextUtils.isEmpty(icon)){
                NetImageUtil.glideImageNormal(activity,icon,clientDetailAccountIv);
            }
        }
    }
    //设置管理员成功的回调
    @Override
    public void refreshUpdateUser() {
        Toaster.toastShort("操作成功！");
    }

    @Override
    public void refreshUpdateUserStatus(String userStatus) {
        if (userStatus.equals("3")){
            Toaster.toastShort("禁言成功！");

        }else if (userStatus.equals("1")){
            Toaster.toastShort("解禁成功！");
        }
        sendEmptyAudioMessage(userStatus);
    }


    //剔除成员成功的回调
    @Override
    public void refreshDeleteUser() {
        activity.finish();
    }

    /**
     * 通过id获取用户在群的权限等纤细
     * @param groupUser
     */
    @Override
    public void refreshUserlist(GroupUser groupUser) {
        if (groupUser!=null){
            if (flag==0){
                bean=groupUser.getList().get(0);
                groupInfoPresent.getGroupUser(teamId,fromAccid);
                flag=1;
            }else if (flag==1){//第二次的网络请求了
                initUserView(groupUser.getList().get(0).getGroupUserRule(),bean);
            }

        }
    }

    @Override
    public void refreshDeleteGroup() {

    }
}
