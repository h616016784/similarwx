package com.android.similarwx.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseDialog;

/**
 * Created by Albert
 * on 16-6-2.
 */
public class DialogBuilder {

    public static final String SINGLE_CHOICE_TEXT = "single_choice_text";

    public static final String SINGLE_CHOICE_ICON = "single_choice_icon";

    protected final BaseDialog mDialog;

    protected ViewGroup mRoot;

    private ViewGroup mTitleLayout;

    private TextView mTitle;

    private TextView mMessage;

    public DialogBuilder(Context context) {

        mDialog = new BaseDialog(context, getStyleResource());

        View v = LayoutInflater.from(context).inflate(getLayoutResource(), null);
        mDialog.setContentView(v);

        mRoot = (ViewGroup) mDialog.findViewById(R.id.page_root);
        mTitleLayout = (ViewGroup) mDialog.findViewById(R.id.title_layout);
        mTitle = (TextView) mDialog.findViewById(R.id.toast_title);
        mMessage = (TextView) mDialog.findViewById(R.id.message);
    }

    protected int getStyleResource() {
        return R.style.dialogBuilder;
    }

    protected int getLayoutResource() {
        return R.layout.dialog_builder;
    }

    protected void setGravity(int gravity) {
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(gravity);
    }

    private void bindButton(int resId, final DialogInterface.OnClickListener onClickListener,
                            int id, final int which, final boolean isNeedDismiss) {

        View layout = mDialog.findViewById(R.id.dialog_btn_layout);
        layout.setVisibility(View.VISIBLE);

        View view = mDialog.findViewById(id);
        ((Button) view).setText(resId);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(mDialog, which);
                }
                if (isNeedDismiss) {
                    mDialog.dismiss();
                }
            }
        });

        view.setVisibility(View.VISIBLE);
    }

    public BaseDialog create() {
        return mDialog;
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public DialogBuilder setMessageGravity(int gravity) {
        mMessage.setGravity(gravity);
        return this;
    }

    public DialogBuilder setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public DialogBuilder setOnCancelListener(DialogInterface.OnCancelListener OnCancelListener) {
        mDialog.setOnCancelListener(OnCancelListener);
        return this;
    }

    public DialogBuilder setCancelable(boolean flag) {
        mDialog.setCancelable(flag);
        return this;
    }

    public DialogBuilder setMessage(int resId) {
        mMessage.setText(resId);
        mMessage.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogBuilder setMessage(String text) {
        mMessage.setText(text);
        mMessage.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogBuilder setPositiveButton(int resId, boolean isNeedDismiss, DialogInterface.OnClickListener onClickListener) {
        bindButton(resId, onClickListener, R.id.button1, DialogInterface.BUTTON_POSITIVE,
                isNeedDismiss);
        return this;
    }

    public DialogBuilder setPositiveButton(int resId, DialogInterface.OnClickListener onClickListener) {
        bindButton(resId, onClickListener, R.id.button1, DialogInterface.BUTTON_POSITIVE, true);
        return this;
    }

    public DialogBuilder setNeutralButton(int resId, DialogInterface.OnClickListener onClickListener) {
        View view = mDialog.findViewById(R.id.dialog_btn_separator2);
        view.setVisibility(View.VISIBLE);

        bindButton(resId, onClickListener, R.id.button3, DialogInterface.BUTTON_NEUTRAL, true);
        return this;
    }

    public DialogBuilder setNegativeButton(int resId, DialogInterface.OnClickListener onClickListener) {

        View view = mDialog.findViewById(R.id.dialog_btn_separator1);
        view.setVisibility(View.VISIBLE);

        bindButton(resId, onClickListener, R.id.button2, DialogInterface.BUTTON_NEGATIVE, true);
        return this;
    }

    public DialogBuilder setTitle(int resId) {
        mTitle.setText(resId);
        mTitleLayout.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogBuilder setSingleLine(boolean singleLine) {
        mMessage.setSingleLine(singleLine);
        return this;
    }

    public DialogBuilder setTextGravity(int gravity) {
        mMessage.setGravity(gravity);
        return this;
    }

    public DialogBuilder setTitle(String text) {
        mTitle.setText(text);
        mTitleLayout.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogBuilder setView(View view) {
        ViewGroup viewGroup = (ViewGroup) mDialog.findViewById(R.id.custom_view_holder);
        viewGroup.addView(view);
        viewGroup.setVisibility(View.VISIBLE);
        return this;
    }

    public DialogBuilder setItems(CharSequence[] item,
                                  final DialogInterface.OnClickListener onClickListener) {

        ListView list = (ListView) mDialog.findViewById(R.id.list);
        list.setVisibility(View.VISIBLE);
        list.setAdapter(new ArrayAdapter(AppContext.getContext(), R.layout.dialog_item_line, item));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (onClickListener != null) {
                    onClickListener.onClick(mDialog, position);
                }
                mDialog.dismiss();
            }
        });

        return this;
    }


}
