package com.android.similarwx.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class PlayerFragment extends BaseFragment {
    @BindView(R.id.my_player_rv)
    RecyclerView myPlayerRv;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_player;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.player_title);
        unbinder = ButterKnife.bind(this, contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
