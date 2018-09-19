package com.android.similarwx.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.inteface.PhoneVerifyViewInterface;
import com.android.similarwx.present.PhoneVerifyPresent;
import com.android.similarwx.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/14.
 */

public class PhoneVerifyFragment extends BaseFragment implements PhoneVerifyViewInterface{
    @BindView(R.id.verify_phone_et)
    EditText verifyPhoneEt;
    @BindView(R.id.verify_code_et)
    EditText verifyCodeEt;
    @BindView(R.id.verify_get_code_tv)
    TextView verifyGetCodeTv;
    @BindView(R.id.verify_next)
    Button verifyNextBt;
    Unbinder unbinder;

    private CountDownTimer timer;
    PhoneVerifyPresent present;
    private String type="";
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_phone_verify;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("验证");
        unbinder = ButterKnife.bind(this, contentView);
        present=new PhoneVerifyPresent(this);
        type=getArguments().getString(AppConstants.TRANSFER_PASSWORD_TYPE);
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                verifyGetCodeTv.setText("还剩(" + millisUntilFinished / 1000+")");
                verifyGetCodeTv.setClickable(false);
            }

            @Override
            public void onFinish() {
                verifyGetCodeTv.setText("获取验证码");
                verifyGetCodeTv.setClickable(true);
            }
        };
        verifyPhoneEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        verifyCodeEt.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        timer.cancel();
        timer = null;

    }

    @OnClick({R.id.verify_get_code_tv, R.id.verify_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_get_code_tv:
                String text = verifyPhoneEt.getText().toString();
                if (TextUtils.isEmpty(text))
                    Toaster.toastShort("手机号不能为空");
                else
                    present.getMobileVerifyCode(text);
                break;
            case R.id.verify_next:
                String textCode = verifyCodeEt.getText().toString();
                if (TextUtils.isEmpty(textCode))
                    Toaster.toastShort("验证码不能为空!");
                else {
                    Bundle bundle=new Bundle();
                    bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE,type);
                    bundle.putString(AppConstants.TRANSFER_VERCODE,textCode);
                    FragmentUtils.navigateToNormalActivity(activity,new SetPayPasswordFragment(),bundle);
                }
                break;
        }
    }

    @Override
    public void refreshGettMobileVerifyCode() {
        timer.start();
    }

    @Override
    public void showErrorMessage(String err) {
        timer.cancel();
    }
}
