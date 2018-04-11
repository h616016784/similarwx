package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MoneyFragment extends BaseFragment {
    @BindView(R.id.my_money_friend_rl)
    RelativeLayout myMoneyFriendRl;
    @BindView(R.id.my_money_save_phone_rl)
    RelativeLayout myMoneySavePhoneRl;
    @BindView(R.id.my_money_share_circle_rl)
    RelativeLayout myMoneyShareCircleRl;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_money;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.money_title);
        unbinder = ButterKnife.bind(this, contentView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_money_friend_rl, R.id.my_money_save_phone_rl, R.id.my_money_share_circle_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_money_friend_rl://分享好友
                break;
            case R.id.my_money_save_phone_rl://保存手机
                break;
            case R.id.my_money_share_circle_rl://分享朋友圈
                break;
        }
    }
}
