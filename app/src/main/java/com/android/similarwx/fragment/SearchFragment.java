package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.inteface.NoticeViewInterface;
import com.android.similarwx.inteface.SearchViewInterface;
import com.android.similarwx.inteface.SendRedViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.NoticePresent;
import com.android.similarwx.present.SearchPresent;
import com.android.similarwx.present.SendRedPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.android.similarwx.widget.dialog.CancelDialogBuilder;
import com.android.similarwx.widget.dialog.EasyAlertDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/7/31.
 */

public class SearchFragment extends BaseFragment implements SearchViewInterface, SendRedViewInterface, NoticeViewInterface {

    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_recyclerView)
    RecyclerView searchRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.search_user_iv)
    ImageView searchUserIv;
    @BindView(R.id.search_user_ll)
    LinearLayout searchUserLl;
    @BindView(R.id.search_user_tv)
    TextView searchUserTv;

    private List<RspGroupInfo.GroupInfo> mListData;
    private BaseQuickAdapter adapter;
    private SearchPresent present;
    private int tag = 1;
    private SendRedPresent sendRedPresent;
    private NoticePresent noticePresent;
    private SearchPresent searchPresent;
    private User mUser;
    private String userId;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void onInitView(View contentView) {
        mActionbar.setTitle("搜索群组");
        unbinder = ButterKnife.bind(this, contentView);
        present = new SearchPresent(this);
        sendRedPresent = new SendRedPresent(this);
        noticePresent = new NoticePresent(this);
        searchPresent = new SearchPresent(this);
        userId = SharePreferenceUtil.getString(activity, AppConstants.USER_ACCID, "");
        mUser = (User) SharePreferenceUtil.getSerializableObjectDefault(activity, AppConstants.USER_OBJECT);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt(AppConstants.TRANSFER_BASE);
            if (tag == 1) {
                mActionbar.setTitle("搜索用户");
            } else if (tag == 2) {
                mActionbar.setTitle("搜索群组");
                searchEt.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        }
        adapter = new BaseQuickAdapter<RspGroupInfo.GroupInfo, BaseViewHolder>(R.layout.item_group, null) {
            @Override
            protected void convert(BaseViewHolder helper, RspGroupInfo.GroupInfo item) {
                helper.setGone(R.id.item_group_count_tv, false);//隐藏这个控件
                helper.setText(R.id.item_group_tv, item.getGroupName());
                String groupIcon = item.getGroupIcon();
                if (!TextUtils.isEmpty(groupIcon)) {
                    NetImageUtil.glideImageNormal(activity, groupIcon, (ImageView) helper.getView(R.id.item_group_iv));
                }
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final RspGroupInfo.GroupInfo bean = (RspGroupInfo.GroupInfo) adapter.getData().get(position);
                doClick(bean);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.search_user_ll,R.id.search_iv})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.search_iv:
                String content = searchEt.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toaster.toastShort("搜索内容不能为空！");
                    return;
                }
                if (tag == 2) {
                    sendRedPresent.getGroupByIdOrGroupId(userId, content);
                } else if (tag == 1) {
                    searchPresent.searchUser(content);
                }
                break;
            case R.id.search_user_ll:

                break;
        }
    }

    private void doClick(RspGroupInfo.GroupInfo bean) {
        if (bean.getUserExists().equals("0")) {//不在群里
            //这回真的不在群里面了
            String joinmode = bean.getJoinmode();
            if (!TextUtils.isEmpty(joinmode)) {
                if (joinmode.equals("0")) {//允许任何人加入
                    doInGroupByAnyOne(bean.getGroupId());
                } else if (joinmode.equals("1")) {
                    EasyAlertDialog mDialog = EasyAlertDialogHelper.createOkCancelDiolag(activity, bean.getGroupName(), "是否加入该群?", "是", "否", true, new EasyAlertDialogHelper.OnDialogActionListener() {
                        @Override
                        public void doCancelAction() {

                        }

                        @Override
                        public void doOkAction() {
                            Gson gson = new Gson();
                            mUser.setPasswd("申请加入该群");
                            mUser.setPasswdStr(bean.getGroupName());
                            Map<String, String> map = SharePreferenceUtil.getHashMapData(activity, AppConstants.USER_MAP_OBJECT);
                            if (map != null) {
                                String applyFlag = map.get(bean.getGroupId());
                                if (TextUtils.isEmpty(applyFlag)) {
                                    APIYUNXIN.applyJoinTeam(activity, bean.getGroupId(), "", new YCallBack<Team>() {
                                        @Override
                                        public void callBack(Team team) {
                                            Toaster.toastShort("申请成功，等待群主审批");
                                        }
                                    });
                                } else {
                                    showQuitDialog("已经发过申请，请等待");
                                }
                            }

                        }
                    });
                    mDialog.show();
                } else {

                }
            }
        } else {
            NimUIKit.startTeamSession(activity, bean.getGroupId());
        }
    }

    private void doInGroupByAnyOne(String groupId) {
        String accid = SharePreferenceUtil.getString(activity, AppConstants.USER_ACCID, "");
        noticePresent.doAddGroupUser(groupId, accid);
    }

    /**
     * 展示申请信息
     *
     * @param msg
     */
    private void showQuitDialog(String msg) {
        final CancelDialogBuilder cancel_dialogBuilder = CancelDialogBuilder
                .getInstance(activity);

        cancel_dialogBuilder.setTitleText(msg);
        cancel_dialogBuilder.setDetermineText("确定");

        cancel_dialogBuilder.isCancelableOnTouchOutside(true)
                .setCancelNone()
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_dialogBuilder.dismiss();
                    }
                }).show();
    }

    @Override
    public void refreshSearchUser() {

    }

    @Override
    public void refreshSearchUser(User user) {
        if (user != null) {
            searchUserLl.setVisibility(View.VISIBLE);
            searchUserTv.setText(user.getName());
            String icon = user.getIcon();
            if (!TextUtils.isEmpty(icon)) {
                NetImageUtil.glideImageNormal(activity, icon, searchUserIv);
            }
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshSendRed(RspGroupInfo.GroupInfo bean) {
        if (bean != null) {
            adapter.addData(bean);
        }
    }

    @Override
    public void aggreeView(SystemMessage systemMessage) {

    }

    @Override
    public void aggreeView(String code, String groupId) {
        if (!TextUtils.isEmpty(code)) {
            if (code.equals("0000")) {//添加成功或者
                APIYUNXIN.applyJoinTeam(activity, groupId, "", new YCallBack<Team>() {
                    @Override
                    public void callBack(Team team) {
                        // 打开群聊界面
                        NimUIKit.startTeamSession(activity, groupId);
                    }
                });
            } else if (code.equals("2045")) {//以在群里了
                // 打开群聊界面
                NimUIKit.startTeamSession(activity, groupId);
            } else {
                Toaster.toastShort("异常操作!!");
            }
        }
    }

}


