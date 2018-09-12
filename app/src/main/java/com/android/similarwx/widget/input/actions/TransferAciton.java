package com.android.similarwx.widget.input.actions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.Transfer;
import com.android.similarwx.fragment.RechargeInputFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.FragmentUtilsV4;
import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class TransferAciton extends BaseAction {
    private MessageFragment fromFragment;
    /**
     * 构造函数
     *
     */
    public TransferAciton(MessageFragment fromFragment) {
        super(R.drawable.demo_reply_bar_zhuan, R.string.chart_transfer);
        this.fromFragment=fromFragment;
    }

    @Override
    public void onClick() {
        String account=getAccount();
        FragmentActivity activity=fromFragment.getActivity();
        if (activity instanceof P2PMessageActivity){
            boolean my=((P2PMessageActivity)activity).myIsNormal;
            boolean you=((P2PMessageActivity)activity).youIsNormal;
            Bundle bundle=new Bundle();
            bundle.putString(AppConstants.TRANSFER_ACCOUNT,account);
            bundle.putBoolean(AppConstants.TRANSFER_BASE,my);
            bundle.putBoolean(AppConstants.TRANSFER_ISHOST,you);
            FragmentUtilsV4.navigateToNormalActivityForResult(fromFragment.getActivity(),new RechargeInputFragment(),bundle, makeRequestCode(AppConstants.SEND_TRAN_REQUEST));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==AppConstants.SEND_TRAN_REQUEST){//发送转账
            if(data!=null){
                Transfer transfer= (Transfer) data.getSerializableExtra(AppConstants.TRANSFER_CHAT_TRANS);
                fromFragment.senCustemTran(transfer);
//                if (redDetailBean!=null){
//                    IMMessage imMessage=createCustomMessage(redDetailBean);
//                    if (imMessage!=null)
//                        getContainer().proxy.sendMessage(imMessage);
//                }
            }
        }
    }
}
