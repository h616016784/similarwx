package com.android.similarwx.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.BaseItemView;
import com.android.similarwx.widget.ItemView;
import com.android.similarwx.widget.dialog.AlertDialogFragment;
import com.android.similarwx.widget.dialog.EditDialogBuilder;
import com.android.similarwx.widget.dialog.EditDialogFragment;
import com.android.similarwx.widget.dialog.EditDialogSimple;

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

    EditDialogSimple editDialogSimple;
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
        if (editDialogSimple!=null){
            editDialogSimple.dimiss();
            editDialogSimple=null;
        }
    }

    @OnClick({R.id.my_set_password_login_iv, R.id.my_set_password_pay_iv, R.id.my_set_password_find_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_set_password_login_iv:
                showEditDialog(0);
                break;
            case R.id.my_set_password_pay_iv:
                showEditDialog(1);
                break;
            case R.id.my_set_password_find_iv:
                break;
        }
    }

    private void showEditDialog(int flag) {
        if(editDialogSimple==null)
            editDialogSimple=new EditDialogSimple(activity,"");
        if(flag==0){
            editDialogSimple.setTitle("请输入新的登录密码");
            editDialogSimple.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                @Override
                public void onClickListener(String text) {

                }
            });
        } else{
            editDialogSimple.setTitle(AppContext.getResources().getString(R.string.set_password_message));
            editDialogSimple.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                @Override
                public void onClickListener(String text) {
                    FragmentUtils.navigateToNormalActivity(activity,new SetPayPasswordFragment(),null);
                }
            });
        }

        editDialogSimple.show();
    }
}