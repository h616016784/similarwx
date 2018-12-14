package com.android.similarwx.widget.input.actions;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.fragment.SendRedFragment;
import com.android.similarwx.fragment.SetPayPasswordFragment;
import com.android.similarwx.inteface.SendRedViewInterface;
import com.android.similarwx.misdk.model.CustomAttachment;
import com.android.similarwx.present.SendRedPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.FragmentUtilsV4;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.dialog.CancelDialogBuilder;
import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;
import com.netease.nim.uikit.business.session.module.Container;
import com.netease.nim.uikit.business.session.viewholder.MsgViewHolderRed;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class RedAction extends BaseAction implements SendRedViewInterface {
    private MessageFragment fromFragment;
    private SendRedPresent present;
    private String myAccid;

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
        present=new SendRedPresent(this, (AppCompatActivity) fromFragment.getActivity());
    }

    @Override
    public void onClick() {
        myAccid= SharePreferenceUtil.getString(getActivity(),AppConstants.USER_ACCID,"");
        present.getGroupByIdOrGroupId(myAccid,getAccount());

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

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshSendRed(RspGroupInfo.GroupInfo groupInfo) {
        if (groupInfo!=null){
            Bundle bundle=new Bundle();
            String account=getAccount();
            bundle.putString(AppConstants.TRANSFER_ACCOUNT,account);
            bundle.putSerializable(AppConstants.TRANSFER_OBJECT,groupInfo);
            FragmentUtilsV4.navigateToNormalActivityForResult(fromFragment.getActivity(),new SendRedFragment(),bundle, makeRequestCode(AppConstants.SEND_RED_REQUEST));
        }
    }

}
