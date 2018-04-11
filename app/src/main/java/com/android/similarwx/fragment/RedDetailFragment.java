package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.adapter.RedDetailAdapter;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.RedDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/10.
 */

public class RedDetailFragment extends BaseFragment {
    @BindView(R.id.red_detail_name)
    TextView redDetailName;
    @BindView(R.id.red_detail_count)
    TextView redDetailCount;
    @BindView(R.id.red_detail_rv)
    RecyclerView redDetailRv;
    Unbinder unbinder;

    private RedDetailAdapter redDetailAdapter;
    private List<RedDetailBean> list;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_red_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.red_detail_title);
        mActionbar.setWholeBackground(R.color.color_red_ccfa3c55);
        unbinder = ButterKnife.bind(this, contentView);

        redDetailRv.setLayoutManager(new LinearLayoutManager(activity));
        redDetailAdapter=new RedDetailAdapter(R.layout.item_red_detial);
        redDetailRv.setAdapter(redDetailAdapter);


    }

    @Override
    protected void fetchData() {
        super.fetchData();
        initData();
    }

    @Override
    protected boolean isNeedFetch() {
        return true;

    }

    private void initData() {
        list=new ArrayList<>();
        RedDetailBean redDetailBean=new RedDetailBean();
        redDetailBean.setName("haha");
        redDetailBean.setMoney("12元");
        redDetailBean.setTime("15:23");
        redDetailBean.setShouqi("最佳手气");
        list.add(redDetailBean);
        redDetailAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
