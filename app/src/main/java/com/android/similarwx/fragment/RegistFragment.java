package com.android.similarwx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.PhoneVerifyViewInterface;
import com.android.similarwx.inteface.RegisterViewInterface;
import com.android.similarwx.present.PhoneVerifyPresent;
import com.android.similarwx.present.RegisterPresent;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/31.
 */

public class RegistFragment extends BaseFragment implements RegisterViewInterface, PhoneVerifyViewInterface {

    @BindView(R.id.login_account)
    TextView loginAccount;
    @BindView(R.id.login_account_et)
    EditText loginAccountEt;
    @BindView(R.id.login_weixn_account)
    TextView loginWeixnAccount;
    @BindView(R.id.login_weixn_account_et)
    EditText loginWeixnAccountEt;
    @BindView(R.id.login_email)
    TextView loginEmail;
    @BindView(R.id.login_email_et)
    EditText loginEmailEt;
    @BindView(R.id.login_phone)
    TextView loginPhone;
    @BindView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @BindView(R.id.login_nick_et)
    EditText loginNickEt;
    @BindView(R.id.register_code)
    TextView registerCode;
    @BindView(R.id.register_code_et)
    EditText registerCodeEt;
    @BindView(R.id.register_get_code)
    TextView registerGetCode;
    @BindView(R.id.register_password)
    TextView registerPassword;
    @BindView(R.id.register_password_et)
    EditText registerPasswordEt;
    @BindView(R.id.register_confirm)
    TextView registerConfirm;
    @BindView(R.id.register_confirm_et)
    EditText registerConfirmEt;
    @BindView(R.id.register_errror)
    TextView registerErrror;
    @BindView(R.id.register_complete)
    Button registerComplete;
    Unbinder unbinder;
    private RegisterPresent registerPresent;
    private PhoneVerifyPresent phoneVerifyPresent;
    Timer timer;
    Integer pos = 60;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_regist;
    }

    @Override
    protected void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0://结束倒计时
                        registerGetCode.setText(AppContext.getContext().getString(R.string.register_get_code));
                        cancelTimer();
                        break;
                    case 1:
                        registerGetCode.setText(pos + "秒");
                        pos--;
                        break;
                }
            }
        };
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        unbinder = ButterKnife.bind(this, contentView);
        mActionbar.setTitle(R.string.login_bar_registe);
        registerPresent = new RegisterPresent(this);
        phoneVerifyPresent=new PhoneVerifyPresent(this);
        timer = new Timer();
    }

    @OnClick({R.id.register_get_code, R.id.register_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_get_code:
                String phoneNum = loginPhoneEt.getText().toString();
                if (registerPresent.isEmpty(phoneNum)) {
                    Toaster.toastShort("手机号不能为空！");
                } else {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            if (pos == 0) {
                                mHandler.sendEmptyMessage(0);
                            } else {
                                mHandler.sendEmptyMessage(1);
                            }
                        }
                    }, 50, 1000);
                    phoneVerifyPresent.getMobileVerifyCode(phoneNum);
                }
                break;
            case R.id.register_complete:
                String account = loginAccountEt.getText().toString();
                String weixinAccount = loginWeixnAccountEt.getText().toString();
                String email = loginEmailEt.getText().toString();
                String phone = loginPhoneEt.getText().toString();
                String code = registerCodeEt.getText().toString();
                String psd = registerPasswordEt.getText().toString();
                String confirm = registerConfirmEt.getText().toString();
                String nick=loginNickEt.getText().toString();
                if (TextUtils.isEmpty(code)){
                    Toaster.toastShort("验证码不能为空！");
                    return;
                }
                registerPresent.register(phone,weixinAccount,email,phone, psd, confirm,nick,null,null,null,null,code);
                break;
        }
    }

    @Override
    public void showErrorMessage(String err) {
        registerErrror.setVisibility(View.VISIBLE);
        registerErrror.setText(err);
    }

    @Override
    public void loginScucces(User user) {
        registerErrror.setVisibility(View.GONE);
        MainChartrActivity.start(activity);
    }

    @Override
    public void getCodeScucces(Integer code) {
//        registerCodeEt.setText(code+"");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
        timer=null;
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            pos = 60;
        }
    }

    @Override
    public void refreshGettMobileVerifyCode() {

    }
}
