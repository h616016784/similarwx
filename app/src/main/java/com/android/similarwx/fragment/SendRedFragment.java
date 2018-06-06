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
    @BindView(R.id.send_red_sum_money)
    TextView sumMoneyTv;
    Unbinder unbinder;
    public GroupMessageBean.ListBean listBean;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.send_red_bt)
    public void onViewClicked() {
        Intent intent=new Intent();
        SendRed bean=new SendRed();
        String money=sendRedSumEt.getText().toString();
        if (TextUtils.isEmpty(money)){
            Toaster.toastShort("金额不能为空！");
            return;
        }
        String type = null;
        if (listBean!=null){
            type = listBean.getGameType();
            if (type.equals("1")){
                type="MINE";
            }else {
                type="LUCK";
            }
        }

        bean.setRequestNum(UUID.randomUUID().toString());
        bean.setAmount(money);
        bean.setGroupId(listBean.getGroupId());
        bean.setMyGroupId(listBean.getId()+"");
        bean.setUserId(SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ACCID,"无"));
        bean.setMyUserId(SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ID,"无"));
        bean.setType(type);
        intent.putExtra(AppConstants.TRANSFER_CHAT_REDENTITY,bean);
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
