package com.android.similarwx.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.base.BillType;
import com.android.similarwx.beans.Bill;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class TransDetailFragment extends BaseFragment {
    @BindView(R.id.trans_detail_iv)
    ImageView transDetailIv;
    @BindView(R.id.trans_detail_tile)
    TextView transDetailTile;
    @BindView(R.id.trans_detail_money)
    TextView transDetailMoney;
    @BindView(R.id.trans_detail_turn)
    TextView transDetailTurn;
    @BindView(R.id.trans_detail_state)
    TextView transDetailState;
    @BindView(R.id.trans_detail_time)
    TextView transDetailTime;
    Unbinder unbinder;

    private Bill.BillDetail billDetail=null;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_trans_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.trans_detail_title);
        unbinder = ButterKnife.bind(this, contentView);
        Bundle bundle=getArguments();
        if (bundle!=null){
            billDetail= (Bill.BillDetail) bundle.getSerializable(AppConstants.TRANSFER_BILL_BEAN);
            double amount=billDetail.getAmount();
            if (amount>0){
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimary));
                transDetailMoney.setText("+"+amount);
            }else {
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                transDetailMoney.setText(amount+"");
            }
            transDetailTime.setText(billDetail.getCreateDate());
            String tradeType=billDetail.getTradeType();
            if (!TextUtils.isEmpty(tradeType))
                transDetailTurn.setText(name(tradeType));
        }
    }

    private String name(String type){
        if (BillType.GRAP_PACKAGE.toString().equals(type)){
            return BillType.GRAP_PACKAGE.toName();
        }else if (BillType.OFFLINE_RECHARGE.toString().equals(type)){
            return BillType.OFFLINE_RECHARGE.toName();
        }else if (BillType.PACKAGE_REBATE.toString().equals(type)){
            return BillType.PACKAGE_REBATE.toName();
        }else if (BillType.PACKAGE_RETURN.toString().equals(type)){
            return BillType.PACKAGE_RETURN.toName();
        }else if (BillType.PACKAGE_REWARD.toString().equals(type)){
            return BillType.PACKAGE_REWARD.toName();
        }else if (BillType.RECHARGE.toString().equals(type)){
            return BillType.RECHARGE.toName();
        }else if (BillType.SEND_PACKAGE.toString().equals(type)){
            return BillType.SEND_PACKAGE.toName();
        }else if (BillType.THUNDER_PACKAGE.toString().equals(type)){
            return BillType.THUNDER_PACKAGE.toName();
        } else if (BillType.THUNDER_REWARD.toString().equals(type)){
            return BillType.THUNDER_REWARD.toName();
        }else if (BillType.TRANSFER.toString().equals(type)){
            return BillType.TRANSFER.toName();
        } else if (BillType.WITHDRAW.toString().equals(type)){
            return BillType.WITHDRAW.toName();
        }
        return "发红包";
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
