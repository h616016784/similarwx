package com.android.similarwx.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.BaseItemView;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/11.
 */

public class MyBaseFragment extends BaseFragment {
    @BindView(R.id.my_base_head_bv)
    BaseItemView myBaseHeadBv;
    @BindView(R.id.my_base_nikename_bv)
    BaseItemView myBaseNikenameBv;
    @BindView(R.id.my_base_account_bv)
    BaseItemView myBaseAccountBv;
    @BindView(R.id.my_base_sex_bv)
    BaseItemView myBaseSexBv;
    @BindView(R.id.my_base_sign_bv)
    BaseItemView myBaseSignBv;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_base;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.my_base_title);
        unbinder = ButterKnife.bind(this, contentView);
        init();
    }

    private void init() {
        myBaseHeadBv.setNameText(R.string.my_base_head);myBaseHeadBv.setImageView(R.drawable.rp_avatar);
        myBaseNikenameBv.setNameText(R.string.my_base_nick);
        myBaseAccountBv.setNameText(R.string.my_base_account);
        myBaseSexBv.setNameText(R.string.my_base_sex);
        myBaseSignBv.setNameText(R.string.my_base_sign);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_base_head_bv, R.id.my_base_nikename_bv, R.id.my_base_sex_bv, R.id.my_base_sign_bv})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.my_base_head_bv:
                showHeadDialog();
                break;
            case R.id.my_base_nikename_bv:
                bundle=new Bundle();
                bundle.putString("tag","nick");
                FragmentUtils.navigateToNormalActivity(activity,new EditFragment(),bundle);
                break;
            case R.id.my_base_sex_bv:
                showSexDialog();
                break;
            case R.id.my_base_sign_bv:
                bundle=new Bundle();
                bundle.putString("tag","sign");
                FragmentUtils.navigateToNormalActivity(activity,new EditFragment(),bundle);
                break;
        }
    }

    private void showSexDialog() {
        Dialog dialog= new TwoButtonDialogBuilder(activity)
                .setMessage("选择性别")
                .setButtonText(R.string.my_base_man,R.string.my_base_women)
                .setConfirmButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setCancelButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setCancleBackground(R.drawable.green_round_rect_btn)
                .create();
        dialog.show();
    }

    private void showHeadDialog() {
        Dialog dialog= new TwoButtonDialogBuilder(activity)
                .setMessage("选择获取图片方式")
                .setButtonText(R.string.my_base_photo,R.string.my_base_pic)
                .setConfirmButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setCancelButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setCancleBackground(R.drawable.green_round_rect_btn)
                .create();
        dialog.show();
    }
}
