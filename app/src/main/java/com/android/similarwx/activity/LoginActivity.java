package com.android.similarwx.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.beans.User;
import com.android.similarwx.fragment.RegistFragment;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginViewInterface {

    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_forget_password)
    TextView loginForgetPassword;
    @BindView(R.id.login_wx_iv)
    ImageView loginWxIv;
    @BindView(R.id.login_login)
    Button login_login;
    @BindView(R.id.login_error)
    TextView loginError;
    private LoginPresent loginPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化butterKnife
        ButterKnife.bind(this);
        loginPresent = new LoginPresent(this);
    }

    @OnClick({R.id.login, R.id.regist, R.id.login_forget_password, R.id.login_wx_iv, R.id.login_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login://

                break;
            case R.id.regist://注册
                FragmentUtils.navigateToNormalActivity(this,new RegistFragment(),null);
                break;
            case R.id.login_forget_password://忘记密码

                break;
            case R.id.login_wx_iv://微信登录

                break;
            case R.id.login_login://登录
                loginError.setVisibility(View.GONE);
                String name = loginAccount.getText().toString();
                String password = loginPassword.getText().toString();
                loginPresent.login(name, password, null);
                break;
        }
    }

    @Override
    public void loginScucces(User user) {

        loginPresent.saveUser(user);
        //之后跳转界面
    }
    @Override
    public void showErrorMessage(String err) {
        loginError.setVisibility(View.VISIBLE);
        loginError.setText(err);
    }
}
