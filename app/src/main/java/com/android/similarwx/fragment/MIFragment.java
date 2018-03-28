package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.similarwx.R;
import com.android.similarwx.adapter.NormalAdapter;
import com.android.similarwx.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/3/28.
 */

public class MIFragment extends BaseFragment {
    List<String> list;
    @BindView(R.id.mi_recyclerView)
    RecyclerView miRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mi_button)
    Button miButton;
    @BindView(R.id.mi_remove)
    Button miRemove;

    private NormalAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mi_layout;
    }

    @Override
    protected void onInitView(View contentView) {
        ButterKnife.bind(this, contentView);
        List<String> data = initData();
        adapter = new NormalAdapter(data);
        miRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
//        miRecyclerView.addItemDecoration(new BaseDecoration());
//        miRecyclerView.setLayoutManager(new GridLayoutManager(activity,3));
        miRecyclerView.setItemAnimator(new DefaultItemAnimator());
        miRecyclerView.setAdapter(adapter);
    }

    private List<String> initData() {
        list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("h");
        list.add("i");
        list.add("g");
        list.add("k");
        list.add("l");
        list.add("m");
        list.add("n");
        return list;
    }


    @OnClick({R.id.mi_button, R.id.mi_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mi_button:
                adapter.addData("abc");
                break;
            case R.id.mi_remove:

                break;
        }
    }
    @Override
    protected void fetchData() {
        super.fetchData();
    }

    /**
     * 如果需要创建fragment就加载数据的话 此方法返回true，就会调用fetchData方法
     *
     * @return
     */
    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
