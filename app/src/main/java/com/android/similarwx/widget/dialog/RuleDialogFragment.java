package com.android.similarwx.widget.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.beans.GroupRule;

/**
 * Created by hanhuailong on 2018/6/12.
 */

public class RuleDialogFragment extends DialogFragment implements View.OnClickListener {
    private EditText dialog_create_group_rule_name_et,dialog_create_group_rule_grab_et,dialog_create_group_rule_get_et;
    private Button mCreatGroup;

    private OnConfirmClickListener mClickListener;
    public static RuleDialogFragment newInstance() {
        return newInstance(null, null);
    }
    public static RuleDialogFragment newInstance(String title, String message) {
        RuleDialogFragment redDialogFragment = new RuleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
//        setStyle(R.style.,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_creat_group_rule,container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        dialog_create_group_rule_name_et=view.findViewById(R.id.dialog_create_group_rule_name_et);
        dialog_create_group_rule_grab_et=view.findViewById(R.id.dialog_create_group_rule_grab_et);
        dialog_create_group_rule_get_et=view.findViewById(R.id.dialog_create_group_rule_get_et);
        mCreatGroup=view.findViewById(R.id.dialog_create_group_rule_bt);
        mCreatGroup.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void show(Activity activity,OnConfirmClickListener listener){
        RuleDialogFragment redResultDialogFragment= RuleDialogFragment.newInstance();
        if (listener!=null){
            redResultDialogFragment.setOnConfirmClickListener(listener);
        }
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(redResultDialogFragment,"ruleDialog");
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public static void disMiss(Activity activity){
        FragmentManager transaction=activity.getFragmentManager();
        Fragment prev = transaction.findFragmentByTag("ruleDialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener){
        this.mClickListener=listener;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_create_group_rule_bt:
                String name=dialog_create_group_rule_name_et.getText().toString();
                String grab=dialog_create_group_rule_grab_et.getText().toString();
                String back=dialog_create_group_rule_get_et.getText().toString();
                if (TextUtils.isEmpty(name))
                    Toaster.toastShort("规则名称不能为空！");
                if (TextUtils.isEmpty(grab))
                    Toaster.toastShort("抢到金额不能为空！");
                if (TextUtils.isEmpty(back))
                    Toaster.toastShort("奖励金额不能为空！");

                GroupRule bean=new GroupRule();
                bean.setName(name);
                bean.setGrab(grab);
                bean.setBack(back);
                if (mClickListener!=null){
                    mClickListener.onConfirmClickListener(bean);
                }
                dismiss();
                break;
        }
    }
    public interface OnConfirmClickListener {
        void onConfirmClickListener(GroupRule groupRule);
    }

}
