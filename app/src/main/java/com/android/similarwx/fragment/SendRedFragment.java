package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/12.
 */

public class SendRedFragment extends BaseFragment {
    @BindView(R.id.send_red_count_et)
    EditText sendRedCountEt;
    @BindView(R.id.send_red_sum_et)
    EditText sendRedSumEt;
    @BindView(R.id.send_red_lei_et)
    EditText sendRedLeiEt;
    @BindView(R.id.send_red_bt)
    Button sendRedBt;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_send_red;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("发红包");
        unbinder = ButterKnife.bind(this, contentView);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.send_red_bt)
    public void onViewClicked() {
    }
}
