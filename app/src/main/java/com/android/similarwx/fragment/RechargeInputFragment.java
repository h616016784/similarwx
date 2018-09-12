package com.android.similarwx.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
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
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspCashUser;
import com.android.similarwx.beans.response.RspTransfer;
import com.android.similarwx.inteface.CashViewInterface;
import com.android.similarwx.inteface.ClientDetailInfoViewInterface;
import com.android.similarwx.inteface.RechargeViewInterface;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.android.similarwx.present.CashPresent;
import com.android.similarwx.present.ClientDetailInfoPresent;
import com.android.similarwx.present.RechargePresent;
import com.android.similarwx.utils.DigestUtil;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.InputPasswordDialog;
import com.bumptech.glide.Glide;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/4/13.
 */

public class RechargeInputFragment extends BaseFragment implements RechargeViewInterface, ClientDetailInfoViewInterface {
    @BindView(R.id.recharge_input_iv)
    ImageView rechargeInputIv;
    @BindView(R.id.recharge_input_tv)
    TextView rechargeInputTv;
    @BindView(R.id.recharge_input_et)
    EditText rechargeInputEt;
    @BindView(R.id.transfer)
    Button transfer;
    private RechargePresent mPresent;
    String account;//就是accid
    String id;
    ClientDetailInfoPresent present;
    Unbinder unbinder;
    private User mUser;
    private boolean my=false;
    private boolean you=false;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recharge_input;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        Bundle bundle=getArguments();
        if (bundle!=null){
            account= bundle.getString(AppConstants.TRANSFER_ACCOUNT);
            my=bundle.getBoolean(AppConstants.TRANSFER_BASE);
            you=bundle.getBoolean(AppConstants.TRANSFER_ISHOST);
            unbinder = ButterKnife.bind(this, contentView);
            mPresent=new RechargePresent(this);
            present=new ClientDetailInfoPresent(this);
            present.getUserInfoByParams("",account);
            mActionbar.setTitle("转账");
            mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity,AppConstants.USER_OBJECT);
        }
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
        String transfer= AppConstants.USER_TRANSFER;
        if (!TextUtils.isEmpty(transfer)){
            if (transfer.equals("1")){
                if (my && you){
                    Toaster.toastShort("已禁止转账！");
                    return ;
                }
            }
        }
        if (!TextUtils.isEmpty(id)){
            InputPasswordDialog dialog=InputPasswordDialog.newInstance("转账", money, new InputPasswordDialog.OnInputFinishListener() {
                @Override
                public void onInputFinish(String password) {
                    if (DigestUtil.sha1(password).equals(mUser.getPaymentPasswd())){
                        mPresent.transfer(id,money);
                    }else
                        Toaster.toastShort("支付密码不正确！");
                }
            });
            FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
            transaction.add(dialog,"transDialog");
            transaction.addToBackStack(null);
            transaction.commit();
        }
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
    public void refreshUserInfo(User user) {
        if (user!=null){
            String logFlag="";
            if (user.getLoginFlg()==1){
                logFlag="在线";
            }else{
                logFlag="离线";
            }
            rechargeInputTv.setText(user.getName()+"("+logFlag+")");
            if (!TextUtils.isEmpty(user.getIcon())){
                Glide.with(activity).load(user.getIcon()).into(rechargeInputIv);
            }
            id=user.getId();
        }
    }

    @Override
    public void refreshUpdateUser() {

    }

    @Override
    public void refreshUpdateUserStatus(String userStatus) {

    }

    @Override
    public void refreshDeleteUser() {

    }
}
