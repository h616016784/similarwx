package com.android.similarwx.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.SetPasswordViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.present.SetPasswordPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Strings.MD5;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/1.
 */

public class SetPayPasswordFragment extends BaseFragment implements SetPasswordViewInterface {
    public static final String LOG_PSD="loginPassword";
    public static final String PAY_PSD="payPassword";
    Unbinder unbinder;
    @BindView(R.id.set_pay_password_et)
    EditText setPayPasswordEt;
    @BindView(R.id.set_pay_password_confirm_et)
    EditText setPayPasswordConfirmEt;
    User muser;
    SetPasswordPresent present;
    private String type="";
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pay_password;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        present=new SetPasswordPresent(this);
        unbinder = ButterKnife.bind(this, contentView);
        type=getArguments().getString(AppConstants.TRANSFER_PASSWORD_TYPE);
        if (!TextUtils.isEmpty(type)){
            if (type.equals(LOG_PSD)){
                mActionbar.setTitle("设置登录密码");
            }else {
                mActionbar.setTitle(R.string.set_pay_password_title);
            }
        }
        mActionbar.setRightText(R.string.register_complete);
        mActionbar.setRightOnClickListener(this);
        muser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity, AppConstants.USER_OBJECT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_layout:
                String password=setPayPasswordEt.getText().toString();
                String confirmPassword=setPayPasswordConfirmEt.getText().toString();
                if (TextUtils.isEmpty(password))
                    Toaster.toastShort("支付密码不能为空！");
                else if (TextUtils.isEmpty(confirmPassword))
                    Toaster.toastShort("确认密码不能为空！");
                else if (!password.equals(confirmPassword))
                    Toaster.toastShort("密码前后不一致!");
                else{
                    if (type.equals(LOG_PSD)){
                        present.setPassword(muser.getId(),"",password);
                    }else {
                        present.setPassword(muser.getId(), password,"");
                    }

                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void refreshSetPassword() {
        String password=setPayPasswordEt.getText().toString();
        if (type.equals(LOG_PSD)){

        }else {
            SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PAYPASSWORD,password);
        }
        activity.finish();
    }

    @Override
    public void showErrorMessage(String err) {

    }
}
