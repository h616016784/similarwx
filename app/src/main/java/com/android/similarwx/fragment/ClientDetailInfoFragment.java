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
import com.android.similarwx.utils.FragmentUtils;

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

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_client_detail_info;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("详细信息");
        unbinder = ButterKnife.bind(this, contentView);

       String accid= getArguments().getString(AppConstants.TRANSFER_ACCOUNT);

    }

    @Override
    protected void fetchData() {
        super.fetchData();

    }

    @OnClick(R.id.client_detail_sent_bt)
    public void onViewClicked() {
        Bundle bundle=new Bundle();
        bundle.putString(AppConstants.CHAT_ACCOUNT_ID,"要发信息的accid");
        FragmentUtils.navigateToNormalActivity(activity,new MIFragment(),bundle);
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
