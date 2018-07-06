package com.android.similarwx.fragment;

import android.app.Activity;
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
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Strings.MD5;
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

public class SendRedFragment extends BaseFragment {
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
    @BindView(R.id.send_red_sum_money)
    TextView sumMoneyTv;
    Unbinder unbinder;
    public GroupMessageBean.ListBean listBean;
    private String type = null;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_send_red;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        Bundle bundle=getArguments();
        if (bundle!=null){
            listBean= (GroupMessageBean.ListBean) bundle.getSerializable(AppConstants.CHAT_GROUP_BEAN);
        }
        mActionbar.setTitle("发红包");
        unbinder = ButterKnife.bind(this, contentView);
        sendRedSumEt.addTextChangedListener(textWatcher);

        if (listBean!=null){
            String groupType = listBean.getGroupType();
            if (TextUtils.isEmpty(groupType)){
                type="MINE";
            }else {
                if (groupType.equals("1")){//游戏群
                    type="MINE";
                }else if(groupType.equals("2")){//交友群也就是普通群
                    type="LUCK";
                    sendRedLeiRl.setVisibility(View.GONE);
//                    sendRedLeiTv.setText("总数");
//                    sendRedLeiEt.setHint("请输入总数");
                    groupHintTv.setText("总数最好不要超过10个");
                }
            }
            groupMoneyTv.setText("红包发布范围： "+listBean.getStartRange()+"-"+listBean.getEndRange()+"元");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.send_red_bt)
    public void onViewClicked() {
        Intent intent=new Intent();
        SendRed sendRed=new SendRed();
        SendRed.SendRedBean bean=new SendRed.SendRedBean();
        String money=sendRedSumEt.getText().toString();
        String lei=sendRedLeiEt.getText().toString();
        String count=sendRedCountEt.getText().toString();
        if (TextUtils.isEmpty(money)){
            Toaster.toastShort("金额不能为空！");
            return;
        }

        if (type.equals("MINE")){//游戏群
            if (TextUtils.isEmpty(lei)){
                Toaster.toastShort("雷数不能为空！");
                return;
            }
            bean.setThunder(lei);
            bean.setTitle("扫雷红包游戏");

        }else {
            bean.setCount(count);
            bean.setTitle("手气红包游戏");
        }

        bean.setRequestNum(MD5.getStringMD5(UUID.randomUUID().toString()));
        bean.setAmount(money);
        bean.setGroupId(listBean.getGroupId());
        bean.setMyGroupId(listBean.getId()+"");
        bean.setUserId(SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ACCID,"无"));
        bean.setMyUserId(SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ID,"无"));
        bean.setType(type);
        bean.setCotent("领取红包");
        sendRed.setData(bean);
        sendRed.setType("7");
        intent.putExtra(AppConstants.TRANSFER_CHAT_REDENTITY,sendRed);
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)){
                sumMoneyTv.setText("¥0.0");
            }else {
                sumMoneyTv.setText("¥"+s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
