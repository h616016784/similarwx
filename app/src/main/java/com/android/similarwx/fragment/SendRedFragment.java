package com.android.similarwx.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.similarwx.utils.NetUtil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/12.
 */

public class SendRedFragment extends BaseFragment {
    @BindView(R.id.send_red_count_et)
    EditText sendRedCountEt;
    @BindView(R.id.send_red_sum_et)
    EditText sendRedSumEt;
    @BindView(R.id.send_red_sum_iv)
    ImageView sendRedSumIv;
    @BindView(R.id.send_red_lei_et)
    EditText sendRedLeiEt;
    @BindView(R.id.send_red_bt)
    Button sendRedBt;
    @BindView(R.id.send_red_group_hint_tv)
    TextView groupHintTv;
    @BindView(R.id.send_red_error_tv)
    TextView sendRedErrorTv;
    @BindView(R.id.send_red_count_et_tv)
    TextView sendRedCountEtTv;
    @BindView(R.id.send_red_sum_et_tv)
    TextView sendRedSumEtTv;
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
    @BindView(R.id.send_red_descript_rl)
    RelativeLayout sendRedDescriptRl;
    @BindView(R.id.send_red_count_read_tv)
    TextView sendRedCountReadTv;
    @BindView(R.id.send_red_sum_money)
    TextView sumMoneyTv;
    @BindView(R.id.send_red_descript_tv)
    EditText sendRedDescripTv;
    Unbinder unbinder;
    public RspGroupInfo.GroupInfo listBean;
    private String type = null;
    private User mUser;
    private String accountId;
    RspGroupInfo.GroupInfo groupInfo;
    private boolean first=false;//两个输入栏是否输入正确
    private boolean second=false;//两个输入栏是否输入正确
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
        if (bundle!=null){
            accountId=bundle.getString(AppConstants.TRANSFER_ACCOUNT);
            groupInfo= (RspGroupInfo.GroupInfo) bundle.getSerializable(AppConstants.TRANSFER_OBJECT);
        }
        if (groupInfo!=null){
            initSendView(groupInfo);
        }
        sendRedCountEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        sendRedCountEt.addTextChangedListener(countWatcher);
        sendRedSumEt.addTextChangedListener(textWatcher);
        sendRedLeiEt.addTextChangedListener(leiWatcher);
        sendRedDescripTv.addTextChangedListener(scripWatcher);
        sendRedDescripTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    String content=sendRedDescripTv.getText().toString();
                    if (!TextUtils.isEmpty(content)){
                        if (content.equals("恭喜发财 大吉大利")){
                            sendRedDescripTv.setText("");
                        }
                    }
                }
            }
        });
    }

    private void initSendView(RspGroupInfo.GroupInfo groupInfo) {
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
                groupHintTv.setVisibility(View.GONE);
                sendRedDescriptRl.setVisibility(View.VISIBLE);
                sendRedSumIv.setImageResource(R.drawable.img_send_red_sum_luck);
            }else {
                if (groupType.equals("1")){//游戏群
                    if (!TextUtils.isEmpty(gameType)){
                        if (gameType.equals("2")){
                            type="MINE";
                            sendRedCountRl.setVisibility(View.GONE);
                            sendRedCountReadRl.setVisibility(View.VISIBLE);
                            groupHintTv.setVisibility(View.VISIBLE);
                            sendRedDescriptRl.setVisibility(View.GONE);
                            sendRedCountReadTv.setText(groupInfo.getGrabBagNumber()+"");
                            sendRedSumIv.setImageResource(R.drawable.img_send_red_sum_lei);
                        }else {
                            type="LUCK";
                            sendRedCountReadRl.setVisibility(View.GONE);
                            sendRedLeiRl.setVisibility(View.GONE);
//                    sendRedLeiTv.setText("总数");
//                    sendRedLeiEt.setHint("请输入总数");
//                            groupHintTv.setText("总数最好不要超过10个");
                            groupHintTv.setVisibility(View.GONE);
                            sendRedDescriptRl.setVisibility(View.VISIBLE);
                            sendRedSumIv.setImageResource(R.drawable.img_send_red_sum_luck);
                        }
                    }
                }else if(groupType.equals("2")){//交友群也就是普通群
                    type="LUCK";
                    sendRedCountReadRl.setVisibility(View.GONE);
                    sendRedLeiRl.setVisibility(View.GONE);
                    groupHintTv.setText("总数最好不要超过10个");
                    groupHintTv.setVisibility(View.GONE);
                    sendRedDescriptRl.setVisibility(View.VISIBLE);
                    sendRedSumIv.setImageResource(R.drawable.img_send_red_sum_luck);
                }
            }
            groupMoneyTv.setText("红包发布范围： "+groupInfo.getStartRange()+"-"+groupInfo.getEndRange()+"元");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity,AppConstants.USER_OBJECT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.send_red_bt)
    public void onViewClicked() {
        int type=NetUtil.getAPNType(activity);
        if (type==0 || type==3){
            Toaster.toastShort("网络异常！");
            return;
        }
        String money=sumMoneyTv.getText().toString();
        if (TextUtils.isEmpty(money)){
            Toaster.toastShort("金额不能为空！");
            return;
        }else {
            money=money.replace("¥","");
            Pattern pattern= Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
            Matcher match=pattern.matcher(money);
            if(match.matches()==false){
                Toaster.toastShort("金额是小数点后两位哦！");
                return ;
            }
            if (listBean!=null){
                double dMoney=Double.parseDouble(money);
                if (dMoney>=listBean.getStartRange() && dMoney<=listBean.getEndRange()){}
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
                    String content=sendRedDescripTv.getText().toString();
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
                            count=sendRedCountReadTv.getText().toString();
                            bean.setCount(count);
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
                    bean.setCotent(content);
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
            if (TextUtils.isEmpty(s)){
                sumMoneyTv.setText("¥0.0");
                sendRedBt.setEnabled(false);
                first=false;
            }else {
                double dMoney=Double.parseDouble(s.toString());
                sumMoneyTv.setText("¥"+dMoney);
                if (dMoney>=listBean.getStartRange() && dMoney<=listBean.getEndRange()){
                    sendRedErrorTv.setVisibility(View.GONE);
                    sendRedSumEtTv.setTextColor(getResources().getColor(R.color.black));
                    sendRedSumEt.setTextColor(getResources().getColor(R.color.black));
                    first=true;
                    String countRed="";
                    if (type.equals("LUCK")){
                        countRed=sendRedCountEt.getText().toString();
                    }else if (type.equals("MINE")){
                        countRed=sendRedLeiEt.getText().toString();
                    }
                    if (!TextUtils.isEmpty(countRed)){
                        int countRedInt=Integer.parseInt(countRed);
                        if ((dMoney/countRedInt)<0.01){
                            sendRedErrorTv.setVisibility(View.VISIBLE);
                            sendRedErrorTv.setText("单个红包金额不可低于0.01");
                            sendRedSumEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedSumEt.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedCountEt.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedCountEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedLeiEt.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedLeiTv.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedBt.setEnabled(false);
                            first=false;
                        }else {
                            sendRedErrorTv.setVisibility(View.GONE);
                            sendRedErrorTv.setText("单个红包金额不可低于0.01");
                            sendRedSumEtTv.setTextColor(getResources().getColor(R.color.black));
                            sendRedSumEt.setTextColor(getResources().getColor(R.color.black));
                            sendRedCountEt.setTextColor(getResources().getColor(R.color.black));
                            sendRedCountEtTv.setTextColor(getResources().getColor(R.color.black));
                            sendRedLeiEt.setTextColor(getResources().getColor(R.color.black));
                            sendRedLeiTv.setTextColor(getResources().getColor(R.color.black));
                            first=true;
                        }
                    }
                    if (second)
                        sendRedBt.setEnabled(true);
                    else
                        sendRedBt.setEnabled(false);
                }else{
                    sendRedErrorTv.setVisibility(View.VISIBLE);
                    sendRedErrorTv.setText("红包总金额超出范围，请重新填写");
                    sendRedSumEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                    sendRedSumEt.setTextColor(getResources().getColor(R.color.colorRed));
                    sendRedCountEt.setTextColor(getResources().getColor(R.color.black));
                    sendRedCountEtTv.setTextColor(getResources().getColor(R.color.black));
                    sendRedLeiEt.setTextColor(getResources().getColor(R.color.black));
                    sendRedLeiTv.setTextColor(getResources().getColor(R.color.black));
                    first=false;
                    sendRedBt.setEnabled(false);
                }
            }
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
  private TextWatcher countWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)){
                sendRedBt.setEnabled(false);
            }else {
                int num=Integer.parseInt(s.toString());
                if (num>100){
                    sendRedBt.setEnabled(false);
                    sendRedErrorTv.setVisibility(View.VISIBLE);
                    sendRedErrorTv.setText("一次最多可发100个红包");
                    sendRedCountEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                    sendRedCountEt.setTextColor(getResources().getColor(R.color.colorRed));
                    sendRedBt.setEnabled(false);
                    second=false;
                }else {
                    sendRedErrorTv.setVisibility(View.GONE);
                    sendRedCountEtTv.setTextColor(getResources().getColor(R.color.black));
                    sendRedCountEt.setTextColor(getResources().getColor(R.color.black));
                    second=true;
                    String sum=sendRedSumEt.getText().toString();
                    if (!TextUtils.isEmpty(sum)){
                        double sumDouble=Double.parseDouble(sum);
                        if (sumDouble/num<0.01){
                            sendRedErrorTv.setVisibility(View.VISIBLE);
                            sendRedErrorTv.setText("单个红包金额不可低于0.01");
                            sendRedSumEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedSumEt.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedCountEt.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedCountEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                            sendRedBt.setEnabled(false);
                            second=false;
                        }else {
                            sendRedErrorTv.setVisibility(View.GONE);
                            sendRedSumEtTv.setTextColor(getResources().getColor(R.color.black));
                            sendRedSumEt.setTextColor(getResources().getColor(R.color.black));
                            sendRedCountEt.setTextColor(getResources().getColor(R.color.black));
                            sendRedCountEtTv.setTextColor(getResources().getColor(R.color.black));
                            second=true;
                        }
                    }
                    if (first)
                        sendRedBt.setEnabled(true);
                    else
                        sendRedBt.setEnabled(false);
                }
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
                second=true;
                String sum=sendRedSumEt.getText().toString();
                if (!TextUtils.isEmpty(sum)){
                    double sumDouble=Double.parseDouble(sum);
                    int num=Integer.parseInt(s.toString());
                    if (sumDouble/num<0.01){
                        sendRedErrorTv.setVisibility(View.VISIBLE);
                        sendRedErrorTv.setText("单个红包金额不可低于0.01");
                        sendRedSumEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                        sendRedSumEt.setTextColor(getResources().getColor(R.color.colorRed));
                        sendRedCountEt.setTextColor(getResources().getColor(R.color.colorRed));
                        sendRedCountEtTv.setTextColor(getResources().getColor(R.color.colorRed));
                        sendRedBt.setEnabled(false);
                        second=false;
                    }else {
                        sendRedErrorTv.setVisibility(View.GONE);
                        sendRedSumEtTv.setTextColor(getResources().getColor(R.color.black));
                        sendRedSumEt.setTextColor(getResources().getColor(R.color.black));
                        sendRedCountEt.setTextColor(getResources().getColor(R.color.black));
                        sendRedCountEtTv.setTextColor(getResources().getColor(R.color.black));
                        second=true;
                    }
                }
                if (first)
                    sendRedBt.setEnabled(true);
                else
                    sendRedBt.setEnabled(false);
            }else {
                sendRedBt.setEnabled(false);
                second=false;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher scripWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            int posDot = temp.length();
            if (posDot > 15)
            {
                s.delete(posDot -1, posDot );
            }
        }
    };
}
