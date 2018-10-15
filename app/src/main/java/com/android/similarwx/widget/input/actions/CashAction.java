package com.android.similarwx.widget.input.actions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.similarwx.R;
import com.android.similarwx.beans.response.RspCashUser;
import com.android.similarwx.fragment.RechargeFragment;
import com.android.similarwx.fragment.RechargeInputFragment;
import com.android.similarwx.inteface.CashViewInterface;
import com.android.similarwx.present.CashPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.actions.BaseAction;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class CashAction extends BaseAction implements CashViewInterface {
    private CashPresent cashPresent;
    /**
     * 构造函数
     *
     */
    public CashAction() {
        super(R.drawable.demo_reply_bar_tixian, R.string.chart_cash);
    }

    @Override
    public void onClick() {
        cashPresent=new CashPresent(this, (AppCompatActivity) getActivity());
        cashPresent.getCashUser();
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshCash(RspCashUser.CashUser cashUser) {
        if (cashUser!=null){
            String accid=cashUser.getAccId();
            NimUIKit.startP2PSession(getActivity(), accid);
        }
    }
}
