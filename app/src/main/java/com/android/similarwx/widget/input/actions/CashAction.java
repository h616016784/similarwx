package com.android.similarwx.widget.input.actions;

import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.fragment.RechargeFragment;
import com.android.similarwx.fragment.RechargeInputFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.actions.BaseAction;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class CashAction extends BaseAction {
    /**
     * 构造函数
     *
     */
    public CashAction() {
        super(R.drawable.demo_reply_bar_tixian, R.string.chart_cash);
    }

    @Override
    public void onClick() {
        String accid=getAccount();
        NimUIKit.startP2PSession(getActivity(), accid);
    }
}
