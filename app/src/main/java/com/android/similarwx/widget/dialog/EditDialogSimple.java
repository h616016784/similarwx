package com.android.similarwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.android.similarwx.R;

/**
 * Created by Administrator on 2018/4/15.
 */

public class EditDialogSimple {
    private TextView mTitle;
    private TextView mConfirm;
    private TextView mCancel;
    private EditText mEditText;

    private String title;
    private ConfirmClickListener mOnConfirmClickListener;
    private Dialog mDialog;
    private int inputType=0;//0是默认 ，1是带小数点的数，2是纯数字
    public EditDialogSimple(Context context,String title,int inputType){
        this.inputType=inputType;
        this.title=title;
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView     = inflater.inflate(R.layout.dialog_fragment_edit, null);
        mDialog.setContentView(dialogView);
        mDialog.setCancelable(false);
        initDialogView(dialogView);
    }
    public EditDialogSimple(Context context,String title){
        this.title=title;
        //1.创建一个Dialog对象，如果是AlertDialog对象的话，弹出的自定义布局四周会有一些阴影，效果不好
        mDialog = new Dialog(context);
        //去除标题栏
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //2.填充布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView     = inflater.inflate(R.layout.dialog_fragment_edit, null);
        //将自定义布局设置进去
        mDialog.setContentView(dialogView);
        //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
//        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
//        Window                     window = mDialog.getWindow();
//        lp.copyFrom(window.getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果

//        window.setAttributes(lp);

        //设置点击其它地方不让消失弹窗
        mDialog.setCancelable(false);
        initDialogView(dialogView);
    }
    public void show(){
        mDialog.show();
    }

    private void initDialogView(View dialogView) {
        mTitle=dialogView.findViewById(R.id.dialog_edit_title);
        mConfirm=dialogView.findViewById(R.id.dialog_edit_confirm);
        mCancel=dialogView.findViewById(R.id.dialog_edit_cancel);
        mEditText=dialogView.findViewById(R.id.dialog_edit_in_et);
        if (inputType==1){
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            mEditText.addTextChangedListener(textWatcher);
        }

        else if (inputType==2)
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mTitle.setText(title);
        addListener();
    }

    private void addListener() {
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=mEditText.getText().toString();
                if(mOnConfirmClickListener!=null)
                    mOnConfirmClickListener.onClickListener(text);
                mDialog.dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }
    public void setOnConfirmClickListener(ConfirmClickListener confirmClickListener){
        this.mOnConfirmClickListener=confirmClickListener;
    }
    public void setTitle(String title){
        if(mTitle!=null)
            mTitle.setText(title);
    }
    public interface ConfirmClickListener{
        void onClickListener(String text);
    }
    public void dimiss(){
        if(mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
    }

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            int posDot = temp.indexOf(".");
            if (posDot <= 0) return;
            if (temp.length() - posDot - 1 > 2)
            {
                s.delete(posDot + 3, posDot + 4);
            }
        }
    };
}
