package com.android.similarwx.base;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.ViewUtil;
import com.android.similarwx.inteface.OnInterceptKeyListener;
import com.android.similarwx.widget.Actionbar;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public class BaseFragment extends Fragment
        implements View.OnClickListener, OnInterceptKeyListener {
    protected Activity activity;
    protected int mGeneratedLoaderId = ViewUtil.generateViewId();
    protected int mConstantLoaderId = ViewUtil.generateViewId();
    protected Handler mHandler;
    protected String mSimpleName;
    protected String TAG = getSimpleName();
    protected Actionbar mActionbar;

    protected View mLoadingView;

    private boolean mIsFirstCreated;

    private boolean mIsPermissionGranted;

    public Activity getHolderActivity(){
        return this.activity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        systemConfig();

        initHandler();

        setFirstCreated(true);

        onInitData(getArguments());
    }

    protected void systemConfig() {

    }

    protected void cameraPermission() {
        if (!mIsPermissionGranted) {
            requestCameraPermission();
            requestReadPermission();
            requestWritePermission();
            mIsPermissionGranted = true;
        }
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, AppConstants.REQUEST_CODE_CAMERA_PERMISSION);
            }
        }
    }

    private void requestReadPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.REQUEST_CODE_READ_PERMISSION);
            }
        }
    }

    private void requestWritePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CODE_WRITE_PERMISSION);
            }
        }
    }

    protected void initHandler() {
        mHandler = new Handler();
    }

    protected void onInitData(Bundle bundle) {
        // Override in sub-class.
    }

    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        initActivityView();
        onInitView(view);
        if (isNeedFetch()) {
            fetchData();
        }
        return view;
    }

    protected void initActivityView() {
        mActionbar = getActionbar();
        if (isHideBackBtn()) {
            mActionbar.hideBackBtn();
        }
        mActionbar.setVisibility(isHideActionbar() ? View.GONE : View.VISIBLE);
        mLoadingView = getLoadingView();
    }

    protected void onInitView(View contentView) {
        // Override in sub-class.
    }

    protected void fetchData() {
        // Override in sub-class.
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

        setFirstCreated(false);
    }

    protected boolean isHideBackBtn() {
        return false;
    }

    protected boolean isHideActionbar() {
        return false;
    }

    protected boolean isNeedFetch() {
        return false;
    }

    public void showKeyboard() {
        ((InputMethodManager) AppContext.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyboard() {
        if (getView() != null) {
            ((InputMethodManager) AppContext.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    public void onUpdate() {

    }

    public void onRefresh() {

    }

    protected void startLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    protected void stopLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    public boolean isFirstCreated() {
        return mIsFirstCreated;
    }

    public void setFirstCreated(boolean mIsFirstCreated) {
        this.mIsFirstCreated = mIsFirstCreated;
    }

    public String getSimpleName() {
        if (TextUtils.isEmpty(mSimpleName)) {
            mSimpleName = getClass().getSimpleName();
        }
        return mSimpleName;
    }

    @Override
    public void onClick(View v) {

    }

    protected Actionbar getActionbar() {
        return ((BaseActivity) getActivity()).getActionbar();
    }

    protected View getLoadingView() {
        return ((BaseActivity) getActivity()).getLoadingView();
    }

    protected void fetchConstant() {
    }

    @Override
    public boolean isInterceptKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return onBackPressed();
        }
        return false;
    }

    @Override
    public boolean isInterceptBackEvent() {
        return onBackPressed();
    }

    protected boolean onBackPressed() {
        return false;
    }

}
