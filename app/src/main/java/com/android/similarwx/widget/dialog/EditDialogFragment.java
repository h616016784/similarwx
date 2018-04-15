package com.android.similarwx.widget.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.utils.FragmentUtils;

/**
 * Created by Administrator on 2018/4/9.
 */

public class EditDialogFragment extends DialogFragment {
    private TextView mTitle;
    private TextView mConfirm;
    private TextView mCancel;
    private EditText mEditText;

    private String title;
    private ConfirmClickListener mOnConfirmClickListener;

    public static EditDialogFragment newInstance(String message) {
        return newInstance(null, message);
    }
    public static EditDialogFragment newInstance(String title, String message) {
        EditDialogFragment redDialogFragment = new EditDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=savedInstanceState.getString("title");
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_fragment_edit,container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTitle=view.findViewById(R.id.dialog_edit_title);
        mConfirm=view.findViewById(R.id.dialog_edit_confirm);
        mCancel=view.findViewById(R.id.dialog_edit_cancel);
        mEditText=view.findViewById(R.id.dialog_edit_in_et);
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
                dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
}
