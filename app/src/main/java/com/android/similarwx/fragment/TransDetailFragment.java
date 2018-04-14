package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class TransDetailFragment extends BaseFragment {
    @BindView(R.id.trans_detail_iv)
    ImageView transDetailIv;
    @BindView(R.id.trans_detail_tile)
    TextView transDetailTile;
    @BindView(R.id.trans_detail_money)
    TextView transDetailMoney;
    @BindView(R.id.trans_detail_turn)
    TextView transDetailTurn;
    @BindView(R.id.trans_detail_state)
    TextView transDetailState;
    @BindView(R.id.trans_detail_time)
    TextView transDetailTime;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_trans_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.trans_detail_title);
        unbinder = ButterKnife.bind(this, contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
