package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.BaseAdapter;
import com.android.similarwx.adapter.MIMultiItemQuickAdapter;
import com.android.similarwx.adapter.NormalAdapter;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.MIMultiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
    List<MIMultiItem> list;
    @BindView(R.id.mi_recyclerView)
    RecyclerView miRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.mi_button)
    Button miButton;
    @BindView(R.id.mi_remove)
    Button miRemove;

    private MIMultiItemQuickAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mi_layout;
    }

    @Override
    protected void onInitView(View contentView) {
        ButterKnife.bind(this, contentView);
        List<MIMultiItem> data = initData();
//        adapter = new BaseAdapter(R.layout.item_test,data);
        adapter=new MIMultiItemQuickAdapter(data);
        miRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
//        miRecyclerView.addItemDecoration(new BaseDecoration());
//        miRecyclerView.setLayoutManager(new GridLayoutManager(activity,3));
//        miRecyclerView.setItemAnimator(new DefaultItemAnimator());
        miRecyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toaster.toastShort("点击了"+position+"..."+((TextView)view).getText().toString());
            }
        });
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setUpFetchEnable(true);
        adapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                adapter.setUpFetching(true);
                Toaster.toastShort("下拉加载了！");
                adapter.setUpFetching(false);
            }
        });
    }

//    private List<String> initData() {
//        list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.add("e");
//        list.add("f");
//        list.add("h");
//        list.add("i");
//        list.add("g");
//        list.add("k");
//        list.add("l");
//        list.add("m");
//        list.add("n");
//        return list;
//    }
    private List<MIMultiItem> initData() {
        list = new ArrayList<>();
        list.add(new MIMultiItem(1));
        list.add(new MIMultiItem(0));
        list.add(new MIMultiItem(0));
        list.add(new MIMultiItem(1));
        list.add(new MIMultiItem(1));
        list.add(new MIMultiItem(1));
        list.add(new MIMultiItem(0));
        list.add(new MIMultiItem(1));
        list.add(new MIMultiItem(0));
        list.add(new MIMultiItem(1));
        list.add(new MIMultiItem(1));

        return list;
    }

    @OnClick({R.id.mi_button, R.id.mi_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mi_button:
//                adapter.addData("abc");

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
