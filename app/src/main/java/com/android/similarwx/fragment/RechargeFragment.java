package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RechargeFragment extends BaseFragment {
    @BindView(R.id.recharge_money_et)
    EditText rechargeMoneyEt;
    @BindView(R.id.recharge_pay_weixin_iv)
    ImageView rechargePayWeixinIv;
    @BindView(R.id.recharge_pay_weixin_ll)
    LinearLayout rechargePayWeixinLl;
    @BindView(R.id.recharge_pay_alipay_iv)
    ImageView rechargePayAlipayIv;
    @BindView(R.id.recharge_pay_alipay_ll)
    LinearLayout rechargePayAlipayLl;
    @BindView(R.id.send_red_bt)
    Button sendRedBt;
    Unbinder unbinder;

    private boolean isWeixin=true;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("充值");
        unbinder = ButterKnife.bind(this, contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.recharge_pay_weixin_ll, R.id.recharge_pay_alipay_ll, R.id.send_red_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recharge_pay_weixin_ll:
                isWeixin=true;
                rechargePayWeixinIv.setVisibility(View.VISIBLE);
                rechargePayAlipayIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.recharge_pay_alipay_ll:
                isWeixin=false;
                rechargePayWeixinIv.setVisibility(View.INVISIBLE);
                rechargePayAlipayIv.setVisibility(View.VISIBLE);
                break;
            case R.id.send_red_bt:
                Bundle bundle=new Bundle();
                if (isWeixin){

                }else {

                }
                FragmentUtils.navigateToNormalActivity(getActivity(),new PayDetailFragment(),bundle);
                break;
        }
    }
}
