package com.netease.nim.uikit.business.session.viewholder;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.activity.MainChartrActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.Transfer;
import com.android.similarwx.fragment.SearchFragment;
import com.android.similarwx.fragment.TranferAdminFragment;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.dialog.RedResultNewDialogFragment;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.chatroom.adapter.ChatRoomMsgAdapter;
import com.netease.nim.uikit.business.session.module.ModuleProxy;
import com.netease.nim.uikit.business.session.module.list.MsgAdapter;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;

/**
 * Created by hanhuailong on 2018/7/11.
 */

public class MsgViewHolderTrans extends MsgViewHolderBase {
    private RelativeLayout sendView, revView;
    private TextView sendContentText, revContentText;    // 红包描述
    private TextView sendTitleText, revTitleText;
    private TextView sendTargetText, revTargetText;    // 红包名称

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
        sendTargetText = findViewById(R.id.tv_bri_target_send);
        sendView = findViewById(R.id.bri_send);
        revContentText = findViewById(R.id.tv_bri_mess_rev);
        revTitleText = findViewById(R.id.tv_bri_name_rev);
        revTargetText = findViewById(R.id.tv_bri_target_rev);

        revView = findViewById(R.id.bri_rev);
    }

    @Override
    protected void bindContentView() {
        TransCustomAttachment attachment = (TransCustomAttachment) message.getAttachment();
        Transfer transfer = attachment.getTransfer();
        String amount = null;
        String textContent = null;

        if (transfer != null) {
            amount = transfer.getAmount();
            textContent = transfer.getToUserName();

        }

        if (!isReceivedMessage()) {// 消息方向，自己发送的
            sendView.setVisibility(View.VISIBLE);
            revView.setVisibility(View.GONE);
            sendContentText.setText("转账给 " + textContent);
            sendTargetText.setText("¥" + amount);

            if (message.getStatus() == MsgStatusEnum.read) {//已读
                sendView.setBackgroundResource(R.drawable.red_packet_send_press);
            } else {
                sendView.setBackgroundResource(R.drawable.red_packet_send_bg);
            }
        } else {
            sendView.setVisibility(View.GONE);
            revView.setVisibility(View.VISIBLE);
            revContentText.setText("转账给 " + "我");
            revTargetText.setText("¥" + amount);

            if (message.getStatus() == MsgStatusEnum.read) {//已读
                revView.setBackgroundResource(R.drawable.red_packet_rev_press);
            } else {
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
        message.setStatus(MsgStatusEnum.read);
        NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
        adapter.notifyDataSetChanged();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.USER_OBJECT, message);
        FragmentUtils.navigateToNormalActivity(((MsgAdapter) adapter).getContainer().activity, new TranferAdminFragment(), bundle);
    }
}
