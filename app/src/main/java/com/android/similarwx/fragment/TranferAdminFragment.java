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
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.List;

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
//            APIYUNXIN.searchUser(message.getSessionId(), new YCallBack<List<NimUserInfo>>() {
//                @Override
//                public void callBack(List<NimUserInfo> nimUserInfos) {
//                    if (nimUserInfos!=null && nimUserInfos.size()>0){
//                        transferAdminNameTv.setText("转账给 "+nimUserInfos.get(0).getName());
//                    }
//                }
//            });
        }

        if (message != null) {
            TransCustomAttachment attachment = (TransCustomAttachment) message.getAttachment();
            if (attachment!=null){
                Transfer transfer=attachment.getTransfer();
                if (transfer!=null){
                    transferAdminMoneyTv.setText("¥ "+transfer.getAmount());
                    String myName= SharePreferenceUtil.getString(activity,AppConstants.USER_NICK,"");
                    String toUserName=transfer.getToUserName();
                    if (myName.equals(toUserName))
                        transferAdminNameTv.setText("转账给 "+"我");
                    else
                        transferAdminNameTv.setText("转账给 "+toUserName);
                }

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
