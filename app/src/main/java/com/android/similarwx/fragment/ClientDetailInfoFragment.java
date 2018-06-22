package com.android.similarwx.fragment;

import android.os.Bundle;
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
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.API;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.dialog.BottomBaseDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
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

public class ClientDetailInfoFragment extends BaseFragment {

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
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_client_detail_info;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
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
            boolean isHost = bundle.getBoolean(AppConstants.TRANSFER_ISHOST);
            if (isHost) {
                clientDetailIdRl.setVisibility(View.VISIBLE);
                clientDetailSetRl.setVisibility(View.VISIBLE);
                clientDetailQuitBt.setVisibility(View.VISIBLE);
            }
        }
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        PopMoreBean bean=new PopMoreBean();
        bean.setId("1");
        bean.setName("设为管理员");
        list.add(bean);
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
                        APIYUNXIN.muteTeamMember(bean.getGroupId(), bean.getUserId(), true, new YCallBack<Void>() {
                            @Override
                            public void callBack(Void aVoid) {
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
                                clientDetailSetTv.setText("是");
                                Toaster.toastShort("该用户已禁言");
                            }
                        });
                    }
                });
                mDialog.show();
                break;
            case R.id.client_detail_sent_bt:
                if (bean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_THREE);
                    bundle.putSerializable(AppConstants.CHAT_TYPE, SessionTypeEnum.P2P);
                    bundle.putString(AppConstants.CHAT_ACCOUNT_ID, bean.getUserId());//
                    bundle.putString(AppConstants.CHAT_ACCOUNT_NAME, bean.getUserName());//
                    FragmentUtils.navigateToNormalActivity(activity, new MIFragmentNew(), bundle);
                }
                break;
            case R.id.client_detail_quit_bt:
                APIYUNXIN.removeMember(bean.getGroupId(), bean.getUserId(), new YCallBack<Void>() {
                    @Override
                    public void callBack(Void aVoid) {
                        Toaster.toastShort("该用户已移除");
                    }
                });
                break;
        }
    }
}
