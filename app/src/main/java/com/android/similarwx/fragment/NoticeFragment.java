package com.android.similarwx.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.similarwx.R;
import com.android.similarwx.adapter.NoticeAdapter;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.inteface.NoticeViewInterface;
import com.android.similarwx.present.NoticePresent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/2.
 */

public class NoticeFragment extends BaseFragment implements NoticeViewInterface{
    @BindView(R.id.notice_recycler)
    RecyclerView noticeRecycler;
    Unbinder unbinder;

    private NoticeAdapter noticeAdapter;

    private NoticePresent noticePresent;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.notice_title);
        unbinder = ButterKnife.bind(this, contentView);
        noticePresent=new NoticePresent(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        noticeRecycler.setLayoutManager(linearLayoutManager);
        noticeAdapter=new NoticeAdapter(R.layout.item_notice);
        noticeRecycler.setAdapter(noticeAdapter);

    }

    @Override
    protected void fetchData() {
        noticePresent.getNoticeList();
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

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshView(List<Notice> data) {
        noticeAdapter.addData(data);
    }
}
