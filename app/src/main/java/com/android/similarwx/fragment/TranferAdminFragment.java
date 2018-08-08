package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.Transfer;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/8/8.
 */

public class TranferAdminFragment extends BaseFragment {
    @BindView(R.id.transfer_admin_iv)
    ImageView transferAdminIv;
    @BindView(R.id.transfer_admin_status_tv)
    TextView transferAdminStatusTv;
    @BindView(R.id.transfer_admin_name_tv)
    TextView transferAdminNameTv;
    @BindView(R.id.transfer_admin_money_tv)
    TextView transferAdminMoneyTv;
    @BindView(R.id.register_complete)
    Button registerComplete;
    Unbinder unbinder;
    private IMMessage message;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transfer_admin;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("转账详情");
        unbinder = ButterKnife.bind(this, contentView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            message = (IMMessage) bundle.getSerializable(AppConstants.USER_OBJECT);
        }
        if (message != null) {
            TransCustomAttachment attachment = (TransCustomAttachment) message.getAttachment();
            if (attachment!=null){
                Transfer transfer=attachment.getTransfer();
                if (transfer!=null)
                    transferAdminMoneyTv.setText("¥ "+transfer.getAmount());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.register_complete)
    public void onViewClicked() {
        activity.finish();
    }
}
