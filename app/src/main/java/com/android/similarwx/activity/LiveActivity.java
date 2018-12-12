package com.android.similarwx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.receiver.ScreenManager;

/**
 * Created by hanhuailong on 2018/12/12.
 */

public class LiveActivity extends BaseActivity {
    public static final String TAG = LiveActivity.class.getSimpleName();

    public static void actionToLiveActivity(Context pContext) {
        Intent intent = new Intent(pContext, LiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        Window window = getWindow();
        //放在左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        //宽高设计为1个像素
        attributes.width = 1;
        attributes.height = 1;
        //起始坐标
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);

        ScreenManager.getInstance(this).setActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
