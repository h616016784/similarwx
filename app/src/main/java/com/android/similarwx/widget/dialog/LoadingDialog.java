package com.android.similarwx.widget.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.similarwx.R;

/**
 * Created by HS on 2016/8/1.
 */
public class LoadingDialog extends DialogFragment{
    private final static String DIALOG_TAG_LOAD = "LOADING_DIALOG";
    private TextView dialog_text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading,container);
        dialog_text = (TextView) view.findViewById(R.id.cip_dialog_loading_text);
        if(null != getArguments()){
            String title = getArguments().getString("TITLE","");
            setDialog_text(title);
        }else{
            setDialog_text("正在加载...");
        }

        return view;
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
            loadingDialog = new LoadingDialog();
        }
//        Loading_Exit(fragmentManager);
        loadingDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CommonDialog);
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
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CommonDialog);
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
//            transaction.commitAllowingStateLoss();
            transaction.commit();
        }
    }
}
