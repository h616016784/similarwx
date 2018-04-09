package com.android.similarwx.fragment;

import android.view.View;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

/**
 * Created by Administrator on 2018/4/10.
 */

public class RedDetailFragment extends BaseFragment {
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_red_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.red_detail_title);
        mActionbar.setWholeBackground(R.color.color_red_ccfa3c55);
    }

    @Override
    protected void fetchData() {
        super.fetchData();

    }

    @Override
    protected boolean isNeedFetch() {
        return true;
    }
}
