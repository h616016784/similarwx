package com.android.similarwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;

public class CancelDialogBuilder extends Dialog implements DialogInterface {
	private LinearLayout mLinearLayoutView;
	private RelativeLayout mRelativeLayoutView;
	private LinearLayout mLinearLayoutTopView;
	private View mDialogView;
	private int mDuration = -1;
	private static int mOrientation = 1;
	private boolean isCancelable = true;
	private volatile static CancelDialogBuilder instance;
	private TextView dialog_title;

	public CancelDialogBuilder(Context context) {
		super(context);
		init(context);

	}

	public CancelDialogBuilder(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.height = ViewGroup.LayoutParams.MATCH_PARENT;
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(
				(WindowManager.LayoutParams) params);

	}

	public static CancelDialogBuilder getInstance(Context context) {

		int ort = context.getResources().getConfiguration().orientation;
		if (mOrientation != ort) {
			mOrientation = ort;
			instance = null;
		}

		instance = new CancelDialogBuilder(context,
				R.style.dialog_untran);
		return instance;

	}

	private void init(Context context) {

		mDialogView = View
				.inflate(context, R.layout.cancel_dialog_layout, null);

		mLinearLayoutView = (LinearLayout) mDialogView
				.findViewById(R.id.cancle_parentPanel);
		mRelativeLayoutView = (RelativeLayout) mDialogView
				.findViewById(R.id.cancle_main);
		mLinearLayoutTopView = (LinearLayout) mDialogView
				.findViewById(R.id.cancle_topPanel);

		setContentView(mDialogView);

		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {

				mLinearLayoutView.setVisibility(View.VISIBLE);
			}
		});
		mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (isCancelable)
					dismiss();
			}
		});
	}

	public void setTitleText(String text) {
		// TODO Auto-generated method stub
		dialog_title = (TextView) mDialogView.findViewById(R.id.dialog_title);
		dialog_title.setText(text);
	}
	public void setDetermineText(String text) {
		// TODO Auto-generated method stub
		 ((TextView) mDialogView.findViewById(R.id.cancle_determine)).setText(text);
	}

	public CancelDialogBuilder setButton1Click(View.OnClickListener click) {

		mDialogView.findViewById(R.id.cancle_btncancle).setOnClickListener(
				click);

		return this;
	}
	public CancelDialogBuilder setCancelNone() {

		mDialogView.findViewById(R.id.cancle_btncancle).setVisibility(View.GONE);

		return this;
	}
	public CancelDialogBuilder setButton2Click(View.OnClickListener click) {
		mDialogView.findViewById(R.id.cancle_determine).setOnClickListener(
				click);
		return this;
	}

	public CancelDialogBuilder isCancelableOnTouchOutside(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCanceledOnTouchOutside(cancelable);
		return this;
	}

	public CancelDialogBuilder isCancelable(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCancelable(cancelable);
		return this;
	}

}
