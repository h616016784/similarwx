package com.android.similarwx.fragment;

import android.view.View;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

/**
 * Created by hanhuailong on 2018/7/31.
 */

public class DealFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_deal;
    }
    @Override
    protected void onInitView(View contentView) {
        mActionbar.setTitle("用户协议");

    }
}
