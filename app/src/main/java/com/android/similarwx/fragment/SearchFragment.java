package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.HomeAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.inteface.SearchViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.SearchPresent;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/7/31.
 */

public class SearchFragment extends BaseFragment implements SearchViewInterface {

    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_recyclerView)
    RecyclerView searchRecyclerView;
    Unbinder unbinder;

    private List<GroupMessageBean.ListBean> mListData;
    private HomeAdapter adapter;
    private SearchPresent present;
    private int tag=1;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void onInitView(View contentView) {
        mActionbar.setTitle("搜索用户或群组");
        unbinder = ButterKnife.bind(this, contentView);
        present=new SearchPresent(this);
        adapter = new HomeAdapter(R.layout.item_group, activity, mListData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(adapter);

        Bundle bundle=getArguments();
        if (bundle!=null){
            tag=bundle.getInt(AppConstants.TRANSFER_BASE);
            if (tag==1){

            }else if (tag==2){

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.search_iv)
    public void onViewClicked() {
        String content=searchEt.getText().toString();
        if (TextUtils.isEmpty(content)){
            Toaster.toastShort("搜索内容不能为空！");
            return;
        }
        if (tag==1){
//            present.searchUser(content);
            APIYUNXIN.searchTeam(content, new YCallBack<Team>() {
                @Override
                public void callBack(Team team) {
                    if (team!=null){

                    }
                }
            });
        }else if (tag==2){
            APIYUNXIN.searchUser(content, new YCallBack<List<NimUserInfo>>() {
                @Override
                public void callBack(List<NimUserInfo> nimUserInfos) {
                    if (nimUserInfos!=null){

                    }
                }
            });
        }

    }

    @Override
    public void refreshSearchUser() {

    }

    @Override
    public void showErrorMessage(String err) {

    }
}
