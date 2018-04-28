package com.android.similarwx.widget.input.actions;

import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.fragment.MyDetailFragment;
import com.android.similarwx.utils.FragmentUtils;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class BillAciton extends BaseAction {
    /**
     * 构造函数
     *
     */
    public BillAciton() {
        super(R.drawable.demo_reply_bar_zhangdan, R.string.chart_bill);
    }

    @Override
    public void onClick() {
        Bundle bundle=new Bundle();
        FragmentUtils.navigateToNormalActivity(getActivity(),new MyDetailFragment(),bundle);
    }
}
