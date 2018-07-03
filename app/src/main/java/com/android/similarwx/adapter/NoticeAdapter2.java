package com.android.similarwx.adapter;

import com.android.similarwx.R;
import com.android.similarwx.beans.User;
import com.android.similarwx.utils.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
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
        Gson gson=new Gson();
        User user = null;
        String content=item.getContent();
        try {
            user =gson.fromJson(content, User.class);
        }catch (Exception e){

        }
        if (user!=null){
            helper.setText(R.id.item_sys_notice_name_tv,user.getName());
            helper.setText(R.id.item_sys_notice_content_tv,user.getPasswd()+"  "+user.getPasswdStr());
        }

        helper.setText(R.id.item_sys_notice_account_tv,item.getFromAccount());
        helper.setText(R.id.item_sys_notice_time_tv, TimeUtil.timestampToString(item.getTime()));

        // 用户申请加入群组通知。群内所有管理员会收到该系统通知
        if (item.getType().getValue()== SystemMessageType.ApplyJoinTeam.getValue()){
            if (item.getStatus().getValue()==0){//未处理
                helper.setGone(R.id.item_sys_notice_agree_tv,true);//显示
                helper.setText(R.id.item_sys_notice_agree_tv,"同意");
                helper.setGone(R.id.item_sys_notice_deny_tv,true);
                helper.setText(R.id.item_sys_notice_deny_tv,"拒绝");
                helper.addOnClickListener(R.id.item_sys_notice_agree_tv);
                helper.addOnClickListener(R.id.item_sys_notice_deny_tv);
            }else if (item.getStatus().getValue()==1){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已通过验证");
            }else if (item.getStatus().getValue()==2){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已拒绝");
            }else if (item.getStatus().getValue()==3){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已忽略");
            }else if (item.getStatus().getValue()==4){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已过期");
            }
        }

        //管理员拒绝用户入群申请。申请入群的用户会收到该通知
        else if (item.getType().getValue()== SystemMessageType.RejectTeamApply.getValue()){
            if (item.getStatus().getValue()==0){//未处理
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"未处理");
            }else if (item.getStatus().getValue()==1){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已通过验证");
            }else if (item.getStatus().getValue()==2){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已拒绝");
            }else if (item.getStatus().getValue()==3){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已忽略");
            }else if (item.getStatus().getValue()==4){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已过期");
            }
        }

        //管理员邀请其他用户加入群组的系统通知。所有被邀请的用户会收到该通知
        else if (item.getType().getValue()== SystemMessageType.TeamInvite.getValue()){
            if (item.getStatus().getValue()==0){//未处理
                helper.setGone(R.id.item_sys_notice_agree_tv,true);//显示
                helper.setText(R.id.item_sys_notice_agree_tv,"同意");
                helper.setGone(R.id.item_sys_notice_deny_tv,true);
                helper.setText(R.id.item_sys_notice_deny_tv,"拒绝");
                helper.addOnClickListener(R.id.item_sys_notice_agree_tv);
                helper.addOnClickListener(R.id.item_sys_notice_deny_tv);
            }else if (item.getStatus().getValue()==1){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已通过验证");
            }else if (item.getStatus().getValue()==2){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已拒绝");
            }else if (item.getStatus().getValue()==3){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已忽略");
            }else if (item.getStatus().getValue()==4){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已过期");
            }
        }
        //用户拒绝入群邀请的系统通知。邀请该用户入群的管理员会收到该通知。
        else if (item.getType().getValue()== SystemMessageType.TeamInvite.getValue()){
            if (item.getStatus().getValue()==0){//未处理
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"未处理");
            }else if (item.getStatus().getValue()==1){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已通过验证");
            }else if (item.getStatus().getValue()==2){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已拒绝");
            }else if (item.getStatus().getValue()==3){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已忽略");
            }else if (item.getStatus().getValue()==4){
                helper.setGone(R.id.item_sys_notice_agree_tv,false);
                helper.setText(R.id.item_sys_notice_deny_tv,"已过期");
            }
        }
    }
}
