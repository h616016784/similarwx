package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/1.
 */

public class SetPayPasswordFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.set_pay_password_et)
    EditText setPayPasswordEt;
    @BindView(R.id.set_pay_password_confirm_et)
    EditText setPayPasswordConfirmEt;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pay_password;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        unbinder = ButterKnife.bind(this, contentView);
        mActionbar.setTitle(R.string.set_pay_password_title);
        mActionbar.setRightText(R.string.register_complete);
        mActionbar.setRightOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_layout:
                Toaster.toastShort("click it");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
