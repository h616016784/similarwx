package com.android.similarwx.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.response.RspTransfer;
import com.android.similarwx.inteface.InMoneyViewInterface;
import com.android.similarwx.inteface.RechargeViewInterface;
import com.android.similarwx.present.InputMoneyPresent;
import com.android.similarwx.present.RechargePresent;
import com.android.similarwx.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RechargeFragment extends BaseFragment implements InMoneyViewInterface {
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

    private InputMoneyPresent mPresent;
    String type="1";
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
        mPresent=new InputMoneyPresent(this);
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
                type="3";
                isWeixin=true;
                rechargePayWeixinIv.setVisibility(View.VISIBLE);
                rechargePayAlipayIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.recharge_pay_alipay_ll:
                type="1";
                isWeixin=false;
                rechargePayWeixinIv.setVisibility(View.INVISIBLE);
                rechargePayAlipayIv.setVisibility(View.VISIBLE);
                break;
            case R.id.send_red_bt:
                String money=rechargeMoneyEt.getText().toString();
                if (TextUtils.isEmpty(money)){
                    Toaster.toastShort("充值金额不能为空！");
                    return;
                }else {
                    Bundle bundle=new Bundle();
                    mPresent.inputMoney(type,money);
//                FragmentUtils.navigateToNormalActivity(getActivity(),new PayDetailFragment(),bundle);
                }


                break;
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshInMoney() {

    }
}
