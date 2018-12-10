package com.android.similarwx.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.SetPasswordViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.present.SetPasswordPresent;
import com.android.similarwx.utils.DigestUtil;
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
    public static final String MOBILE="mobile";
    public static final String MOBILENUMBER="mobileNumber";
    Unbinder unbinder;
    @BindView(R.id.set_pay_password_et)
    EditText setPayPasswordEt;
    @BindView(R.id.set_pay_password_confirm_et)
    EditText setPayPasswordConfirmEt;
    @BindView(R.id.set_pay_password_hint)
    TextView set_pay_password_hint;
    User muser;
    SetPasswordPresent present;
    private String type="";
    private String code="";
    private String mobileNumber="";
    private int mobile=0;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pay_password;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        present=new SetPasswordPresent(this,activity);
        unbinder = ButterKnife.bind(this, contentView);
        type=getArguments().getString(AppConstants.TRANSFER_PASSWORD_TYPE);
        code=getArguments().getString(AppConstants.TRANSFER_VERCODE);
        mobileNumber=getArguments().getString(MOBILENUMBER);
        mobile=getArguments().getInt(MOBILE);
        if (!TextUtils.isEmpty(type)){
            if (type.equals(LOG_PSD)){
                mActionbar.setTitle("设置登录密码");
                set_pay_password_hint.setText("请您设置泡泡社交登录密码。");
            }else {
                mActionbar.setTitle(R.string.set_pay_password_title);
                setPayPasswordEt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                setPayPasswordConfirmEt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
        }
        setPayPasswordEt.addTextChangedListener(textWatcher1);
        setPayPasswordConfirmEt.addTextChangedListener(textWatcher2);
//        setPayPasswordEt.setInputType(InputType.TYPE_CLASS_NUMBER);
//        setPayPasswordConfirmEt.setInputType(InputType.TYPE_CLASS_NUMBER);
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
                    Toaster.toastShort("密码不能为空！");
                else if (TextUtils.isEmpty(confirmPassword))
                    Toaster.toastShort("确认密码不能为空！");
                else if (!password.equals(confirmPassword))
                    Toaster.toastShort("密码前后不一致!");
                else{
                    if (password.length()>6){
                        Toaster.toastShort("密码长度不能大于6");
                        return;
                    }
                    if (confirmPassword.length()>6){
                        Toaster.toastShort("密码长度不能大于6");
                        return;
                    }
                    if (mobile==0){
                        if (type.equals(LOG_PSD)){
                            present.setPassword("",muser.getId(),"",password,"");
                        }else {
                            present.setPassword("",muser.getId(),password,"","");
                        }
                    }else {
                        if (TextUtils.isEmpty(mobileNumber)){
                            if (type.equals(LOG_PSD)){
                                present.setPassword(muser.getMobile(),"", "",password,"");
                            }else {
                                present.setPassword(muser.getMobile(),"", password,"",code);
                            }
                        }else {
                            present.setPassword(mobileNumber,"", "",password,code);
                        }
                    }
                }
                break;
        }
    }
    private TextWatcher textWatcher1=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            if (temp.length() > 6)
            {
                s.delete(temp.length()-1, temp.length());
                Toaster.toastShort("只能是6位数字");
            }
        }
    };
    private TextWatcher textWatcher2=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            if (temp.length() > 6)
            {
                s.delete(temp.length()-1, temp.length());
                Toaster.toastShort("只能是6位数字");
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    @Override
    public void refreshSetPassword(User user) {
        if (user!=null){
            SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(),AppConstants.USER_OBJECT,user);
            String password=setPayPasswordEt.getText().toString();
            if (type.equals(LOG_PSD)){
                SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_LOGIN_PASSWORD,password);
            }else {
                password=DigestUtil.sha1(password);
                SharePreferenceUtil.putObject(AppContext.getContext(),AppConstants.USER_PAYPASSWORD,password);
                muser.setPaymentPasswd(password);
                SharePreferenceUtil.saveSerializableObjectDefault(AppContext.getContext(),AppConstants.USER_OBJECT,muser);
            }
            activity.finish();
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }
}
