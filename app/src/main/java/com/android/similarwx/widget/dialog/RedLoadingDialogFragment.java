package com.android.similarwx.widget.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.similarwx.R;

/**
 * Created by hanhuailong on 2018/6/12.
 */

public class RedLoadingDialogFragment extends DialogFragment {
    private ImageView mImageView;
    AnimationDrawable animation;

    public static RedLoadingDialogFragment newInstance() {
        return newInstance(null, null);
    }
    public static RedLoadingDialogFragment newInstance(String title, String message) {
        RedLoadingDialogFragment redDialogFragment = new RedLoadingDialogFragment();
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
//        setStyle(R.style.dialog_tran,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_fragment_red_loading,container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mImageView=view.findViewById(R.id.dialog_red_loading_iv);
        animation = (AnimationDrawable) mImageView.getDrawable();
        animation.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (animation!=null){
            if (animation.isRunning()){
                animation.stop();
            }
        }
    }

    public static void show(Activity activity){
        RedLoadingDialogFragment redResultDialogFragment= RedLoadingDialogFragment.newInstance();
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(redResultDialogFragment,"redLoadingDialog");
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public static void disMiss(Activity activity){
        FragmentManager transaction=activity.getFragmentManager();
        Fragment prev = transaction.findFragmentByTag("fragment_dialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }
}
