package com.netease.nim.uikit.business.session.viewholder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.widget.dialog.RedResultNewDialogFragment;
import com.netease.nim.uikit.business.chatroom.adapter.ChatRoomMsgAdapter;
import com.netease.nim.uikit.business.session.module.ModuleProxy;
import com.netease.nim.uikit.business.session.module.list.MsgAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanhuailong on 2018/7/11.
 */

public class MsgViewHolderRed extends MsgViewHolderBase {
    private RelativeLayout sendView, revView;
    private TextView sendContentText, revContentText;    // 红包描述
    private TextView sendTitleText, revTitleText;    // 红包名称

    public MsgViewHolderRed(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.red_packet_item;
    }

    @Override
    protected void inflateContentView() {
        sendContentText = findViewById(R.id.tv_bri_mess_send);
        sendTitleText = findViewById(R.id.tv_bri_name_send);
        sendView = findViewById(R.id.bri_send);
        revContentText = findViewById(R.id.tv_bri_mess_rev);
        revTitleText = findViewById(R.id.tv_bri_name_rev);
        revView = findViewById(R.id.bri_rev);
    }
    SendRed.SendRedBean sendRedBean;
    @Override
    protected void bindContentView() {
        RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
        String amount=null;
        String title=null;
        String click=null;
        String textContent=null;
        sendRedBean=attachment.getSendRedBean();
        if (sendRedBean!=null){
            amount =sendRedBean.getAmount();
            title =null;
            click=sendRedBean.getClick();
            if (TextUtils.isEmpty(sendRedBean.getThunder()))
                textContent=amount+"-"+sendRedBean.getCount();
            else
                textContent=amount+"-"+sendRedBean.getThunder();
            if (sendRedBean.getType().equals("MINE"))
                title="扫雷红包";
            else if (sendRedBean.getType().equals("LUCK"))
                title="拼手气红包";
        }


        if (!isReceivedMessage()) {// 消息方向，自己发送的
            sendView.setVisibility(View.VISIBLE);
            revView.setVisibility(View.GONE);
            sendContentText.setText(textContent);
            sendTitleText.setText(title);

            if (message.getStatus()==MsgStatusEnum.read){//已读
                sendView.setBackgroundResource(R.drawable.red_packet_send_press);
            }else{
                sendView.setBackgroundResource(R.drawable.red_packet_send_bg);
            }
        } else {
            sendView.setVisibility(View.GONE);
            revView.setVisibility(View.VISIBLE);
            revContentText.setText(textContent);
            revTitleText.setText(title);

            if (message.getStatus()==MsgStatusEnum.read){//已读
                revView.setBackgroundResource(R.drawable.red_packet_rev_press);
            }else{
                sendView.setBackgroundResource(R.drawable.red_packet_rev_bg);
            }
        }
    }

    @Override
    protected int leftBackground() {
        return R.color.transparent;
    }

    @Override
    protected int rightBackground() {
        return R.color.transparent;
    }

    @Override
    protected void onItemClick() {
        if (sendRedBean!=null){
            message.setStatus(MsgStatusEnum.read);
            NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
        }

        RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
        BaseMultiItemFetchLoadAdapter adapter = getAdapter();
        ModuleProxy proxy = null;
        if (adapter instanceof MsgAdapter) {
            proxy = ((MsgAdapter) adapter).getContainer().proxy;
        } else if (adapter instanceof ChatRoomMsgAdapter) {
            proxy = ((ChatRoomMsgAdapter) adapter).getContainer().proxy;
        }
        message.getFromAccount(); message.getSessionId(); message.getSessionType();
        RedResultNewDialogFragment.show((Activity) context,attachment.getSendRedBean(),message);
    }
}
