package com.netease.nim.uikit.business.session.viewholder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspRedDetail;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.inteface.RedDetailViewInterface;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.present.RedDetailPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.dialog.RedResultNewDialogFragment;
import com.google.gson.Gson;
import com.netease.nim.uikit.business.session.emoji.MoonUtil;
import com.netease.nim.uikit.business.session.helper.TeamNotificationHelper;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by hanhuailong on 2018/8/6.
 */

public class MsgViewHolderRedTip extends MsgViewHolderBase implements RedDetailViewInterface {
    public MsgViewHolderRedTip(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    private TextView message_item_tips_label,message_item_tips_red_tv,message_item_tips_red_finish_tv;
    private ImageView message_item_tips_iv;
    private LinearLayout message_item_tips_ll;
    private RedDetailPresent mPresent;
    SendRed.SendRedBean sendRedBean;
    @Override
    protected int getContentResId() {
        return R.layout.item_msg_red_tips;
    }

    @Override
    protected void inflateContentView() {
        message_item_tips_label=view.findViewById(R.id.message_item_tips_label);
        message_item_tips_red_tv=view.findViewById(R.id.message_item_tips_red_tv);
        message_item_tips_ll=view.findViewById(R.id.message_item_tips_ll);
        message_item_tips_red_finish_tv=view.findViewById(R.id.message_item_tips_red_finish_tv);
        message_item_tips_iv=view.findViewById(R.id.message_item_tips_iv);
        mPresent=new RedDetailPresent(this,(AppCompatActivity)context);
    }

    @Override
    protected void bindContentView() {
        handleTextNotification(getDisplayText());
    }
    protected String getDisplayText() {
//        String from=TeamHelper.getTeamMemberDisplayName(message.getSessionId(),message.getFromAccount());
        Map<String, Object> content=message.getRemoteExtension();
        message_item_tips_ll.setVisibility(View.VISIBLE);
//        String to=TeamHelper.getTeamMemberDisplayName(message.getSessionId(),accid);
        message_item_tips_red_tv.setVisibility(View.GONE);
        message_item_tips_iv.setVisibility(View.GONE);
        if (content!=null){
            String accid= (String) content.get("accId");
//            String redPacTipMessageType= (String) content.get("redPacTipMessageType");
//            if (redPacTipMessageType.equals("emptyTipsMessage")) {//禁言tip
//                view.setVisibility(View.VISIBLE);
//                return message.getContent();
//            }
            String sendRedJson= (String) content.get("sendRedBean");
            if (!TextUtils.isEmpty(sendRedJson))  {//抢红包的tip
                message_item_tips_red_tv.setVisibility(View.VISIBLE);
                message_item_tips_iv.setVisibility(View.VISIBLE);
                String from=TeamHelper.getTeamMemberDisplayName(message.getSessionId(),accid);
                Object object= message.getRemoteExtension().get("finishFlag");
                int finishFlag=0;
                if (object!=null){
                    finishFlag = (int)object;
                }
                Gson gson=new Gson();
                if (!TextUtils.isEmpty(sendRedJson)){
                    sendRedBean=gson.fromJson(sendRedJson, SendRed.SendRedBean.class);
                }
                String to=TeamHelper.getTeamMemberDisplayName(message.getSessionId(),sendRedBean.getMyUserId());
                if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)){
                    if (!from.equals("我")&&!from.equals("你")&&!to.equals("我")&&!to.equals("你")){
                        message_item_tips_ll.setVisibility(View.GONE);
                    }else {
                        if (to.equals("我")){
                            if (finishFlag==1){
                                message_item_tips_red_finish_tv.setVisibility(View.VISIBLE);
                            }else {
                                message_item_tips_red_finish_tv.setVisibility(View.GONE);
                            }
//                    mPresent.redDetailList(sendRedBean.getRedPacId(),sendRedBean.getGroupId());
                        }
                    }
                }
                return from+"领取了"+to+"的";
            }
        }
        return message.getContent();
//        return TeamNotificationHelper.getTeamNotificationText(message, message.getSessionId());
    }

    private void handleTextNotification(String text) {
        MoonUtil.identifyFaceExpressionAndATags(context, message_item_tips_label, text, ImageSpan.ALIGN_BOTTOM);
        message_item_tips_label.setMovementMethod(LinkMovementMethod.getInstance());
        message_item_tips_red_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendRedBean!=null){
                    Bundle bundle=new Bundle();
                    bundle.putString(RedDetailFragment.GROUPID,sendRedBean.getGroupId());
                    bundle.putString(RedDetailFragment.REDID,sendRedBean.getRedPacId());
                    bundle.putSerializable(RedDetailFragment.SENDRED,sendRedBean);
                    FragmentUtils.navigateToNormalActivity((Activity) context,new RedDetailFragment(),bundle);
                }
            }
        });
    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshRedDetail(RspRedDetail.RedListData redListData) {
        List<RedDetialBean> list=redListData.getRedPacDetailList();
        if (list!=null && sendRedBean!=null){
            String count=sendRedBean.getCount();
            if (!TextUtils.isEmpty(count)){
                if (list.size()==Integer.parseInt(count)){
                    message_item_tips_red_finish_tv.setVisibility(View.VISIBLE);
                }else {
                    message_item_tips_red_finish_tv.setVisibility(View.GONE);
                }
            }
        }
    }
}
