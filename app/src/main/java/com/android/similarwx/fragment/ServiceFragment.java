package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.adapter.ServiceAdapter;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.ServiceItemBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/2.
 */

public class ServiceFragment extends BaseFragment {
    @BindView(R.id.service_recycler)
    RecyclerView serviceRecycler;
    Unbinder unbinder;

    private ServiceAdapter serviceAdapter;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_service;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.service_title);
        unbinder = ButterKnife.bind(this, contentView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        serviceRecycler.setLayoutManager(linearLayoutManager);
        serviceAdapter=new ServiceAdapter(R.layout.item_service,activity);
        serviceRecycler.setAdapter(serviceAdapter);

        ImageView imageView=contentView.findViewById(R.id.imageView);
//        Glide.with(this).load("http://p1.pstatp.com/large/166200019850062839d3").into(imageView);
    }

    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    @Override
    protected void fetchData() {
       List<ServiceItemBean> data=new ArrayList<>();
       ServiceItemBean serviceItemBean=new ServiceItemBean();
       serviceItemBean.setRole("群主");
       serviceItemBean.setContent("管家（在线）");
       serviceItemBean.setImageUrl("http://p1.pstatp.com/large/166200019850062839d3");
       data.add(serviceItemBean);
       serviceAdapter.addData(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
