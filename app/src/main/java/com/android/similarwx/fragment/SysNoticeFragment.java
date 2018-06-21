package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.outbaselibrary.primary.Log;
import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/10.
 */

public class SysNoticeFragment extends BaseFragment {

    @BindView(R.id.sys_notice_rv)
    RecyclerView sysNoticeRv;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sys_notice;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.sys_notice_title);
        unbinder = ButterKnife.bind(this, contentView);
        init();
    }

    private void init() {
// 从10条开始，查询10条系统消息
        NIMClient.getService(SystemMessageService.class).querySystemMessages(0, 100)
                .setCallback(new RequestCallback<List<SystemMessage>>() {
                    @Override
                    public void onSuccess(List<SystemMessage> param) {
                        // 查询成功
                        Log.e("onSuccess","onSuccess");
                    }

                    @Override
                    public void onFailed(int code) {
                        // 查询失败
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // error
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
