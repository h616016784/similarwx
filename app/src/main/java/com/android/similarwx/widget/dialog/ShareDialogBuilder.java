package com.android.similarwx.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.inteface.WechatShareListener;

/**
 *
 * on 16-9-1.
 */
public class ShareDialogBuilder implements WechatShareListener {
    protected final BaseDialog mDialog;

    protected ViewGroup mRoot;

    private TextView mCancelBtn;

    private TextView mShareToFriend;

    private TextView mShareToCircle;

    private TextView mShareToImage;

    public ShareDialogBuilder(final Context context, final View.OnClickListener onClickSaveImage) {

        mDialog = new BaseDialog(context, getStyleResource());

        View v = LayoutInflater.from(context).inflate(getLayoutResource(), null);
        mDialog.setContentView(v);

        setGravity(Gravity.BOTTOM);


        mRoot = (ViewGroup) mDialog.findViewById(R.id.page_root);
        mCancelBtn = (TextView) mDialog.findViewById(R.id.cancel);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mShareToFriend = (TextView) mDialog.findViewById(R.id.share_wechat_friend);
        mShareToCircle = (TextView) mDialog.findViewById(R.id.share_wechat_circle);
        mShareToImage = (TextView) mDialog.findViewById(R.id.share_save_img);
        mShareToFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mShareToCircle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mShareToImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickSaveImage!=null){
                    onClickSaveImage.onClick(v);
                }
                dismiss();
            }
        });
    }

    public void hideShareToImage(){
        mShareToImage.setVisibility(View.INVISIBLE);
    }



    protected int getStyleResource() {
        return R.style.bottomDialogStyle;
    }

    protected int getLayoutResource() {
        return R.layout.dialog_wechat_share;
    }

    protected void setGravity(int gravity) {
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(gravity);
    }

    public BaseDialog create() {

        mRoot.getLayoutParams().width = AppContext.getScreenWidth();
        mRoot.requestLayout();

        return mDialog;
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    @Override
    public void shareToWechat(boolean circle) {

        // Do something when share failed.
    }

    @Override
    public void onShareInvalid() {
        // Do something when share failed.
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

}
