package com.android.similarwx.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.fragment.MIFragment;

/**
 * Created by hanhuailong on 2018/3/28.
 */

public class MIActivity extends BaseActivity {
    public static final String ACTION_MI = "com.android.similarwx.activity.MIActivity";

    public static void show(Context context, Bundle bundle){

    }
    public static void show(Context context){
        Bundle bundle = new Bundle();
        show(context,bundle,-1);
    }
    public static void show(Context context, Bundle bundle, int flag) {
        Intent intent = new Intent(context, MIActivity.class);
        intent.setFlags(
                flag > 0 ? flag : (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        if (bundle != null) {
            intent.putExtras(bundle);
            intent.setAction(ACTION_MI);
        }
        context.startActivity(intent);
        if(context instanceof Activity){
            ((Activity)context).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment(new MIFragment(),null);
    }

}
