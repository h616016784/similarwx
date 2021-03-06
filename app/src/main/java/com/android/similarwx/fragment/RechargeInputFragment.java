package com.android.similarwx.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.InputPasswordDialog;
import com.android.similarwx.widget.dialog.CancelDialogBuilder;
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
            mPresent=new RechargePresent(this,activity);
            present=new ClientDetailInfoPresent(this,activity);
            present.getUserInfoByParams("",account,0);
            mActionbar.setTitle("转账");

            rechargeInputEt.addTextChangedListener(textWatcher);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.transfer)
    public void onViewClicked() {
        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity,AppConstants.USER_OBJECT);
        String money=rechargeInputEt.getText().toString();
        if (TextUtils.isEmpty(money)){
            Toaster.toastShort("转账金额不能为空！");
            return;
        }
        String transfer= AppConstants.USER_TRANSFER;
        if (!TextUtils.isEmpty(transfer)){
            if (transfer.equals("0")){
                if (my && you){
                    Toaster.toastShort("已禁止转账！");
                    activity.finish();
                    return ;
                }
            }
        }
        if (!TextUtils.isEmpty(id)){
            if (TextUtils.isEmpty(mUser.getPaymentPasswd())){
                showDialog();
            }else {
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
    }
    private void showDialog() {
        final CancelDialogBuilder cancel_dialogBuilder = CancelDialogBuilder
                .getInstance(getActivity());

        cancel_dialogBuilder.setTitleText("无支付密码，是否要设置？");
        cancel_dialogBuilder.setDetermineText("确定");

        cancel_dialogBuilder.isCancelableOnTouchOutside(true)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_dialogBuilder.dismiss();
                    }
                }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_dialogBuilder.dismiss();
                Bundle bundle=new Bundle();
                bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE,SetPayPasswordFragment.PAY_PSD);
                bundle.putInt(SetPayPasswordFragment.MOBILE,0);
                FragmentUtils.navigateToNormalActivity(activity, new SetPayPasswordFragment(), bundle);
//                activity.finish();
            }
        }).show();
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            int posDot = temp.indexOf(".");
            if (posDot <= 0) return;
            if (temp.length() - posDot - 1 > 2)
            {
                s.delete(posDot + 3, posDot + 4);
            }
        }
    };

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
    public void refreshUserInfo(User user,int flag) {
        if (user!=null){
            String logFlag="";
            if (user.getLoginFlg()==1){
                logFlag="在线";
            }else{
                logFlag="离线";
            }
            rechargeInputTv.setText(user.getName());
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
