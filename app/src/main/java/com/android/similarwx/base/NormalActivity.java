package com.android.similarwx.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.android.similarwx.R;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public class NormalActivity extends BaseActivity {
    private static final String TAG = "NormalActivity";

    private boolean isUpAnim;

    private boolean isSplashAnim;

    private int mEnterAnimationResId;

    private int mExitAnimationResId;

    private boolean mIsFullScreen;

    private int mExitAnimationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mIsFullScreen = bundle.getBoolean(ARGUMENTS_FULL_SCREEN);
            mEnterAnimationResId = bundle.getInt(ARGUMENTS_ENTER_ANIMATION);
            mExitAnimationResId = bundle.getInt(ARGUMENTS_EXIT_ANIMATION);

            mNoAnimation = bundle.getBoolean(ARGUMENT_EXTRA_NO_ANIMATION);

            isUpAnim = bundle.getBoolean(ARGUMENT_EXTRA_ANIMATION_UP);
            isSplashAnim = bundle.getBoolean(ARGUMENT_EXTRA_ANIMATION_SPLASH);
        }

        super.onCreate(savedInstanceState);
        overrideEnterAnimation(bundle);
    }

    protected void overrideEnterAnimation(Bundle bundle) {
        if (mNoAnimation) {
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
        } else if (isUpAnim) {
            overridePendingTransition(R.anim.down_in, R.anim.fade_out);
        } else if (isSplashAnim) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (mEnterAnimationResId != 0) {
            overridePendingTransition(mEnterAnimationResId, R.anim.alpha);
        } else {
            overridePendingTransition(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit);
        }
    }

    @Override
    public void setContentView(int layoutResID) {

        if (mIsFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        super.setContentView(layoutResID);
}

    @Override
    public void finish() {
        super.finish();

        if (mNoAnimation) {
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
        } else if (mExitAnimationId > 0) {
            overridePendingTransition(0, mExitAnimationId);
        } else if (mExitAnimationResId != 0) {
            overridePendingTransition(R.anim.alpha, mExitAnimationResId);
        } else if (isUpAnim) {
            overridePendingTransition(R.anim.playlist_slide_in, R.anim.down_out);
        }
    }

    @Override
    protected BaseFragment initContentFragment() {

        String className = null;
        Bundle bundle = null;
        if (getFragmentManager().findFragmentById(R.id.layout_container_main) == null
                && getIntent() != null && getIntent().getExtras() != null) {
            bundle = getIntent().getExtras();
            className = bundle.getString(ARGUMENT_EXTRA_FRAGMENT_NAME);
        }

        if (className != null) {

            try {

                Fragment fragment = (Fragment) Class.forName(className).newInstance();
                loadFragment(fragment, bundle);

                if (fragment instanceof BaseFragment) {

                    mContentFragment = (BaseFragment) fragment;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return mContentFragment;

    }
}
