package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.similarwx.R;
import com.android.similarwx.adapter.NoticeAdapter;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.NoticeItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/2.
 */

public class NoticeFragment extends BaseFragment {
    @BindView(R.id.notice_recycler)
    RecyclerView noticeRecycler;
    Unbinder unbinder;

    private NoticeAdapter noticeAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.notice_title);
        unbinder = ButterKnife.bind(this, contentView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        noticeRecycler.setLayoutManager(linearLayoutManager);
        noticeAdapter=new NoticeAdapter(R.layout.item_notice);
        noticeRecycler.setAdapter(noticeAdapter);

    }

    @Override
    protected void fetchData() {
        List<NoticeItemBean> data=new ArrayList<>();
        NoticeItemBean bean=new NoticeItemBean();
        bean.setTitle("最新版本3.0");
        bean.setTime("2018-4-6 21:07");
        bean.setContent("测试的版本更新");
        bean.setContentdetail("www.test.com");
        data.add(bean);
        NoticeItemBean bean1=new NoticeItemBean();
        bean1.setTitle("最新版本3.0");
        bean1.setTime("2018-4-6 21:07");
        bean1.setContent("测试的版本更新");
        bean1.setContentdetail("www.test.com");
        data.add(bean1);
        noticeAdapter.addData(data);
    }

    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
