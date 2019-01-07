package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.HomeAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.inteface.GroupInfoViewInterface;
import com.android.similarwx.inteface.SearchViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.GroupInfoPresent;
import com.android.similarwx.present.SearchPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/8/2.
 */

public class GroupSearchFragment extends BaseFragment implements SearchViewInterface, GroupInfoViewInterface {

    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_recyclerView)
    RecyclerView searchRecyclerView;
    Unbinder unbinder;

    private BaseQuickAdapter groupAdapter;
    private List<GroupUser.ListBean> groupList;
    public RspGroupInfo.GroupInfo listBean;
    private SearchPresent present;
    private String groupId;

    private GroupInfoPresent groupInfoPresent;
    private int rows=10;
    private int pages=1;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void onInitView(View contentView) {
        mActionbar.setTitle("搜索用户");
        unbinder = ButterKnife.bind(this, contentView);
        present=new SearchPresent(this,activity);
        groupInfoPresent=new GroupInfoPresent(this,activity);
        Bundle bundle=getArguments();
        if (bundle!=null){
            groupId=bundle.getString(AppConstants.TRANSFER_ACCOUNT);
            listBean = (RspGroupInfo.GroupInfo) bundle.getSerializable(AppConstants.TRANSFER_AWARDRULE_BEAN);
        }
        groupAdapter = new BaseQuickAdapter<GroupUser.ListBean, BaseViewHolder>(R.layout.item_group_search, groupList) {
            @Override
            protected void convert(BaseViewHolder helper, GroupUser.ListBean item) {
                String icon=item.getUserIcon();
                if (!TextUtils.isEmpty(icon)){
                    NetImageUtil.glideImageNormal(activity,icon,(ImageView) helper.getView(R.id.item_group_member_iv));
                }
                helper.setText(R.id.item_group_member_tv, item.getUserName());
            }
        };
        groupAdapter.setEnableLoadMore(true);
        groupAdapter.setOnLoadMoreListener(requestLoadMoreListener,searchRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(groupAdapter);
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GroupUser.ListBean> userList =groupAdapter.getData();
                GroupUser.ListBean bean=userList.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable(AppConstants.TRANSFER_AWARDRULE,bean);
                bundle.putString(AppConstants.TRANSFER_GROUP_USER_ROLE,listBean.getGroupUserRule());
                FragmentUtils.navigateToNormalActivity(getActivity(),new ClientDetailInfoFragment(),bundle);
//                NimUIKit.startP2PSession(activity, userList.get(position).getUserId());
            }
        });
        groupInfoPresent.getGroupUserList(groupId,rows,pages);
    }

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener=new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            pages++;
            groupInfoPresent.getGroupUserList(groupId,rows,pages);
        }
    };
    @OnClick(R.id.search_iv)
    public void onViewClicked() {
        pages=1;
        String content=searchEt.getText().toString();
        if (TextUtils.isEmpty(content)){
            Toaster.toastShort("搜索内容不能为空！");
            return;
        }
        present.searchUser(groupId,content);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void refreshSearchUser() {

    }

    @Override
    public void refreshSearchUser(User user) {

    }

    @Override
    public void refreshSearchUser(GroupUser groupUser) {
        if (groupUser!=null){
            List<GroupUser.ListBean> userList = groupUser.getList();
            if (userList!=null && userList.size()>0){
                groupAdapter.getData().clear();
                groupAdapter.addData(userList);
            }
        }
    }

    @Override
    public void showErrorMessage(String err) {
        groupAdapter.loadMoreComplete();
    }

    @Override
    public void refreshUserlist(GroupUser groupUser) {
        groupAdapter.loadMoreComplete();
        if (groupUser!=null){
            int count=groupUser.getCount();
            if (rows*pages >= count) {
                //数据全部加载完毕
                groupAdapter.loadMoreEnd();
            }
            List<GroupUser.ListBean> userList = groupUser.getList();
            if (userList!=null && userList.size()>0)
                groupAdapter.addData(userList);
        }
    }

    @Override
    public void refreshDeleteGroup() {

    }
}
