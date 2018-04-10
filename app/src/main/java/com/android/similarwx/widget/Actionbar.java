package com.android.similarwx.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseActivity;
import com.netease.nim.uikit.business.robot.parser.elements.group.LinearLayout;

/**
 * Created by Albert
 * on 16-5-31.
 */
public class Actionbar extends FrameLayout {

    private BaseActivity mActivity;

    private ViewGroup mTitleLayout;

    private TextView mTitleView;

    private TextView mLeftBtn;

    private TextView mRightBtn;

    private ImageView mLeftImage;

    private ImageView mRightImage;
    private ImageView rightImagePeople;

    private ViewGroup mLeftLayout;

    private ViewGroup mRightLayout;

    private View mCustomTitleView;
    private LinearLayout layout_ll;
    View layout;

    public Actionbar(Context context) {
        super(context);
        initViews(context);
    }

    public Actionbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public Actionbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {

        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        }

        layout= LayoutInflater.from(context).inflate(R.layout.actionbar_common, this);
        setBackgroundColor(AppContext.getColor(R.color.white));

        mTitleLayout = (ViewGroup) layout.findViewById(R.id.title_layout);
        mTitleView = (TextView) layout.findViewById(R.id.title);
        mLeftBtn = (TextView) layout.findViewById(R.id.left_btn);
        mRightBtn = (TextView) layout.findViewById(R.id.right_btn);
        mLeftImage = (ImageView) layout.findViewById(R.id.left_image);
        mRightImage = (ImageView) layout.findViewById(R.id.right_image);
        rightImagePeople = (ImageView) layout.findViewById(R.id.right_image_people);
        mLeftLayout = (ViewGroup) layout.findViewById(R.id.left_layout);
        mRightLayout = (ViewGroup) layout.findViewById(R.id.right_layout);
        initActions();
    }

    private void initActions() {
        if (mActivity != null) {
            mLeftLayout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    mActivity.onActionBackPressed();
                }
            });
        }
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void customTitle(View v) {
        mCustomTitleView = v;
        mTitleLayout.addView(mCustomTitleView);
        mTitleLayout.findViewById(R.id.title).setVisibility(GONE);
        mTitleView = (TextView) mCustomTitleView.findViewById(R.id.custom_title);
    }

    public void showCustomTitle() {
        mCustomTitleView.setVisibility(VISIBLE);
        mTitleLayout.findViewById(R.id.title).setVisibility(GONE);
        mTitleView = (TextView) mCustomTitleView.findViewById(R.id.custom_title);
    }

    public void recoverTitle() {
        mTitleView = (TextView) mTitleLayout.findViewById(R.id.title);
        mCustomTitleView.setVisibility(GONE);
        mTitleView.setVisibility(VISIBLE);
    }

    public Actionbar hideBackBtn() {
        mLeftImage.setImageResource(0);
        mLeftImage.setVisibility(GONE);
        return this;
    }

    public Actionbar showBackBtn() {
        mLeftImage.setImageResource(R.drawable.actionbar_back);
        return this;
    }

    public Actionbar setTitle(int resId) {
        mTitleView.setText(resId);
        return this;
    }

    public Actionbar setTitle(String title) {
        mTitleView.setText(title);
        return this;
    }

    public void setLeftText(String text) {
        mLeftBtn.setText(text);
        mLeftBtn.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
    }

    public void setLeftText(int textRes) {
        mLeftBtn.setText(textRes);
        mLeftBtn.setVisibility(VISIBLE);
    }

    public void setRightText(String text) {
        mRightBtn.setText(text);
        mRightBtn.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
    }

    public void setRightText(int textRes) {
        mRightBtn.setText(textRes);
        mRightBtn.setVisibility(VISIBLE);
    }

    public void setLeftImage(int image) {
        mLeftImage.setImageResource(image);
        mLeftImage.setVisibility(image > 0 ? VISIBLE : GONE);
    }

    public void setRightImage(int image) {
        mRightImage.setImageResource(image);
        mRightImage.setVisibility(image > 0 ? VISIBLE : GONE);
    }

    public void setWholeBackground(int res){
        layout.setBackgroundColor(res);
    }
    /**
     * 设置people
     * @param image
     */
    public void setRightImagePeople(int image) {
        rightImagePeople.setImageResource(image);
        rightImagePeople.setVisibility(image > 0 ? VISIBLE : GONE);
    }
    public void setRightImageOnClickListener(OnClickListener listener){
        mRightImage.setOnClickListener(listener);
    }
    public void setRightImagePeopleOnClickListener(OnClickListener listener){
        rightImagePeople.setOnClickListener(listener);
    }

    public void setLeftOnClickListener(OnClickListener listener) {
        mLeftLayout.setOnClickListener(listener);
    }

    public void setRightOnClickListener(OnClickListener listener) {
        mRightLayout.setOnClickListener(listener);
    }

    public void setRightEnable(boolean enable) {
        mRightLayout.setEnabled(enable);
    }

    public void clearLeft() {
        setLeftImage(0);
        setLeftText(null);
        mLeftLayout.setOnClickListener(null);
    }

    public void clearRight() {
        setRightImage(0);
        setRightText(null);
        mRightLayout.setOnClickListener(null);
    }

}
