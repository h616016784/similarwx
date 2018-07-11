package com.android.similarwx.widget.input.actions;

import android.app.Fragment;
import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.fragment.MIFragmentNew;
import com.android.similarwx.fragment.MyDetailFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.netease.nim.uikit.business.session.actions.BaseAction;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class BillAciton extends BaseAction {
    private Fragment fromFragment;
    /**
     * 构造函数
     *
     */
    public BillAciton() {
        super(R.drawable.demo_reply_bar_zhangdan, R.string.chart_bill);
    }
    public BillAciton(Fragment fromFragment) {
        super(R.drawable.demo_reply_bar_hb, R.string.chart_bill);
        this.fromFragment=fromFragment;
    }

    @Override
    public void onClick() {
        Bundle bundle=new Bundle();
//        bundle.putSerializable(AppConstants.CHAT_GROUP_BEAN,((MIFragmentNew)fromFragment).listBean);
        FragmentUtils.navigateToNormalActivity(getActivity(),new MyDetailFragment(),bundle);
    }
}
