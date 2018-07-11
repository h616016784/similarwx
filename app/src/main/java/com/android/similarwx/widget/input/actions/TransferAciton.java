package com.android.similarwx.widget.input.actions;

import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.fragment.RechargeInputFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.netease.nim.uikit.business.session.actions.BaseAction;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class TransferAciton extends BaseAction {
    /**
     * 构造函数
     *
     */
    public TransferAciton() {
        super(R.drawable.demo_reply_bar_zhuan, R.string.chart_transfer);
    }

    @Override
    public void onClick() {
        String account=getAccount();
        Bundle bundle=new Bundle();
        bundle.putString(AppConstants.TRANSFER_ACCOUNT,account);
        FragmentUtils.navigateToNormalActivity(getActivity(),new RechargeInputFragment(),bundle);
    }
}
