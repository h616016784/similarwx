package com.android.similarwx.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.widget.BaseItemView;
import com.android.similarwx.widget.ItemView;
import com.android.similarwx.widget.dialog.EditDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SetPasswordFragment extends BaseFragment {
    @BindView(R.id.my_set_password_login_iv)
    BaseItemView mySetPasswordLoginIv;
    @BindView(R.id.my_set_password_pay_iv)
    BaseItemView mySetPasswordPayIv;
    @BindView(R.id.my_set_password_find_iv)
    BaseItemView mySetPasswordFindIv;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_set_password;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.my_base_title);
        unbinder = ButterKnife.bind(this, contentView);
        init();
    }

    private void init() {
        mySetPasswordLoginIv.setNameText(R.string.set_password_login);
        mySetPasswordPayIv.setNameText(R.string.set_password_pay);
        mySetPasswordFindIv.setNameText(R.string.set_password_find);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_set_password_login_iv, R.id.my_set_password_pay_iv, R.id.my_set_password_find_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_set_password_login_iv:
                showEditDialog();
                break;
            case R.id.my_set_password_pay_iv:
                break;
            case R.id.my_set_password_find_iv:
                break;
        }
    }

    private void showEditDialog() {
        BaseDialog dialog=new EditDialogBuilder(activity).setButtonText(R.string.confirm,R.string.cancel)
                .setMessage(R.string.set_password_message)
                .setConfirmButton(new EditDialogBuilder.ButtonClicker() {
                    @Override
                    public void onButtonClick(String str) {

                    }
                }).create();
        dialog.show();
    }
}
