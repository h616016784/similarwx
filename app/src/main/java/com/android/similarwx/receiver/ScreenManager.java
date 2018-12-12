package com.android.similarwx.receiver;

import android.app.Activity;
import android.content.Context;

import com.android.similarwx.activity.LiveActivity;

import java.lang.ref.WeakReference;

/**
 * Created by hanhuailong on 2018/12/12.
 */

public class ScreenManager {
    private Context mContext;

    private WeakReference<Activity> mActivityWref;

    public static ScreenManager gDefualt;

    public static ScreenManager getInstance(Context pContext) {
        if (gDefualt == null) {
            gDefualt = new ScreenManager(pContext.getApplicationContext());
        }
        return gDefualt;
    }
    private ScreenManager(Context pContext) {
        this.mContext = pContext;
    }

    public void setActivity(Activity pActivity) {
        mActivityWref = new WeakReference<Activity>(pActivity);
    }

    public void startActivity() {
        LiveActivity.actionToLiveActivity(mContext);
    }

    public void finishActivity() {
        //结束掉LiveActivity
        if (mActivityWref != null) {
            Activity activity = mActivityWref.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
