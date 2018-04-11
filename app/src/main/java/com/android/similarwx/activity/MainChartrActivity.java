package com.android.similarwx.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.adapter.HomeAdapter;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.fragment.ExplainFragment;
import com.android.similarwx.fragment.MIFragment;
import com.android.similarwx.fragment.MyFragment;
import com.android.similarwx.fragment.NoticeFragment;
import com.android.similarwx.fragment.ServiceFragment;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/1.
 */

public class MainChartrActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    Unbinder unbinder;
    @BindView(R.id.main_search_iv)
    ImageView mainSearchIv;
    @BindView(R.id.main_search_et)
    EditText mainSearchEt;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.main_rl_explain)
    RelativeLayout mainRlExplain;
    @BindView(R.id.main_rl_chart)
    RelativeLayout mainRlChart;
    @BindView(R.id.main_my_chart)
    RelativeLayout mainMyChart;

    private HomeAdapter adapter;
    private List<GroupMessageBean> mListData;
    GroupPresent groupPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian_lt);
        unbinder = ButterKnife.bind(this);
        groupPresent=new GroupPresent();
        initData();
        adapter=new HomeAdapter(R.layout.item_group,this,mListData);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void initData() {
        mListData=groupPresent.getInitData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.main_search_iv, R.id.main_rl_explain, R.id.main_rl_chart, R.id.main_my_chart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_search_iv:

                break;
            case R.id.main_rl_explain:
                FragmentUtils.navigateToNormalActivity(this,new ExplainFragment(),null);
                break;
            case R.id.main_rl_chart:
                FragmentUtils.navigateToNormalActivity(this,new MIFragment(),null);
                break;
            case R.id.main_my_chart:
                FragmentUtils.navigateToNormalActivity(this,new MyFragment(),null);
                break;
        }
    }

    /**
     *
     * 点击群列表回调函数
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (position==0){//通知
            FragmentUtils.navigateToNormalActivity(this,new NoticeFragment(),null);
        }else  if (position==1){//在线客服
            FragmentUtils.navigateToNormalActivity(this,new ServiceFragment(),null);
        }
    }
}
