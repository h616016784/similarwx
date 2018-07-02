package com.android.similarwx.adapter;

import com.android.similarwx.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

/**
 * Created by Administrator on 2018/4/6.
 */

public class NoticeAdapter2 extends BaseQuickAdapter<SystemMessage,BaseViewHolder> {

    public NoticeAdapter2(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessage item) {
        helper.setText(R.id.item_sys_notice_name_tv,item.getFromAccount());
        helper.setText(R.id.item_sys_notice_account_tv,item.getFromAccount());
        helper.setText(R.id.item_sys_notice_content_tv,"申请加入群组  "+item.getTargetId());
        if (item.getStatus().getValue()==0){//未处理
            if (item.getType().getValue()== SystemMessageType.ApplyJoinTeam.getValue()){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setGone(R.id.item_sys_notice_deny_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"拒绝");
            }
        }else {
            helper.setGone(R.id.item_sys_notice_agree_tv,true);
        }
    }
}
