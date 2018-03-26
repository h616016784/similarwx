package com.android.similarwx.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;

/**
 * Created by Albert
 * on 16-6-2.
 */
public class BaseDialog extends Dialog {
    private static final String TAG = "BaseDialog";

    protected Context mContext;

    public BaseDialog(Context context) {
        super(context, R.style.dialogBuilder);
        init(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, R.style.dialogBuilder);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
    }

    @Override
    public void show() {

        if (isShowing()) {
            return;
        }

        if (mContext != null && mContext instanceof Activity) {
            if (!((Activity) mContext).isFinishing()) {
                super.show();
            }
        } else {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        hideKeyboard();
        super.dismiss();
    }

    public void showKeyboard() {
        ((InputMethodManager) AppContext.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) AppContext.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    currentFocus.getWindowToken(), 0);
        }
    }
}
