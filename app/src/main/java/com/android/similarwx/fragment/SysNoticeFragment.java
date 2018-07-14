package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupRule;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.model.API;
import com.android.similarwx.present.SysNoticePresent;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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

public class SysNoticeFragment extends BaseFragment implements SysNoticeViewInterface {

    @BindView(R.id.sys_notice_rv)
    RecyclerView sysNoticeRv;
    Unbinder unbinder;
    private BaseQuickAdapter adapter;
    private SysNoticePresent present;
    private String tag;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sys_notice;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        Bundle bundle=getArguments();
        if (bundle!=null)
            tag=bundle.getString(AppConstants.TRANSFER_BASE);
        if (TextUtils.isEmpty(tag))
            mActionbar.setTitle(R.string.sys_notice_title);
        else if(tag.equals("contract")){
            mActionbar.setTitle("联系群主");
        }else
            mActionbar.setTitle(R.string.sys_notice_title);
        unbinder = ButterKnife.bind(this, contentView);
        present=new SysNoticePresent(this);
        init();
    }

    private void init() {
        sysNoticeRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<Notice,BaseViewHolder>(R.layout.item_notice,null){

            @Override
            protected void convert(BaseViewHolder helper, Notice item) {
                String content=item.getContent();
                helper.setText(R.id.notice_item_title,item.getTitle());
                helper.setText(R.id.notice_item_time,item.getModifyDate());
                helper.setText(R.id.notice_item_content,item.getRemark());
                if (!TextUtils.isEmpty(content)){
                    String text=Html.fromHtml(content).toString();
                    helper.setText(R.id.notice_item_content_detail,text);
                }
            }
        };
        sysNoticeRv.setAdapter(adapter);

        if (TextUtils.isEmpty(tag))
            present.getNotice();
        else if(tag.equals("contract"))
            present.getContract();
        else
            present.getNotice();
    }

    @Override
    protected void fetchData() {
        super.fetchData();
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

    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    @Override
    public void refreshSysNotice(List<Notice> list) {
        if (list!=null){
            adapter.addData(list);
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }
}
