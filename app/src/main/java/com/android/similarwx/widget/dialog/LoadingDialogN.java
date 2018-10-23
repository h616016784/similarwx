package com.android.similarwx.widget.dialog;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;

/**
 * Created by HS on 2016/8/1.
 */
public class LoadingDialogN extends DialogFragment{
    private final static String DIALOG_TAG_LOAD = "LOADING_DIALOG";
    private TextView dialog_text;
    private ImageView progressBar;
    AnimationDrawable animationDrawable;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading_sm,container);
//        dialog_text = (TextView) view.findViewById(R.id.cip_dialog_loading_text);
//        if(null != getArguments()){
//            String title = getArguments().getString("TITLE","");
//            setDialog_text(title);
//        }else{
//            setDialog_text("正在加载...");
//        }

        progressBar = (ImageView) view.findViewById(R.id.progressBar);
        animationDrawable = (AnimationDrawable) progressBar.getDrawable();
        animationDrawable.start();
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;

        window.setAttributes(windowParams);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (animationDrawable.isRunning())
            animationDrawable.stop();
    }
    private void setDialog_text(String text){
        if(null != dialog_text){
            dialog_text.setText(text);
        }
    }

    public static void Loading_Show(FragmentManager fragmentManager,
                                     boolean cancelable) {
        DialogFragment loadingDialog = (DialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG_LOAD);
        if (loadingDialog==null){
            loadingDialog = new LoadingDialogN();
        }
//        Loading_Exit(fragmentManager);
        loadingDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TransCommonDialog);
        loadingDialog.setCancelable(cancelable);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(loadingDialog, DIALOG_TAG_LOAD);
        transaction.show(loadingDialog);
//        transaction.commit();
        transaction.commitAllowingStateLoss ();
//        fragmentManager.executePendingTransactions();
    }
    public static void Loading_Show(FragmentManager fragmentManager,
                                             boolean cancelable,String title) {
        Loading_Exit(fragmentManager);
        LoadingDialogN loadingDialog = new LoadingDialogN();
        loadingDialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TransCommonDialog);
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        loadingDialog.setArguments(bundle);
        loadingDialog.setCancelable(cancelable);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(loadingDialog, DIALOG_TAG_LOAD);
        transaction.show(loadingDialog);
        transaction.commitAllowingStateLoss();
    }

    public static void Loading_Exit(FragmentManager fragmentManager){
        DialogFragment dialog = (DialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG_LOAD);
        if (dialog != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(dialog);
            transaction.commitAllowingStateLoss();
//            transaction.commit();
        }
    }
}
