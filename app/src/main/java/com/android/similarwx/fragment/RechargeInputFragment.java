package com.android.similarwx.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.Transfer;
import com.android.similarwx.beans.response.RspCashUser;
import com.android.similarwx.beans.response.RspTransfer;
import com.android.similarwx.inteface.CashViewInterface;
import com.android.similarwx.inteface.RechargeViewInterface;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.android.similarwx.present.CashPresent;
import com.android.similarwx.present.RechargePresent;
import com.android.similarwx.widget.InputPasswordDialog;
import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/13.
 */

public class RechargeInputFragment extends BaseFragment implements RechargeViewInterface, CashViewInterface {
    @BindView(R.id.recharge_input_iv)
    ImageView rechargeInputIv;
    @BindView(R.id.recharge_input_tv)
    TextView rechargeInputTv;
    @BindView(R.id.recharge_input_et)
    EditText rechargeInputEt;
    @BindView(R.id.transfer)
    Button transfer;
    private RechargePresent mPresent;
    String account;
    String accid;

    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recharge_input;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        account= getArguments().getString(AppConstants.TRANSFER_ACCOUNT);
        unbinder = ButterKnife.bind(this, contentView);
        mPresent=new RechargePresent(this);
        mActionbar.setTitle("转账");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.transfer)
    public void onViewClicked() {
        String money=rechargeInputEt.getText().toString();
        if (TextUtils.isEmpty(money)){
            Toaster.toastShort("转账金额不能为空！");
            return;
        }
        if (!TextUtils.isEmpty(account))
            mPresent.transfer(account,money);
//        InputPasswordDialog dialog=InputPasswordDialog.newInstance("支付", "100", new InputPasswordDialog.OnInputFinishListener() {
//            @Override
//            public void onInputFinish(String password) {
//
//            }
//        });
//        activity.getFragmentManager().beginTransaction().add(dialog,"inputpassword").commit();
    }


    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshRecharge(Transfer transfer) {
        if (transfer!=null){
            if (transfer.isSuccess()){
                Intent intent=new Intent();
                intent.putExtra(AppConstants.TRANSFER_CHAT_TRANS,transfer);
                activity.setResult(Activity.RESULT_OK,intent);
                activity.finish();
            }
        }
    }

    @Override
    public void refreshCash(RspCashUser.CashUser cashUser) {
        if (cashUser!=null){
            String logFlag="";
            if (cashUser.getLoginFlg()==1){
                logFlag="在线";
            }else{
                logFlag="离线";
            }
            rechargeInputTv.setText(cashUser.getName()+"("+logFlag+")");
            if (!TextUtils.isEmpty(cashUser.getIcon())){
                Glide.with(activity).load(cashUser.getIcon()).into(rechargeInputIv);
            }
            account=cashUser.getId();
            accid=cashUser.getAccId();

        }
    }


}
