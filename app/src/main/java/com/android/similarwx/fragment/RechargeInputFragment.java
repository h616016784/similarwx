package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/13.
 */

public class RechargeInputFragment extends BaseFragment {
    @BindView(R.id.recharge_input_iv)
    ImageView rechargeInputIv;
    @BindView(R.id.recharge_input_tv)
    TextView rechargeInputTv;
    @BindView(R.id.recharge_input_et)
    EditText rechargeInputEt;
    @BindView(R.id.transfer)
    Button transfer;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recharge_input;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("转账");
        unbinder = ButterKnife.bind(this, contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.transfer)
    public void onViewClicked() {
    }
}
