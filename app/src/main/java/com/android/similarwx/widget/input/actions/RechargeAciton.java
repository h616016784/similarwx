package com.android.similarwx.widget.input.actions;

import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.fragment.MyDetailFragment;
import com.android.similarwx.fragment.RechargeFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.netease.nim.uikit.business.session.actions.BaseAction;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class RechargeAciton extends BaseAction {
    /**
     * 构造函数
     *
     * @param iconResId 图标 res id
     * @param titleId   图标标题的string res id
     */
    public RechargeAciton() {
        super(R.drawable.demo_reply_bar_chongzhi, R.string.chart_recharge);
    }

    @Override
    public void onClick() {
        Bundle bundle=new Bundle();
        FragmentUtils.navigateToNormalActivity(getActivity(),new RechargeFragment(),bundle);
    }
}
