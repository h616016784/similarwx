package com.android.similarwx.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nimlib.sdk.auth.LoginInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements LoginViewInterface {

    @BindView(R.id.splash_iv)
    ImageView splashIv;

    Animator animator;
    private LoginPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        present=new LoginPresent(this);
        animator  = AnimatorInflater.loadAnimator(SplashActivity.this, R.animator.alpha_splash);
        animator.setTarget(splashIv);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashIv.setVisibility(View.VISIBLE);
                animator.start();
            }
        },100);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String accid;
                String token;
                String password;
//                try {
//                    accid=(String) SharePreferenceUtil.getObject(SplashActivity.this, AppConstants.USER_ACCID,"无");
//                    token=(String) SharePreferenceUtil.getObject(SplashActivity.this,AppConstants.USER_TOKEN,"a170417844a19c6bfebb4ab1a137fc31");
//                    password=(String) SharePreferenceUtil.getObject(SplashActivity.this,AppConstants.USER_LOGIN_PASSWORD,"a170417844a19c6bfebb4ab1a137fc31");
//                    if (!TextUtils.isEmpty(accid)&&!TextUtils.isEmpty(token)){
//                        present.login(accid,password,"","","");
//                    }else {
//                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
//                        finish();
//                    }
//                }catch (Exception e){
//                }
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator!=null){
            animator.cancel();
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void loginScucces(User user) {
        MainChartrActivity.start(SplashActivity.this);
        finish();
    }

    @Override
    public void logoutScucces(User user) {

    }

    @Override
    public void refreshTotalBalance(User user) {

    }
}
