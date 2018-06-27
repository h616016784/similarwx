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
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.inteface.RedDetailViewInterface;
import com.android.similarwx.present.RedDetailPresent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/10.
 */

public class RedDetailFragment extends BaseFragment implements RedDetailViewInterface{
    public static String REDID="redId";
    public static String GROUPID="groupId";
    public static String SENDRED="sendRed";
    public static String GRAB="grab";
    @BindView(R.id.red_detail_name)
    TextView redDetailName;
    @BindView(R.id.red_detail_count)
    TextView redDetailCount;
    @BindView(R.id.red_detail_acount_tv)
    TextView redDetailAcountTv;
    @BindView(R.id.red_detail_rv)
    RecyclerView redDetailRv;
    Unbinder unbinder;

    private RedDetailAdapter redDetailAdapter;
    private RedDetailPresent mPresent;
    SendRed sendRed;
    RspGrabRed.GrabRedBean grabRedBean;
    String redId;
    String groupId;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_red_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.red_detail_title);
        mActionbar.setWholeBackground(R.color.colorRed2);
        unbinder = ButterKnife.bind(this, contentView);
        mPresent=new RedDetailPresent(this);

        Bundle bundle=getArguments();
        if (bundle!=null){
            redId=bundle.getString(REDID);
            groupId=bundle.getString(GROUPID);
            sendRed= (SendRed) bundle.getSerializable(SENDRED);
            grabRedBean= (RspGrabRed.GrabRedBean) bundle.getSerializable(GRAB);
            if (sendRed!=null){
//                redDetailName.setText(sendRed.get);
                redDetailCount.setText(sendRed.getData().getCotent());
            }
            if (grabRedBean!=null){
                redDetailAcountTv.setText(grabRedBean.getAmount()+"元");
            }
        }

        redDetailRv.setLayoutManager(new LinearLayoutManager(activity));
        redDetailAdapter=new RedDetailAdapter(R.layout.item_red_detial);
        redDetailRv.setAdapter(redDetailAdapter);
    }

    @Override
    protected void fetchData() {
        super.fetchData();
        mPresent.redDetailList(redId,groupId);
        initData();
    }

    @Override
    protected boolean isNeedFetch() {
        return true;

    }

    private void initData() {
//        redDetailAdapter.notifyDataSetChanged();
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
    public void refreshRedDetail(List<RedDetialBean> list) {
        redDetailAdapter.addData(list);
    }
}
