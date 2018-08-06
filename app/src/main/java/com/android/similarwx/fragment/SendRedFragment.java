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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.inteface.SendRedViewInterface;
import com.android.similarwx.present.SendRedPresent;
import com.android.similarwx.utils.DigestUtil;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Strings.MD5;
import com.android.similarwx.widget.InputPasswordDialog;
import com.android.similarwx.widget.dialog.CancelDialogBuilder;
import com.android.similarwx.widget.input.sessions.Extras;
import com.android.similarwx.widget.input.sessions.SessionCustomization;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/12.
 */

public class SendRedFragment extends BaseFragment implements SendRedViewInterface {
    @BindView(R.id.send_red_count_et)
    EditText sendRedCountEt;
    @BindView(R.id.send_red_sum_et)
    EditText sendRedSumEt;
    @BindView(R.id.send_red_lei_et)
    EditText sendRedLeiEt;
    @BindView(R.id.send_red_bt)
    Button sendRedBt;
    @BindView(R.id.send_red_group_hint_tv)
    TextView groupHintTv;
    @BindView(R.id.send_red_group_money_tv)
    TextView groupMoneyTv;
    @BindView(R.id.send_red_lei_tv)
    TextView sendRedLeiTv;
    @BindView(R.id.send_red_lei_rl)
    RelativeLayout sendRedLeiRl;
    @BindView(R.id.send_red_count_rl)
    RelativeLayout sendRedCountRl;
    @BindView(R.id.send_red_count_read_rl)
    RelativeLayout sendRedCountReadRl;
    @BindView(R.id.send_red_count_read_tv)
    TextView sendRedCountReadTv;
    @BindView(R.id.send_red_sum_money)
    TextView sumMoneyTv;
    @BindView(R.id.send_red_descript_tv)
    TextView sendRedDescripTv;
    Unbinder unbinder;
    public RspGroupInfo.GroupInfo listBean;
    private String type = null;
    private User mUser;
    private String accountId;
    private SendRedPresent present;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_send_red;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("发红包");
        unbinder = ButterKnife.bind(this, contentView);
        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity,AppConstants.USER_OBJECT);
        Bundle bundle=getArguments();
        accountId=bundle.getString(AppConstants.TRANSFER_ACCOUNT);
        present=new SendRedPresent(this);
        present.getGroupByIdOrGroupId(mUser.getAccId(),accountId);
        sendRedSumEt.addTextChangedListener(textWatcher);
        sendRedLeiEt.addTextChangedListener(leiWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.send_red_bt)
    public void onViewClicked() {
        String money=sendRedSumEt.getText().toString();
        if (TextUtils.isEmpty(money)){
            Toaster.toastShort("金额不能为空！");
            return;
        }else {
            if (listBean!=null){
                double dMoney=Double.parseDouble(money);
                if (dMoney>listBean.getStartRange() && dMoney<listBean.getEndRange()){}
                else{
                    Toaster.toastShort("金额超出了范围！");
                    return;
                }
            }
        }

        if (TextUtils.isEmpty(mUser.getPaymentPasswd())){
            showDialog();
        }else {
            showInputDialog(money);
        }
    }

    private void showInputDialog(String money) {
        InputPasswordDialog dialog=InputPasswordDialog.newInstance("请输入支付密码", money, new InputPasswordDialog.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                if (DigestUtil.sha1(password).equals(mUser.getPaymentPasswd())){
                    String money=sendRedSumEt.getText().toString();
                    String lei=sendRedLeiEt.getText().toString();
                    String count=sendRedCountEt.getText().toString();
                    Intent intent=new Intent();
                    SendRed sendRed=new SendRed();
                    SendRed.SendRedBean bean=new SendRed.SendRedBean();
                    if (TextUtils.isEmpty(type)){
                        bean.setCount(count);
                        bean.setTitle("手气红包游戏");
                    }else {
                        if (type.equals("MINE")){//游戏群
                            if (TextUtils.isEmpty(lei)){
                                Toaster.toastShort("雷数不能为空！");
                                return;
                            }
                            bean.setThunder(lei);
                            bean.setCount(sendRedCountReadTv.getText().toString());
                            bean.setTitle("扫雷红包游戏");

                        }else {
                            bean.setCount(count);
                            bean.setTitle("手气红包游戏");
                        }
                    }

                    bean.setRequestNum(MD5.getStringMD5(UUID.randomUUID().toString()));
                    bean.setAmount(money);
                    if (listBean!=null){
                        bean.setGroupId(listBean.getGroupId());
                        bean.setMyGroupId(listBean.getId()+"");
                    }

                    bean.setUserId(SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ID,"无"));
                    bean.setMyUserId(SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ACCID,"无"));

                    bean.setType(type);
                    bean.setCotent("领取红包");
                    sendRed.setData(bean);
                    sendRed.setType("7");
                    intent.putExtra(AppConstants.TRANSFER_CHAT_REDENTITY,sendRed);
                    activity.setResult(Activity.RESULT_OK,intent);
                    activity.finish();
                }else
                    Toaster.toastShort("支付密码不正确！");
            }
        });
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(dialog,"inputDialog");
        transaction.addToBackStack(null);
        transaction.commit();
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
                bundle.putString(AppConstants.TRANSFER_PASSWORD_TYPE, SetPayPasswordFragment.PAY_PSD);
                FragmentUtils.navigateToNormalActivity(activity,new SetPayPasswordFragment(),bundle);
            }
        }).show();
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)){
                sumMoneyTv.setText("¥0.0");
                sendRedBt.setEnabled(false);
            }else {
                sumMoneyTv.setText("¥"+s);
                sendRedBt.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher leiWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s)){
                if (s.length()>1){
                    sendRedLeiEt.setText(s.subSequence(0,1));
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshSendRed(RspGroupInfo.GroupInfo groupInfo) {
        if (groupInfo!=null){
            listBean=groupInfo;
            String groupType = groupInfo.getGroupType();
            String gameType = groupInfo.getGameType();
            if (TextUtils.isEmpty(groupType)){
                type="LUCK";
                sendRedCountReadRl.setVisibility(View.GONE);
                sendRedLeiRl.setVisibility(View.GONE);
//                    sendRedLeiTv.setText("总数");
//                    sendRedLeiEt.setHint("请输入总数");
                groupHintTv.setText("总数最好不要超过10个");
                sendRedDescripTv.setVisibility(View.VISIBLE);
            }else {
                if (groupType.equals("1")){//游戏群
                    if (!TextUtils.isEmpty(gameType)){
                        if (gameType.equals("2")){
                            type="MINE";
                            sendRedCountRl.setVisibility(View.GONE);
                            sendRedCountReadRl.setVisibility(View.VISIBLE);
                            sendRedDescripTv.setVisibility(View.GONE);
                            sendRedCountReadTv.setText(groupInfo.getGrabBagNumber()+"");
                        }
                    }
                }else if(groupType.equals("2")){//交友群也就是普通群
                    type="LUCK";
                    sendRedCountReadRl.setVisibility(View.GONE);
                    sendRedLeiRl.setVisibility(View.GONE);
//                    sendRedLeiTv.setText("总数");
//                    sendRedLeiEt.setHint("请输入总数");
                    groupHintTv.setText("总数最好不要超过10个");
                    sendRedDescripTv.setVisibility(View.VISIBLE);
                }
            }
            groupMoneyTv.setText("红包发布范围： "+groupInfo.getStartRange()+"-"+groupInfo.getEndRange()+"元");
        }
    }
}
