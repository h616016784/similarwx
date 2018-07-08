package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.BaseItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SetFragment extends BaseFragment {
    @BindView(R.id.my_set_account_bv)
    BaseItemView mySetAccountBv;
    @BindView(R.id.my_set_phone_bv)
    BaseItemView mySetPhoneBv;
    @BindView(R.id.my_set_password_bv)
    BaseItemView mySetPasswordBv;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_set;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.set_title);
        unbinder = ButterKnife.bind(this, contentView);
        init();
    }

    private void init() {
        String account= SharePreferenceUtil.getString(activity, AppConstants.USER_ACCID,"");
        String mobile= SharePreferenceUtil.getString(activity, AppConstants.USER_PHONE,"");
        mySetAccountBv.setNameText(R.string.set_account);mySetAccountBv.setRightText(account);
        mySetPhoneBv.setNameText(R.string.set_phone);mySetPhoneBv.setRightText(mobile);
        mySetPasswordBv.setNameText(R.string.set_password);mySetPasswordBv.setRightText("");mySetPasswordBv.setImageView(R.drawable.em_right);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.my_set_password_bv)
    public void onViewClicked() {
        FragmentUtils.navigateToNormalActivity(activity, new SetPasswordFragment(), null);
    }
}
