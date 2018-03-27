package com.android.similarwx.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;

/**
 * Created by puyafeng on 2017/3/23.
 */

public class DialogUtils {
    //******************************************通用******************************************************

    /**
     * 弹出两个按钮的对话框
     *
     * @param poActivity
     * @param poHandler
     * @param psTitle
     * @param psText
     * @param piOkMsg        点击Positive按钮的msg
     * @param psPositiveName 按钮名
     * @param psCancelName   按钮名
     */
    public static AlertDialog twoBtnDialog(Activity poActivity, final Handler poHandler, String psTitle,
                                           String psText, final int piOkMsg, final String psPositiveName, final String psCancelName) {
        final AlertDialog loDialog = new AlertDialog.Builder(poActivity).create();
        LayoutInflater loInflater = poActivity.getLayoutInflater();
        loDialog.setView(loInflater.inflate(R.layout.dialog_info, null));
        loDialog.show();
        loDialog.getWindow().setContentView(R.layout.dialog_info);
        loDialog.setCancelable(false);
        TextView loTitleInfo = (TextView) loDialog.findViewById(R.id.dialog_title);
        TextView loTextInfo = (TextView) loDialog.findViewById(R.id.dialog_tv_tip);
        Button loBtnOk = (Button) loDialog.findViewById(R.id.dialog_btn_ok);
        Button loBtnCancel = (Button) loDialog.findViewById(R.id.dialog_btn_cancle);
        if (!TextUtils.isEmpty(psTitle))
            loTitleInfo.setText(psTitle);
        if (!TextUtils.isEmpty(psPositiveName))
            loTextInfo.setText(psText);
        if (!TextUtils.isEmpty(psPositiveName))
            loBtnOk.setText(psPositiveName);
        if (!TextUtils.isEmpty(psCancelName))
            loBtnCancel.setText(psCancelName);
        loBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loDialog.dismiss();
                if (poHandler != null) {
                    Message loMsg = new Message();
                    loMsg.what = piOkMsg;
                    poHandler.sendMessage(loMsg);
                }
            }
        });
        loBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loDialog.dismiss();
            }
        });
        return loDialog;
    }

    /**
     * 系统消息弹出框
     *
     * @param psText
     * @param psPositiveName
     * @param listener
     * @return
     */
    public static AlertDialog systemDialog(String psText, final String psPositiveName, final View.OnClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AppContext.getContext());
        View dialogView = LayoutInflater.from(AppContext.getContext()).inflate(R.layout.dialog_info, null);
        TextView loTextInfo = (TextView) dialogView.findViewById(R.id.dialog_tv_tip);
        loTextInfo.setText(psText);
        Button loBtnOk = (Button) dialogView.findViewById(R.id.dialog_btn_ok);
        loBtnOk.setText(psPositiveName);
        if (listener != null) {
            loBtnOk.setOnClickListener(listener);
        }
        dialogView.findViewById(R.id.dialog_btn_cancle).setVisibility(View.GONE);
        dialog.setView(dialogView);
        AlertDialog mDialog = dialog.create();
        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);// 设定为系统级警告，关键
        mDialog.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        mDialog.show();
        return mDialog;
    }

    /**
     * 弹出一个按钮的对话框
     *
     * @param poActivity
     * @param psTitle
     * @param psText
     * @param ok
     * @param listener
     * @param keylistener
     * @return
     */
    public static Dialog oneBtnDialog(final Activity poActivity, String psTitle, String psText, String ok,
                                      View.OnClickListener listener, DialogInterface.OnKeyListener keylistener, boolean okclose) {
        Dialog dialog;
        if (okclose) {
            dialog = twoBtnDialog(poActivity, psTitle, psText, ok, "", listener, true);
        } else {
            dialog = twoBtnDialog(poActivity, psTitle, psText, ok, "", listener, false);
        }
        dialog.findViewById(R.id.dialog_btn_cancle).setVisibility(View.INVISIBLE);
        dialog.setOnKeyListener(keylistener);
        return dialog;
    }

    public static Dialog oneBtnDialog(final Activity poActivity, String psTitle, String psText, String ok,
                                      View.OnClickListener listener, DialogInterface.OnKeyListener keylistener) {
        return oneBtnDialog(poActivity, psTitle, psText, ok, listener, keylistener, true);
    }

    /**
     * 弹出两个按钮的对话框
     *
     * @param poActivity
     * @param psTitle
     * @param psText
     * @param psPositiveName
     * @param psCancelName
     * @param listener
     * @return
     */
    public static AlertDialog twoBtnDialog(Activity poActivity, String psTitle, String psText,
                                           final String psPositiveName, final String psCancelName, final View.OnClickListener listener) {
        return twoBtnDialog(poActivity, psTitle, psText, psPositiveName, psCancelName, listener, true);
    }


    /**
     * 弹出两个按钮的对话框
     *
     * @param poActivity
     * @param psTitle
     * @param psText
     * @param psPositiveName
     * @param psCancelName
     * @param listener
     * @return
     */
    public static AlertDialog twoBtnDialog(Activity poActivity, String psTitle, String psText,
                                           final String psPositiveName, final String psCancelName, final View.OnClickListener listener, final boolean okBtnCloseDialog) {
        final AlertDialog loDialog = new AlertDialog.Builder(poActivity).create();
        LayoutInflater loInflater = poActivity.getLayoutInflater();
        loDialog.setView(loInflater.inflate(R.layout.dialog_info, null));
        loDialog.show();
        loDialog.getWindow().setContentView(R.layout.dialog_info);
        loDialog.setCancelable(false);
        TextView loTitleInfo = (TextView) loDialog.findViewById(R.id.dialog_title);
        TextView loTextInfo = (TextView) loDialog.findViewById(R.id.dialog_tv_tip);
        Button loBtnOk = (Button) loDialog.findViewById(R.id.dialog_btn_ok);
        Button loBtnCancel = (Button) loDialog.findViewById(R.id.dialog_btn_cancle);
        if (!TextUtils.isEmpty(psTitle))
            loTitleInfo.setText(psTitle);
        if (!TextUtils.isEmpty(psPositiveName))
            loTextInfo.setText(psText);
        if (!TextUtils.isEmpty(psPositiveName))
            loBtnOk.setText(psPositiveName);
        if (!TextUtils.isEmpty(psCancelName))
            loBtnCancel.setText(psCancelName);
        loBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                    if (okBtnCloseDialog) {
                        loDialog.dismiss();
                    }
                } else {
                    loDialog.dismiss();
                }
            }
        });
        loBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loDialog.dismiss();
            }
        });
        return loDialog;
    }

    public static AlertDialog twoBtnDialog(Activity poActivity, String psTitle, String psText,
                                           final String psPositiveName, final String psCancelName, final View.OnClickListener listener, final View.OnClickListener canclelistener) {
        final AlertDialog loDialog = new AlertDialog.Builder(poActivity).create();
        LayoutInflater loInflater = poActivity.getLayoutInflater();
        loDialog.setView(loInflater.inflate(R.layout.dialog_info, null));
        loDialog.show();
        loDialog.getWindow().setContentView(R.layout.dialog_info);
        loDialog.setCancelable(false);
        TextView loTitleInfo = (TextView) loDialog.findViewById(R.id.dialog_title);
        TextView loTextInfo = (TextView) loDialog.findViewById(R.id.dialog_tv_tip);
        Button loBtnOk = (Button) loDialog.findViewById(R.id.dialog_btn_ok);
        Button loBtnCancel = (Button) loDialog.findViewById(R.id.dialog_btn_cancle);
        if (!TextUtils.isEmpty(psTitle))
            loTitleInfo.setText(psTitle);
        if (!TextUtils.isEmpty(psPositiveName))
            loTextInfo.setText(psText);
        if (!TextUtils.isEmpty(psPositiveName))
            loBtnOk.setText(psPositiveName);
        if (!TextUtils.isEmpty(psCancelName))
            loBtnCancel.setText(psCancelName);
        loBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loDialog.dismiss();
                if (listener != null) {
                    listener.onClick(v);
                } else {
                    loDialog.dismiss();
                }
            }
        });
        loBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loDialog.dismiss();
                canclelistener.onClick(v);
            }
        });
        return loDialog;
    }

    public static Dialog dialog_loading(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_progress, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_loading_nodeal);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();
        return loadingDialog;

    }

    public static void closeDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
