package com.android.similarwx.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.NoticeAdapter;
import com.android.similarwx.adapter.NoticeAdapter2;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.inteface.NoticeViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.NoticePresent;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NIMSDK;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.SystemMessageStatus;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/2.
 */

public class NoticeFragment extends BaseFragment implements NoticeViewInterface{
    private int num=0;
    @BindView(R.id.notice_recycler)
    RecyclerView noticeRecycler;
    Unbinder unbinder;

    private NoticeAdapter2 noticeAdapter;

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
        noticePresent=new NoticePresent(this,activity);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        noticeRecycler.setLayoutManager(linearLayoutManager);
//        noticeAdapter=new NoticeAdapter2(R.layout.item_notice);
        noticeAdapter=new NoticeAdapter2(R.layout.item_sys_notice);
        noticeAdapter.setEnableLoadMore(true);
//        adapter.disableLoadMoreIfNotFullPage();
        noticeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                num++;
                fetchData();
            }
        },noticeRecycler);
        noticeRecycler.setAdapter(noticeAdapter);
        noticeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SystemMessage message=noticeAdapter.getData().get(position);
                switch (view.getId()){
                    case R.id.item_sys_notice_agree_tv:
                        if (message.getType().getValue()== SystemMessageType.ApplyJoinTeam.getValue()){

                            APIYUNXIN.passApply(message.getTargetId(), message.getFromAccount(), new YCallBack<Void>() {
                                @Override
                                public void callBack(Void aVoid) {
                                    noticePresent.doAddGroupUser(message);
                                }
                            });
                        }else if (message.getType().getValue()== SystemMessageType.TeamInvite.getValue()){

                        }

                        break;
                    case R.id.item_sys_notice_deny_tv:
                        if (message.getType().getValue()== SystemMessageType.ApplyJoinTeam.getValue()){
                            APIYUNXIN.rejectApply(message.getTargetId(), message.getFromAccount(), new YCallBack<Void>() {
                                @Override
                                public void callBack(Void aVoid) {
                                    Toaster.toastShort("已拒绝！");
                                    APIYUNXIN.setSystemMessageStatus(message.getMessageId(), SystemMessageStatus.declined);
                                    num=0;
                                    noticeAdapter.getData().clear();
                                    fetchData();
                                }
                            });
                        }else if (message.getType().getValue()== SystemMessageType.TeamInvite.getValue()){

                        }
                        break;
                }
            }
        });
        NIMClient.getService(SystemMessageService.class).resetSystemMessageUnreadCount();
    }

    @Override
    protected void fetchData() {
//        noticePresent.getNoticeList();
        APIYUNXIN.getTeamNotice(new YCallBack<List<SystemMessage>>() {
            @Override
            public void callBack(List<SystemMessage> systemMessages) {
                if (systemMessages != null && systemMessages.size() > 0) {

                    noticeAdapter.addData(systemMessages);
                    if (systemMessages.size()<20){
                        noticeAdapter.loadMoreEnd();
                    }else {
                        noticeAdapter.loadMoreComplete();
                    }


                    for (SystemMessage systemMessage:systemMessages){
                        if (systemMessage.isUnread()){
                            NIMClient.getService(SystemMessageService.class).setSystemMessageRead(systemMessage.getMessageId());
                        }
                    }
                }else {
                    noticeAdapter.loadMoreEnd();
                }
            }
        },num);
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
    public void aggreeView(SystemMessage systemMessage) {
        if (systemMessage.getType().getValue()== SystemMessageType.ApplyJoinTeam.getValue()){//申请入群
            APIYUNXIN.setSystemMessageStatus(systemMessage.getMessageId(), SystemMessageStatus.passed);
        }
        else if (systemMessage.getType().getValue()== SystemMessageType.TeamInvite.getValue()){
            APIYUNXIN.setSystemMessageStatus(systemMessage.getMessageId(), SystemMessageStatus.passed);
        }
        num=0;
        noticeAdapter.getData().clear();
        fetchData();
    }

    @Override
    public void aggreeView(String code,String groupId) {

    }
}
