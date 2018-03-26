package com.android.similarwx.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public class BaseActivity extends AppCompatActivity {

    public void onActionBackPressed() {
        onBackPressed();
    }
}
