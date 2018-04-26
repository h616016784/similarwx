package com.android.similarwx.widget;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.android.similarwx.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InputPasswordDialog extends DialogFragment implements View.OnClickListener {

    private OnInputFinishListener onInputFinishListener;
    private View v;
    private String pointStr;
    private TextView[] passwordTvs;
    private StringBuilder password = new StringBuilder();
    private int index;

    public static InputPasswordDialog newInstance(String title, String money, OnInputFinishListener onInputFinishListener) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("money", money);
        InputPasswordDialog fragment = new InputPasswordDialog();
        fragment.setArguments(bundle);
        fragment.setOnInputFinishListener(onInputFinishListener);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //取消自带标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置背景颜色
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //接收参数
        String money = getArguments().getString("money");
        String title = getArguments().getString("title");
        //布局
        v = inflater.inflate(R.layout.dialog_pay_input_password, container, false);
        //字符串
        pointStr = getString(R.string.eml_point);
        //遮盖的黑点
        passwordTvs = new TextView[6];
        passwordTvs[0] = (TextView) v.findViewById(R.id.tv_pw0);
        passwordTvs[1] = (TextView) v.findViewById(R.id.tv_pw1);
        passwordTvs[2] = (TextView) v.findViewById(R.id.tv_pw2);
        passwordTvs[3] = (TextView) v.findViewById(R.id.tv_pw3);
        passwordTvs[4] = (TextView) v.findViewById(R.id.tv_pw4);
        passwordTvs[5] = (TextView) v.findViewById(R.id.tv_pw5);
        //点击监听
        v.findViewById(R.id.tv_num1).setOnClickListener(this);
        v.findViewById(R.id.tv_num2).setOnClickListener(this);
        v.findViewById(R.id.tv_num3).setOnClickListener(this);
        v.findViewById(R.id.tv_num4).setOnClickListener(this);
        v.findViewById(R.id.tv_num5).setOnClickListener(this);
        v.findViewById(R.id.tv_num6).setOnClickListener(this);
        v.findViewById(R.id.tv_num7).setOnClickListener(this);
        v.findViewById(R.id.tv_num8).setOnClickListener(this);
        v.findViewById(R.id.tv_num9).setOnClickListener(this);
        v.findViewById(R.id.tv_num0).setOnClickListener(this);
        v.findViewById(R.id.iv_backspace).setOnClickListener(this);
        v.findViewById(R.id.iv_close).setOnClickListener(this);
        //金额
        TextView moneyTv = (TextView) v.findViewById(R.id.tv_money);

        double m = Double.parseDouble(money);

        if (m == 0) {
            moneyTv.setVisibility(View.GONE);
        } else {

            BigDecimal bd = new BigDecimal(m);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            String chargeText = getString(R.string.rmb_symbol) + (bd.toString());
            moneyTv.setText(chargeText);
        }
        // 标题
        TextView titleTv = (TextView) v.findViewById(R.id.tv_title);
        titleTv.setText(title);
        //显示动画
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        ta.setDuration(200);
        v.startAnimation(ta);
        return v;
    }

    @Override
    public void dismiss() {
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                superDismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        v.startAnimation(ta);
    }

    public void superDismiss() {
        super.dismiss();
    }

    public void setOnInputFinishListener(OnInputFinishListener onInputFinishListener) {
        this.onInputFinishListener = onInputFinishListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_num1:
                addNum(1);
                break;
            case R.id.tv_num2:
                addNum(2);
                break;
            case R.id.tv_num3:
                addNum(3);
                break;
            case R.id.tv_num4:
                addNum(4);
                break;
            case R.id.tv_num5:
                addNum(5);
                break;
            case R.id.tv_num6:
                addNum(6);
                break;
            case R.id.tv_num7:
                addNum(7);
                break;
            case R.id.tv_num8:
                addNum(8);
                break;
            case R.id.tv_num9:
                addNum(9);
                break;
            case R.id.tv_num0:
                addNum(0);
                break;
            case R.id.iv_backspace:
                backspace();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    private void addNum(int num) {
        if (index < 6) {
            password.append(num);
            passwordTvs[index].setText(pointStr);
            index++;
        }
        if (index >= 6) {
            onInputFinishListener.onInputFinish(password.toString());
            dismiss();
        }
    }

    private void backspace() {
        if (index > 0) {
            index--;
            passwordTvs[index].setText("");
            password.deleteCharAt(password.length() - 1);
        }
    }

    public interface OnInputFinishListener {
        void onInputFinish(String password);
    }
}
