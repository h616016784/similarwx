package com.android.similarwx.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_iv)
    ImageView splashIv;

    Animator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
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
}
