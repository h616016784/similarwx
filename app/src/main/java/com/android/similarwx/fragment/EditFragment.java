package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/11.
 */

public class EditFragment extends BaseFragment {
    String tag;
    @BindView(R.id.my_base_edit_et)
    EditText myBaseEditEt;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_edit;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        unbinder = ButterKnife.bind(this, contentView);
        int title = 0;
        mActionbar.setTitle(R.string.my_base_title);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
            if ("sign".equals(tag)){
                title = R.string.my_base_sign;
                myBaseEditEt.setHint(R.string.edit_title_in_sign);
            } else if ("nick".equals(tag)){
                title = R.string.my_base_nick;
                myBaseEditEt.setHint(R.string.edit_title_in_nick);
            }

        }
        mActionbar.setTitle(title);
        mActionbar.setRightText(R.string.edit_title);
        mActionbar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
