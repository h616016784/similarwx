package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.BaseItemView;
import com.android.similarwx.widget.toggle.ToggleButton;

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
    @BindView(R.id.my_set_sound_set_rl)
    RelativeLayout mySetSoundSetRl;
    @BindView(R.id.my_set_sound_set_tg)
    ToggleButton mySetSoundSetTg;
    Unbinder unbinder;

    private int isClose=0;
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
        isClose=SharePreferenceUtil.getInt(activity,AppConstants.USER_SOUND_SET);
        if (isClose==1){
            mySetSoundSetTg.setToggleOn();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_set_password_bv,R.id.my_set_sound_set_tg})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.my_set_password_bv:
                FragmentUtils.navigateToNormalActivity(activity, new SetPasswordFragment(), null);
                break;
            case R.id.my_set_sound_set_tg://声音设置
                if (isClose==0){
                    isClose=1;
                    mySetSoundSetTg.setToggleOn();
                }else if (isClose==1){
                    isClose=0;
                    mySetSoundSetTg.setToggleOff();
                }
                SharePreferenceUtil.putObject(activity,AppConstants.USER_SOUND_SET,isClose);
                break;
        }

    }
}
