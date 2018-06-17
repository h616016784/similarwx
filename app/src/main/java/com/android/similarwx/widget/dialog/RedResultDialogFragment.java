package com.android.similarwx.widget.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.utils.FragmentUtils;

/**
 * Created by Administrator on 2018/4/9.
 */

public class RedResultDialogFragment extends DialogFragment implements View.OnClickListener {
    public static RedResultDialogFragment newInstance(RspGrabRed.GrabRedBean bean) {
        RedResultDialogFragment redDialogFragment = new RedResultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("isGood", bean);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    public static RedResultDialogFragment newInstance(String title, String message) {
        RedResultDialogFragment redDialogFragment = new RedResultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    private ImageView dialog_red_result_cancel_iv;
    private ImageView dialog_red_result_kai_tv;
    private TextView dialog_red_result_bottom_tv;
    private TextView dialog_red_result_name_tv;
    private TextView dialog_red_result_tips_tv;

    private static OnOpenClick mClicker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_red_result,container);
        initView(view);
        addClickListener();
        return view;
    }

    private void initView(View view) {
        dialog_red_result_cancel_iv=view.findViewById(R.id.dialog_red_result_cancel_iv);
        dialog_red_result_bottom_tv=view.findViewById(R.id.dialog_red_result_bottom_tv);
        dialog_red_result_name_tv=view.findViewById(R.id.dialog_red_result_name_tv);
        dialog_red_result_tips_tv=view.findViewById(R.id.dialog_red_result_tips_tv);
        dialog_red_result_kai_tv=view.findViewById(R.id.dialog_red_result_kai_tv);

        Bundle bundle=getArguments();
        if (bundle!=null){
            RspGrabRed.GrabRedBean bean= (RspGrabRed.GrabRedBean) bundle.getSerializable("isGood");
            if (bean!=null){
                String code = bean.getRetCode();
                if (code.equals("0000")) {
                    dialog_red_result_kai_tv.setVisibility(View.VISIBLE);
                } else {
                    dialog_red_result_kai_tv.setVisibility(View.GONE);
                }
            }
        }
    }
    private void addClickListener() {
        dialog_red_result_cancel_iv.setOnClickListener(this);
        dialog_red_result_bottom_tv.setOnClickListener(this);
        dialog_red_result_kai_tv.setOnClickListener(this);
    }

    public static void show(Activity activity,RspGrabRed.GrabRedBean bean,OnOpenClick onOpenClick){
        mClicker=onOpenClick;
        RedResultDialogFragment redResultDialogFragment= RedResultDialogFragment.newInstance(bean);
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(redResultDialogFragment,"redResultDialog");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_red_result_cancel_iv:
                dismiss();
                break;
            case R.id.dialog_red_result_bottom_tv:
                Bundle bundle=new Bundle();
                FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),bundle);
                dismiss();
                break;
            case R.id.dialog_red_result_kai_tv://开红包
                if (mClicker!=null)
                    mClicker.onOpenClick();
//                dismiss();
                break;
        }
    }
    public interface OnOpenClick{
        void onOpenClick();
    }
}
