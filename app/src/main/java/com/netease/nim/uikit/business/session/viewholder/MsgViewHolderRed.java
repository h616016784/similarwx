package com.netease.nim.uikit.business.session.viewholder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.beans.BaseBean;
import com.android.similarwx.beans.CanGrabBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.inteface.MiViewInterface;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.dialog.LoadingDialog;
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

public class MsgViewHolderRed extends MsgViewHolderBase implements MiViewInterface {
    private RelativeLayout sendView, revView;
    private TextView sendContentText, revContentText;    // 红包描述
    private TextView sendTitleText, revTitleText;    // 红包名称
    private TextView tv_bri_target_send, tv_bri_target_rev;    // 红包change
    private ImageView tv_bri_pic_send,tv_bri_pic_rev;//红包图标
    private MIPresent miPresent;
    public MsgViewHolderRed(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
        miPresent=new MIPresent(this, (AppCompatActivity) ((MsgAdapter) adapter).getContainer().activity);
    }

    @Override
    protected int getContentResId() {
        return R.layout.red_packet_item_new;
    }

    @Override
    protected void inflateContentView() {
        sendContentText = findViewById(R.id.tv_bri_mess_send);
        sendTitleText = findViewById(R.id.tv_bri_name_send);
        sendView = findViewById(R.id.bri_send);
        revContentText = findViewById(R.id.tv_bri_mess_rev);
        revTitleText = findViewById(R.id.tv_bri_name_rev);
        tv_bri_target_rev = findViewById(R.id.tv_bri_target_rev);
        tv_bri_target_send = findViewById(R.id.tv_bri_target_send);
        tv_bri_pic_send = findViewById(R.id.tv_bri_pic_send);
        tv_bri_pic_rev = findViewById(R.id.tv_bri_pic_rev);
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
        String content=null;
        sendRedBean=attachment.getSendRedBean();
        if (sendRedBean!=null){
            amount =sendRedBean.getAmount();
            title =null;
            click=sendRedBean.getClick();
            content=sendRedBean.getCotent();
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
            if (sendRedBean.getType().equals("MINE"))
                sendContentText.setText(textContent);
            else if (sendRedBean.getType().equals("LUCK"))
                sendContentText.setText(content);
            sendTitleText.setText(title);

//            if (message.getStatus()==MsgStatusEnum.read){//已读
//                sendView.setBackgroundResource(R.drawable.red_packet_send_press);
//                tv_bri_target_send.setText("红包已领取");
//            }else{
//                sendView.setBackgroundResource(R.drawable.red_packet_send_bg);
//                tv_bri_target_send.setText("领取红包");
//            }

            if (!TextUtils.isEmpty(click)){
                if (click.equals("0000")){
                    tv_bri_pic_send.setImageResource(R.drawable.img_red_item_looked);
                    tv_bri_target_send.setText("红包已领取");
                    sendView.setBackgroundResource(R.drawable.red_packet_send_bg_other);
                }else if (click.equals("8889")){
                    tv_bri_pic_send.setImageResource(R.drawable.img_red_item_looked);
                    tv_bri_target_send.setText("红包已被领完");
                    sendView.setBackgroundResource(R.drawable.red_packet_send_bg_other);
                }else if (click.equals("9000")){
                    tv_bri_pic_send.setImageResource(R.drawable.img_red_item_re);
                    tv_bri_target_send.setText("红包已过期");
                    sendView.setBackgroundResource(R.drawable.red_packet_send_bg_other);
                }
            }else {
                tv_bri_pic_send.setImageResource(R.drawable.img_red_item_re);
                tv_bri_target_send.setText("领取红包");
                sendView.setBackgroundResource(R.drawable.red_packet_send_bg);
            }
        } else {
            sendView.setVisibility(View.GONE);
            revView.setVisibility(View.VISIBLE);
            if (sendRedBean.getType().equals("MINE"))
                revContentText.setText(textContent);
            else if (sendRedBean.getType().equals("LUCK"))
                revContentText.setText(content);
            revTitleText.setText(title);

//            if (message.getStatus()==MsgStatusEnum.read){//已读
//                revView.setBackgroundResource(R.drawable.red_packet_rev_press);
//                tv_bri_target_rev.setText("红包已领取");
//            }else{
//                sendView.setBackgroundResource(R.drawable.red_packet_rev_bg);
//                tv_bri_target_rev.setText("领取红包");
//            }
            if (!TextUtils.isEmpty(click)){
                if (click.equals("0000")){
                    tv_bri_pic_rev.setImageResource(R.drawable.img_red_item_looked);
                    tv_bri_target_rev.setText("红包已领取");
                    revView.setBackgroundResource(R.drawable.red_packet_rev_bg_other);
                }else if (click.equals("8889")){
                    tv_bri_pic_rev.setImageResource(R.drawable.img_red_item_looked);
                    tv_bri_target_rev.setText("红包已被领完");
                    revView.setBackgroundResource(R.drawable.red_packet_rev_bg_other);
                }else if (click.equals("9000")){
                    tv_bri_pic_rev.setImageResource(R.drawable.img_red_item_re);
                    tv_bri_target_rev.setText("红包已过期");
                    revView.setBackgroundResource(R.drawable.red_packet_rev_bg_other);
                }
            }else {
                tv_bri_pic_rev.setImageResource(R.drawable.img_red_item_re);
                tv_bri_target_rev.setText("领取红包");
                revView.setBackgroundResource(R.drawable.red_packet_rev_bg);
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

//        message.getFromAccount(); message.getSessionId(); message.getSessionType();
        miPresent.canGrab(attachment.getSendRedBean().getRedPacId());//请求是否能抢红包
//        LoadingDialog.Loading_Show(((AppCompatActivity)context).getSupportFragmentManager(),true);

    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshCustemRed(SendRed.SendRedBean data) {

    }

    @Override
    public void grabRed(RspGrabRed bean) {

    }

    @Override
    public void canGrab(CanGrabBean bean) {
        RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
        if (bean!=null) {
            String code = bean.getRetCode();
            if (code.equals("8888")) {
                Bundle bundle=new Bundle();
                SendRed.SendRedBean mSendRedBean=attachment.getSendRedBean();
                if (mSendRedBean!=null){
                    bundle.putString(RedDetailFragment.GROUPID,mSendRedBean.getGroupId());
                    bundle.putString(RedDetailFragment.REDID,mSendRedBean.getRedPacId());
                    bundle.putSerializable(RedDetailFragment.SENDRED,mSendRedBean);
                }
                FragmentUtils.navigateToNormalActivity((Activity) context,new RedDetailFragment(),bundle);
            }else {
                BaseMultiItemFetchLoadAdapter adapter = getAdapter();
                ModuleProxy proxy = null;
                if (adapter instanceof MsgAdapter) {
                    proxy = ((MsgAdapter) adapter).getContainer().proxy;
                } else if (adapter instanceof ChatRoomMsgAdapter) {
                    proxy = ((ChatRoomMsgAdapter) adapter).getContainer().proxy;
                }
                RedResultNewDialogFragment.show((Activity) context,attachment.getSendRedBean(),message.getSessionId(),message,proxy);
            }
        }
    }
}
