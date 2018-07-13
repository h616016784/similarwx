package com.android.similarwx.widget.input.actions;

import android.os.Bundle;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.fragment.AdiminInfoFragment;
import com.android.similarwx.fragment.ClientDetailInfoFragment;
import com.android.similarwx.fragment.MIFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.netease.nim.uikit.business.session.actions.BaseAction;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class ContactAdminAction extends BaseAction {
    /**
     * 构造函数
     *
     * @param iconResId 图标 res id
     * @param titleId   图标标题的string res id
     */
    public ContactAdminAction() {
        super(R.drawable.demo_reply_bar_shang, R.string.chart_get_pay);
    }

    @Override
    public void onClick() {
        String id=getAccount();//获取群id号码
        Bundle bundle=new Bundle();
        bundle.putString(AppConstants.TRANSFER_ACCOUNT,id);
        FragmentUtils.navigateToNormalActivity(getActivity(),new AdiminInfoFragment(),bundle);
    }
}
