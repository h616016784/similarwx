package com.android.similarwx.widget.dialog;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.base.NormalActivity;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.utils.FragmentUtils;

/**
 * Created by Administrator on 2018/4/9.
 */

public class RedDialogFragment extends DialogFragment {
    public static RedDialogFragment newInstance(String message) {
        return newInstance(null, message);
    }
    public static RedDialogFragment newInstance(String title, String message) {
        RedDialogFragment redDialogFragment = new RedDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_fragment_edit,container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ImageView imageView=view.findViewById(R.id.dialog_red_head_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),null);
                dismiss();
            }
        });
    }
}
