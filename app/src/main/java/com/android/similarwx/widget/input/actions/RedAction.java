package com.android.similarwx.widget.input.actions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.fragment.SendRedFragment;
import com.android.similarwx.misdk.model.CustomAttachment;
import com.android.similarwx.utils.FragmentUtilsV4;
import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;
import com.netease.nim.uikit.business.session.module.Container;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class RedAction extends BaseAction {
    private MessageFragment fromFragment;
    /**
     * 构造函数
     *
     */
    public RedAction() {
        super(R.drawable.demo_reply_bar_hb, R.string.chart_red);
    }
    public RedAction(MessageFragment fromFragment) {
        super(R.drawable.demo_reply_bar_hb, R.string.chart_red);
        this.fromFragment=fromFragment;
    }

    @Override
    public void onClick() {
        Bundle bundle=new Bundle();
        FragmentUtilsV4.navigateToNormalActivityForResult(fromFragment.getActivity(),new SendRedFragment(),bundle, makeRequestCode(AppConstants.SEND_RED_REQUEST));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==AppConstants.SEND_RED_REQUEST){//发红包回调请求
            Log.e(""+AppConstants.SEND_RED_REQUEST,"发红包的结果数据");
            if(data!=null){
                SendRed redDetailBean= (SendRed) data.getSerializableExtra(AppConstants.TRANSFER_CHAT_REDENTITY);
                fromFragment.senCustemRed(redDetailBean);
//                if (redDetailBean!=null){
//                    IMMessage imMessage=createCustomMessage(redDetailBean);
//                    if (imMessage!=null)
//                        getContainer().proxy.sendMessage(imMessage);
//                }
            }

        }
    }
    protected IMMessage createCustomMessage(RedDetailBean  redDetailBean) {
        Container container=getContainer();
        String account=container.account;
        SessionTypeEnum sessionType=container.sessionType;
        return MessageBuilder.createCustomMessage(account, sessionType, "红包",
                new CustomAttachment<RedDetailBean>(MultipleItem.ITEM_RED,redDetailBean));
    }
}
