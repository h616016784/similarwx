package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/13.
 */

public class AdiminInfoFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.admin_to_code_wb)
    WebView adminToCodeWb;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_admin_info;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("联系信息");
        unbinder = ButterKnife.bind(this, contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
