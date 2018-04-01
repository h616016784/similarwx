package com.android.similarwx.activity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/1.
 */

public class MainChartrActivity extends BaseActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian_lt);
        unbinder = ButterKnife.bind(this);
        initData();
        adapter=new HomeAdapter(this,recyclerView,R.layout.item_group,mListData);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        List<GroupMessageBean> mListData=new ArrayList<>();
        GroupMessageBean bean=new GroupMessageBean();
        bean.setName(AppContext.getString(R.string.main_notice));
        mListData.add(bean);
        GroupMessageBean bean1=new GroupMessageBean();
        bean1.setName(AppContext.getString(R.string.main_online_answer));
        mListData.add(bean1);
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
                break;
            case R.id.main_rl_chart:
                break;
            case R.id.main_my_chart:
                break;
        }
    }
}
