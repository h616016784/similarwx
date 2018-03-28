package com.android.similarwx.base;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.outbaselibrary.utils.StringUtil;
import com.android.similarwx.R;
import com.android.similarwx.inteface.OnInterceptKeyListener;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.StatusBarUtil;
import com.android.similarwx.widget.Actionbar;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public class BaseActivity extends AppCompatActivity  implements View.OnClickListener{

    public static final String ARGUMENT_EXTRA_FRAGMENT_NAME = "com.similarwx.activity.ARGUMENT_EXTRA_FRAGMENT_NAME";

    public static final String ARGUMENT_EXTRA_NO_ANIMATION = "com.similarwx.activity.ARGUMENT_EXTRA_NO_ANIMATION";

    public static final String ARGUMENT_EXTRA_ANIMATION_UP = "com.similarwx.activity.ARGUMENT_EXTRA_ANIMATION_UP";

    public static final String ARGUMENT_EXTRA_ANIMATION_SPLASH = "com.similarwx.activity.ARGUMENT_EXTRA_ANIMATION_SPLASH";

    public static final String ARGUMENTS_ENTER_ANIMATION = "com.similarwx.activity.ARGUMENTS_ENTER_ANIMATION";

    public static final String ARGUMENTS_EXIT_ANIMATION = "com.similarwx.activity.ARGUMENTS_EXIT_ANIMATION";

    public static final String ARGUMENTS_FULL_SCREEN = "com.similarwx.activity.ARGUMENTS_FULL_SCREEN";

    public static final String ACTION_SIGN_IN = "com.similarwx.action.sign.in";

    public static final String ACTION_FINISH = "com.similarwx.action.finish";

    protected boolean mNoAnimation;

    protected String mFragmentClassName;

    protected Handler mHandler = new Handler();

    protected Actionbar mActionbar;

    protected View mLoadingView;

    protected BaseFragment mContentFragment;

    protected BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    private OnInterceptKeyListener mOnInterceptKeyListener;

    public void refresh() {
        if (mContentFragment != null) {
            mContentFragment.onRefresh();
        }

    }

    protected int getLayoutResource() {
        return R.layout.activity_base;
    }

    protected void setContentView() {
        setContentView(getLayoutResource());
        mActionbar = (Actionbar) findViewById(R.id.actionbar);
        mLoadingView = findViewById(R.id.loading_view);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        mOnInterceptKeyListener = initContentFragment();

        StatusBarUtil.statusBarLightMode(this);
        registerReceiver(mFinishReceiver, new IntentFilter(ACTION_FINISH));
    }

    public Actionbar getActionbar() {
        return mActionbar;
    }


    public View getLoadingView() {
        return mLoadingView;
    }

    protected BaseFragment initContentFragment() {
        return null;
    }

    protected void loadFragment(Fragment fragment, Bundle bundle) {
        if (fragment == null) {
            finish();
        }
        mFragmentClassName = fragment.getClass().getName();
        FragmentUtils.replaceFragment(R.id.layout_container_main, getFragmentManager(), fragment,
                bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        MobclickAgent.onResume(this);
//        ClickManager.getInstance().setFragmentManager(getFragmentManager());
//        ClickManager.getInstance().setCurrentActivity(this);
//        ClickManager.getInstance().setHandler(mHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();

//        MobclickAgent.onPause(this);
//
//        ClickManager.getInstance().setFragmentManager(null);
//        ClickManager.getInstance().setCurrentActivity(null);
//        ClickManager.getInstance().setHandler(null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Fragment fragment = getFragmentManager().findFragmentById(R.id.layout_container_main);

        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onActionBackPressed() {
        if (mOnInterceptKeyListener != null && mOnInterceptKeyListener.isInterceptBackEvent()) {
            // Do something related.
        } else {
            onBackPressed();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mOnInterceptKeyListener != null && mOnInterceptKeyListener.isInterceptKeyEvent(event)) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    public void hideKeyboard() {
        InputMethodManager localInputMethodManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            localInputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (StringUtil.equalsIgnoreCase(appProcess.processName, packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View view) {

    }

    private void unregister() {
        if (mFinishReceiver != null) {
            unregisterReceiver(mFinishReceiver);
            mFinishReceiver = null;
        }
    }

    @Override
    public void finish() {
        super.finish();

        unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregister();
    }
}
