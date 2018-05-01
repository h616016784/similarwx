package com.android.similarwx.widget.input.actions;

import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.fragment.RechargeFragment;
import com.android.similarwx.fragment.ServiceFragment;
import com.android.similarwx.utils.FragmentUtils;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class ServiceAction extends BaseAction {
    /**
     * 构造函数
     *
     * @param iconResId 图标 res id
     * @param titleId   图标标题的string res id
     */
    public ServiceAction() {
        super(R.drawable.demo_reply_bar_kehu, R.string.chart_service);
    }

    @Override
    public void onClick() {
        Bundle bundle=new Bundle();
        FragmentUtils.navigateToNormalActivity(getActivity(),new ServiceFragment(),bundle);
    }
}
