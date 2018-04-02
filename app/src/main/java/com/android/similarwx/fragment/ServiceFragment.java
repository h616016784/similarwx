package com.android.similarwx.fragment;

import android.view.View;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

/**
 * Created by hanhuailong on 2018/4/2.
 */

public class ServiceFragment extends BaseFragment {
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_service;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.service_title);
    }
}
