package com.android.similarwx.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseDialog;


/**
 * Created by Albert on 2016/8/29.
 */
public class TwoButtonDialogBuilder {

    protected final BaseDialog mDialog;

    protected ViewGroup mRoot;

    private TextView mMessage;

    private TextView mConfirm;

    private TextView mCancle;

    public TwoButtonDialogBuilder(Context context) {

        mDialog = new BaseDialog(context, getStyleResource());

        View v = LayoutInflater.from(context).inflate(getLayoutResource(), null);
        mDialog.setContentView(v);

        mRoot = (ViewGroup) mDialog.findViewById(R.id.page_root);
        mMessage = (TextView) mDialog.findViewById(R.id.message);
        mConfirm = (TextView) mDialog.findViewById(R.id.confirm);
        mCancle = (TextView) mDialog.findViewById(R.id.cancel);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    protected int getStyleResource() {
        return R.style.dialogBuilder;
    }

    protected int getLayoutResource() {
        return R.layout.dialog_two_button;
    }

    public TwoButtonDialogBuilder setMessage(int resId) {
        mMessage.setText(resId);
        mMessage.setVisibility(View.VISIBLE);
        return this;
    }

    public TwoButtonDialogBuilder setMessage(String text) {
        mMessage.setText(text);
        mMessage.setVisibility(View.VISIBLE);
        return this;
    }

    public TwoButtonDialogBuilder setButtonText(int confirmId,int cancleId) {
        if(confirmId>0)
        mConfirm.setText(confirmId);
        if(cancleId>0)
        mCancle.setText(cancleId);
        return this;
    }

    public TwoButtonDialogBuilder setConfirmButton(final View.OnClickListener onClickListener) {
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                dismiss();
            }
        });
        return this;
    }
    public TwoButtonDialogBuilder setCancelButton(final View.OnClickListener onClickListener) {
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public TwoButtonDialogBuilder  setCancleBackground(int res){
        mCancle.setBackgroundResource(res);
        return this;
    }

    public BaseDialog create() {
        return mDialog;
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
