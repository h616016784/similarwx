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

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.ClientDetailInfoViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.API;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.ClientDetailInfoPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.widget.dialog.BottomBaseDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ClientDetailInfoFragment extends BaseFragment implements ClientDetailInfoViewInterface {

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
            String accid = bundle.getString(AppConstants.TRANSFER_ACCOUNT);
            bean = (GroupUser.ListBean) bundle.getSerializable(AppConstants.TRANSFER_AWARDRULE);
            if (bean != null) {
                clientDetailNameTv.setText(bean.getUserName());
                clientDetailAccountTv.setText(bean.getUserId());
            }
            String groupUserType = bundle.getString(AppConstants.TRANSFER_GROUP_USER_ROLE);
            String rule=bean.getGroupUserRule();
            if (TextUtils.isEmpty(groupUserType)){
                clientDetailIdTv.setText("普通用户");
            }else {
                if(groupUserType.equals("1")){
                    clientDetailIdTv.setText("普通用户");
                }else{
                    clientDetailIdRl.setVisibility(View.VISIBLE);
                    clientDetailSetRl.setVisibility(View.VISIBLE);
                    clientDetailQuitBt.setVisibility(View.VISIBLE);
                    if (rule.equals("2")){
                        clientDetailIdTv.setText("管理员");
                    }else if (rule.equals("3")){
                        clientDetailIdTv.setText("群主");
                    }else if (rule.equals("4")){
                        clientDetailIdTv.setText("系统用户");
                    }else if (rule.equals("1")){
                        clientDetailIdTv.setText("普通用户");
                    }

                }
            }
        }
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        PopMoreBean beanPop=new PopMoreBean();
        beanPop.setId("1");
        beanPop.setName("设为管理员");
        list.add(beanPop);

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
                                Toaster.toastShort("已提升为管理员");
                                mPresent.doUpdateGroupUser(bean.getGroupId(),bean.getUserId(),"2");
                            }
                        });
                    }
                });
                dialog.show();
                break;
            case R.id.client_detail_set_rl:
                EasyAlertDialog mDialog= EasyAlertDialogHelper.createOkCancelDiolag(activity,bean.getUserName(),"是否对该成员禁言?","是","否",true, new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {
                        APIYUNXIN.muteTeamMember(bean.getGroupId(), bean.getUserId(), false, new YCallBack<Void>() {
                            @Override
                            public void callBack(Void aVoid) {
                                mPresent.doUpdateGroupUserStatus(bean.getGroupId(), bean.getUserId(),"1");
                                clientDetailSetTv.setText("否");
                                Toaster.toastShort("该用户已解禁");
                            }
                        });
                    }
                    @Override
                    public void doOkAction() {
                        APIYUNXIN.muteTeamMember(bean.getGroupId(), bean.getUserId(), true, new YCallBack<Void>() {
                            @Override
                            public void callBack(Void aVoid) {
                                mPresent.doUpdateGroupUserStatus(bean.getGroupId(), bean.getUserId(),"3");
                                clientDetailSetTv.setText("是");
                                Toaster.toastShort("该用户已禁言");
                            }
                        });
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

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshUserInfo(User user) {
        if (user!=null){
            String icon=user.getIcon();
            if (!TextUtils.isEmpty(icon)){
                Glide.with(activity)
                        .load(icon)
//                        .override(120,120)
//                        .transform(new CircleCrop(activity))
//                        .placeholder(R.drawable.rp_avatar)
//                        .error(R.drawable.rp_avatar)
                        .into(clientDetailAccountIv);
            }
        }
    }
    //设置管理员成功的回调
    @Override
    public void refreshUpdateUser() {
        Toaster.toastShort("本地设置为管理员成功");
    }

    @Override
    public void refreshUpdateUserStatus() {

    }

    //剔除成员成功的回调
    @Override
    public void refreshDeleteUser() {
        activity.finish();
    }
}
