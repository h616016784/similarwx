package com.android.similarwx.activity;

import android.os.Bundle;
import android.widget.Button;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.NormalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化butterKnife
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button4)
    public void onViewClicked() {
        MIActivity.show(this);
    }
}
