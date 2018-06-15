package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.RewardRule;
import com.android.similarwx.utils.FragmentUtils;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

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
    @BindView(R.id.client_detail_sent_bt)
    Button clientDetailSentBt;
    Unbinder unbinder;

    private GroupUser.ListBean bean;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_client_detail_info;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("详细信息");
        unbinder = ButterKnife.bind(this, contentView);
        Bundle bundle=getArguments();
        if (bundle!=null){
            String accid= bundle.getString(AppConstants.TRANSFER_ACCOUNT);
            bean = (GroupUser.ListBean) bundle.getSerializable(AppConstants.TRANSFER_AWARDRULE);
           if (bean!=null){
               clientDetailNameTv.setText(bean.getUserName());
               clientDetailAccountTv.setText(bean.getUserId());
           }
        }
    }

    @Override
    protected void fetchData() {
        super.fetchData();

    }

    @OnClick(R.id.client_detail_sent_bt)
    public void onViewClicked() {
        if (bean!=null){
            Bundle bundle=new Bundle();
            bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_THREE);
            bundle.putSerializable(AppConstants.CHAT_TYPE, SessionTypeEnum.P2P);
            bundle.putString(AppConstants.CHAT_ACCOUNT_ID, bean.getUserId());//
            bundle.putString(AppConstants.CHAT_ACCOUNT_NAME, bean.getUserName());//
            FragmentUtils.navigateToNormalActivity(activity,new MIFragmentNew(),bundle);
        }
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

}
