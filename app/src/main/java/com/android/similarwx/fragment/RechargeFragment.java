package com.android.similarwx.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.beans.response.RspInMoney;
import com.android.similarwx.beans.response.RspTransfer;
import com.android.similarwx.inteface.InMoneyViewInterface;
import com.android.similarwx.inteface.RechargeViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.InputMoneyPresent;
import com.android.similarwx.present.RechargePresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.dialog.BottomBaseDialog;
import com.netease.nimlib.sdk.team.model.TeamMember;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RechargeFragment extends BaseFragment implements InMoneyViewInterface {
    @BindView(R.id.recharge_money_et)
    TextView rechargeMoneyEt;
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
    private List<PopMoreBean> list;
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
        mPresent=new InputMoneyPresent(this,activity);
        iniList();
    }

    private void iniList() {
        list=new ArrayList<>();
        PopMoreBean beanPop=new PopMoreBean();
        beanPop.setId("1");
        beanPop.setName("20");
        list.add(beanPop);

        PopMoreBean beanPop2=new PopMoreBean();
        beanPop2.setId("2");
        beanPop2.setName("50");
        list.add(beanPop2);

        PopMoreBean beanPop3=new PopMoreBean();
        beanPop3.setId("3");
        beanPop3.setName("100");
        list.add(beanPop3);

        PopMoreBean beanPop4=new PopMoreBean();
        beanPop4.setId("4");
        beanPop4.setName("200");
        list.add(beanPop4);

        PopMoreBean beanPop5=new PopMoreBean();
        beanPop5.setId("5");
        beanPop5.setName("300");
        list.add(beanPop5);

        PopMoreBean beanPop6=new PopMoreBean();
        beanPop6.setId("6");
        beanPop6.setName("500");
        list.add(beanPop6);

        PopMoreBean beanPop7=new PopMoreBean();
        beanPop7.setId("7");
        beanPop7.setName("1000");
        list.add(beanPop7);

        PopMoreBean beanPop8=new PopMoreBean();
        beanPop8.setId("8");
        beanPop8.setName("2000");
        list.add(beanPop8);

        PopMoreBean beanPop9=new PopMoreBean();
        beanPop9.setId("9");
        beanPop9.setName("3000");
        list.add(beanPop9);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.recharge_pay_weixin_ll, R.id.recharge_pay_alipay_ll, R.id.send_red_bt,R.id.recharge_money_et})
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
            case R.id.recharge_money_et:
                showInputMoney();
                break;
            case R.id.send_red_bt:
                String money=rechargeMoneyEt.getText().toString();
                if (TextUtils.isEmpty(money)){
                    Toaster.toastShort("充值金额不能为空！");
                    return;
                }else {
                    mPresent.inputMoney(type,money);
                }
                break;
        }
    }

    private void showInputMoney() {
        BottomBaseDialog dialog=new BottomBaseDialog(activity);
        dialog.setTitle("金额选择");
        dialog.setList(list);
        dialog.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PopMoreBean bean= list.get(position);
                rechargeMoneyEt.setText(bean.getName());
            }
        });
        dialog.show();
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshInMoney(RspInMoney.InMoneyBean bean) {
        if (bean!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable(PayDetailFragment.INMONEY,bean);
            bundle.putString(PayDetailFragment.TYPE,type);
            FragmentUtils.navigateToNormalActivity(getActivity(),new PayDetailFragment(),bundle);
        }
    }
}
