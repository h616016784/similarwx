package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.ServiceItemBean;
import com.android.similarwx.utils.FragmentUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChartFragment extends BaseFragment {

    @BindView(R.id.chart_rv)
    RecyclerView chartRv;
    Unbinder unbinder;
    private BaseQuickAdapter baseQuickAdapter;
    private List<RecentContact> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        unbinder = ButterKnife.bind(this, view);
        chartRv.setLayoutManager(new LinearLayoutManager(activity));
        baseQuickAdapter=new BaseQuickAdapter<RecentContact,BaseViewHolder>(R.layout.item_chart,null) {
            @Override
            protected void convert(BaseViewHolder helper, RecentContact item) {

            }
        };
        chartRv.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               List<RecentContact> list= baseQuickAdapter.getData();
               if (list!=null && list.size()>0){
                   RecentContact recentContact=list.get(position);
                   if (recentContact!=null){
                       Bundle bundle=new Bundle();
                       bundle.putInt(MIFragment.MIFLAG,MIFragment.DELETE_THREE);
                       FragmentUtils.navigateToNormalActivity(activity,new MIFragmentNew(),bundle);
                   }
               }
            }
        });
        registerMessageObservber();
        return view;
    }

    private void registerMessageObservber() {
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> result, Throwable exception) {
                        if (result!=null && result.size()>0){
                            baseQuickAdapter.addData(result);
//                            for (RecentContact recentContact:result){
//
//                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
