package com.android.similarwx.fragment;

import android.view.View;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

/**
 * Created by Administrator on 2018/4/3.
 */

public class GroupCodeFragment extends BaseFragment {
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_explain;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("群二维码");
    }
}
