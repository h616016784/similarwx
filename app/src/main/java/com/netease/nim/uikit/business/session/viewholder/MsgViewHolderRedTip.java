package com.netease.nim.uikit.business.session.viewholder;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.netease.nim.uikit.business.session.emoji.MoonUtil;
import com.netease.nim.uikit.business.session.helper.TeamNotificationHelper;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;

/**
 * Created by hanhuailong on 2018/8/6.
 */

public class MsgViewHolderRedTip extends MsgViewHolderBase {
    public MsgViewHolderRedTip(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    private TextView message_item_tips_label;
    private LinearLayout message_item_tips_ll;
    @Override
    protected int getContentResId() {
        return R.layout.item_msg_red_tips;
    }

    @Override
    protected void inflateContentView() {
        message_item_tips_label=view.findViewById(R.id.message_item_tips_label);
        message_item_tips_ll=view.findViewById(R.id.message_item_tips_ll);
    }

    @Override
    protected void bindContentView() {
        handleTextNotification(getDisplayText());
    }
    protected String getDisplayText() {
        String from=TeamHelper.getTeamMemberDisplayName(message.getSessionId(),message.getFromAccount());
        String accid= (String) message.getRemoteExtension().get("accId");
        String to=TeamHelper.getTeamMemberDisplayName(message.getSessionId(),accid);
        if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)){
            if (from.equals("我")||from.equals("你")){

            }else if (to.equals("我")||to.equals("你")){

            }else {
                message_item_tips_ll.setVisibility(View.GONE);
            }
        }
        return from+" 领取了 "+to+" 的红包";

//        return TeamNotificationHelper.getTeamNotificationText(message, message.getSessionId());
    }

    private void handleTextNotification(String text) {
        MoonUtil.identifyFaceExpressionAndATags(context, message_item_tips_label, text, ImageSpan.ALIGN_BOTTOM);
        message_item_tips_label.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }
}
