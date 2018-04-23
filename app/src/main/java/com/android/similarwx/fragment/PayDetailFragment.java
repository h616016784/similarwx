package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class PayDetailFragment extends BaseFragment {
    @BindView(R.id.pay_detail_money_tv)
    TextView payDetailMoneyTv;
    @BindView(R.id.pay_detail_iv)
    ImageView payDetailIv;
    @BindView(R.id.pay_detail_share_bt)
    Button payDetailShareBt;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pay_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.pay_detail_title);
        unbinder = ButterKnife.bind(this, contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.pay_detail_iv, R.id.pay_detail_share_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_detail_iv:
                break;
            case R.id.pay_detail_share_bt:
                break;
        }
    }
}