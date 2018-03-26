package com.android.similarwx.widget.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.widget.wheel.WheelAdapter;
import com.android.similarwx.widget.wheel.WheelView;


public class WheelDialogBuilder {

    private Context mContext;

    private final BaseDialog mDialog;

    private ViewGroup mRoot;

    private WheelView mWheel;

    private TextView mCancelBtn;

    private TextView mConfirmBtn;

    private WheelSelectListener mListener;

    public interface WheelSelectListener {

        public void onWheelSelected(String item, long value);
    }

    public WheelDialogBuilder(Context context, WheelAdapter adapter, WheelSelectListener listener) {

        mContext = context;
        mListener = listener;
        mDialog = new BaseDialog(mContext, R.style.bottomDialogStyle);

        View v = LayoutInflater.from(context).inflate(R.layout.layout_dialog_wheel, null);
        mDialog.setContentView(v);

        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);

        mRoot = (ViewGroup) mDialog.findViewById(R.id.page_root);

        mWheel = (WheelView) mDialog.findViewById(R.id.wheel);
        mWheel.setAdapter(adapter);
        mWheel.TEXT_SIZE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 17,
                AppContext.getResources().getDisplayMetrics());

        mCancelBtn = (TextView) mDialog.findViewById(R.id.cancel);
        mConfirmBtn = (TextView) mDialog.findViewById(R.id.confirm);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onWheelSelected(mWheel.getCurrentText(), mWheel.getCurrentValue());
                }
                dismiss();
            }
        });
    }

    public WheelDialogBuilder(Context context, WheelAdapter adapter, WheelSelectListener listener,
                              int count) {

        mContext = context;
        mListener = listener;
        mDialog = new BaseDialog(mContext, R.style.bottomDialogStyle);

        View v = LayoutInflater.from(context).inflate(R.layout.layout_dialog_wheel, null);
        mDialog.setContentView(v);

        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);

        mRoot = (ViewGroup) mDialog.findViewById(R.id.page_root);

        mWheel = (WheelView) mDialog.findViewById(R.id.wheel);
        mWheel.setAdapter(adapter, count);
        mWheel.TEXT_SIZE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 17,
                AppContext.getResources().getDisplayMetrics());

        mCancelBtn = (TextView) mDialog.findViewById(R.id.cancel);
        mConfirmBtn = (TextView) mDialog.findViewById(R.id.confirm);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onWheelSelected(mWheel.getCurrentText(), mWheel.getCurrentValue());
                }
                dismiss();
            }
        });
    }

    public BaseDialog create() {

        mWheel.setCurrentItem(0);

        mRoot.getLayoutParams().width = AppContext.getScreenWidth();
        mRoot.requestLayout();

        return mDialog;
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
