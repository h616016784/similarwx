package com.netease.nim.uikit.business.session.viewholder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.Transfer;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.android.similarwx.widget.dialog.RedResultNewDialogFragment;
import com.netease.nim.uikit.business.chatroom.adapter.ChatRoomMsgAdapter;
import com.netease.nim.uikit.business.session.module.ModuleProxy;
import com.netease.nim.uikit.business.session.module.list.MsgAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * Created by hanhuailong on 2018/7/11.
 */

public class MsgViewHolderTrans extends MsgViewHolderBase {
    private RelativeLayout sendView, revView;
    private TextView sendContentText, revContentText;    // 红包描述
    private TextView sendTitleText, revTitleText;    // 红包名称

    public MsgViewHolderTrans(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.trans_packet_item;
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

    @Override
    protected void bindContentView() {
        TransCustomAttachment attachment = (TransCustomAttachment) message.getAttachment();
        Transfer transfer=attachment.getTransfer();
        String amount=null;
        String textContent=null;

        if (transfer!=null){
            amount=transfer.getAmount();
            textContent=transfer.getName();
        }

        if (!isReceivedMessage()) {// 消息方向，自己发送的
            sendView.setVisibility(View.VISIBLE);
            revView.setVisibility(View.GONE);
            sendContentText.setText("转账给"+textContent);
            sendTitleText.setText("¥"+amount);
        } else {
            sendView.setVisibility(View.GONE);
            revView.setVisibility(View.VISIBLE);
            revContentText.setText("转账给"+textContent);
            revTitleText.setText("¥"+amount);
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
//        RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
//        BaseMultiItemFetchLoadAdapter adapter = getAdapter();
//        ModuleProxy proxy = null;
//        if (adapter instanceof MsgAdapter) {
//            proxy = ((MsgAdapter) adapter).getContainer().proxy;
//        } else if (adapter instanceof ChatRoomMsgAdapter) {
//            proxy = ((ChatRoomMsgAdapter) adapter).getContainer().proxy;
//        }
//        message.getFromAccount(); message.getSessionId(); message.getSessionType();
//        RedResultNewDialogFragment.show((Activity) context,attachment.getSendRedBean());
    }
}
