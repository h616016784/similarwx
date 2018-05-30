package com.android.similarwx.widget.input.actions;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.fragment.MIFragment;
import com.android.similarwx.fragment.MIFragmentNew;
import com.android.similarwx.fragment.SendRedFragment;
import com.android.similarwx.misdk.model.CustomAttachment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.input.module.Container;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class RedAction extends BaseAction {
    private Fragment fromFragment;
    /**
     * 构造函数
     *
     */
    public RedAction() {
        super(R.drawable.demo_reply_bar_hb, R.string.chart_red);
    }
    public RedAction(Fragment fromFragment) {
        super(R.drawable.demo_reply_bar_hb, R.string.chart_red);
        this.fromFragment=fromFragment;
    }

    @Override
    public void onClick() {
        Bundle bundle=new Bundle();
        FragmentUtils.navigateToNormalActivityForResult(fromFragment,new SendRedFragment(),bundle, makeRequestCode(AppConstants.SEND_RED_REQUEST));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==AppConstants.SEND_RED_REQUEST){//发红包回调请求
            Log.e(""+AppConstants.SEND_RED_REQUEST,"发红包的结果数据");
            if(data!=null){
                RedDetailBean redDetailBean= (RedDetailBean) data.getSerializableExtra(AppConstants.TRANSFER_CHAT_REDENTITY);
                ((MIFragmentNew)fromFragment).senCustemRed(redDetailBean);
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
