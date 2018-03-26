package com.android.similarwx.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.android.similarwx.R;

/**
 * Created by Albert
 * on 16-6-2.
 */
public class AlertDialogFragment extends DialogFragment {

    public static AlertDialogFragment newInstance(String message) {
        return newInstance(null, message);
    }

    public static AlertDialogFragment newInstance(String title, String message) {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        alertDialogFragment.setArguments(bundle);
        return alertDialogFragment;
    }

    public static AlertDialogFragment newInstance(String title, String message, int buttonTextId) {
        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        bundle.putInt("button", buttonTextId);
        alertDialogFragment.setArguments(bundle);
        return alertDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        Bundle argumentBundle = getArguments();
        String message = argumentBundle.getString("message");
        String title = null;
        int buttonTextId = R.string.cancel;
        if (argumentBundle.containsKey("title")) {
            title = argumentBundle.getString("title");
        }
        if (argumentBundle.containsKey("button")) {
            buttonTextId = argumentBundle.getInt("button");
        }

        DialogBuilder uschoolDialogBuilder = new DialogBuilder(getActivity())
                .setMessage(message);

        if (title != null) {
            uschoolDialogBuilder.setTitle(title);
        }

        return uschoolDialogBuilder.setPositiveButton(buttonTextId,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
