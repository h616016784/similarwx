package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.RedTakeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class MyDetailFragment extends BaseFragment {
    @BindView(R.id.my_detail_zong_tv)
    TextView myDetailZongTv;
    @BindView(R.id.my_detail_freeze_tv)
    TextView myDetailFreezeTv;
    @BindView(R.id.my_detail_start_tv)
    TextView myDetailStartTv;
    @BindView(R.id.my_detail_start_ll)
    LinearLayout myDetailStartLl;
    @BindView(R.id.my_detail_end_tv)
    TextView myDetailEndTv;
    @BindView(R.id.my_detail_end_ll)
    LinearLayout myDetailEndLl;
    @BindView(R.id.my_detail_sum_tv)
    TextView myDetailSumTv;
    @BindView(R.id.my_detail_class_tv)
    TextView myDetailClassTv;
    @BindView(R.id.my_detail_class_ll)
    LinearLayout myDetailClassLl;
    @BindView(R.id.my_detail_rv)
    RecyclerView myDetailRv;
    Unbinder unbinder;

    private BaseQuickAdapter adapter;
    private List<RedTakeBean> list;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("我的明细");
        unbinder = ButterKnife.bind(this, contentView);
        iniData();
        myDetailRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<RedTakeBean,BaseViewHolder>(R.layout.item_my_detail,list) {
            @Override
            protected void convert(BaseViewHolder helper, RedTakeBean item) {
                helper.setText(R.id.item_my_detail_name_tv,item.getRedType());
                helper.setText(R.id.item_my_detail_time_tv,item.getTime());
                helper.setText(R.id.item_my_detail_money_tv,item.getMoney());
            }
        };
        myDetailRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void iniData() {
        list=new ArrayList<>();
        RedTakeBean redTakeBean=new RedTakeBean();
        redTakeBean.setRedType("红包领取");
        redTakeBean.setTime("2017-02-12");
        redTakeBean.setMoney("+10");
        list.add(redTakeBean);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_detail_start_ll, R.id.my_detail_end_ll, R.id.my_detail_class_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_detail_start_ll:
                break;
            case R.id.my_detail_end_ll:
                break;
            case R.id.my_detail_class_ll:
                break;
        }
    }
}
