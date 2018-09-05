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
import com.android.similarwx.beans.AccountDetailBean;
import com.android.similarwx.beans.Bill;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.AcountViewInterface;
import com.android.similarwx.present.AcountPresent;
import com.android.similarwx.utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class TransDetailFragment extends BaseFragment implements AcountViewInterface {
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
    private AcountPresent mPresent;
    private Bill.BillDetail billDetail=null;
    private int userFlag=0;
    private String tranId;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_trans_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.trans_detail_title);
        unbinder = ButterKnife.bind(this, contentView);
        mPresent=new AcountPresent(this);
//        User user= (User) SharePreferenceUtil.getSerializableObjectDefault(activity,AppConstants.USER_OBJECT);
//        if (user!=null)
//            userFlag=user.getSystemFlg();
        Bundle bundle=getArguments();
        if (bundle!=null){
            billDetail= (Bill.BillDetail) bundle.getSerializable(AppConstants.TRANSFER_BILL_BEAN);
            tranId=bundle.getString(AppConstants.TRANSFER_ACCOUNT);
            transDetailTime.setText(billDetail.getCreateDate());
            String type=billDetail.getTradeType();
            double amount=Double.parseDouble(String.format("%.2f", billDetail.getAmount()));
            if (type.equals(BillType.ALL.toString())){
                transDetailTurn.setText(BillType.ALL.toName());
                transDetailState.setText("");
            }else if (type.equals(BillType.GRAP_PACKAGE.toString())){ //红包领取
                transDetailTurn.setText(BillType.GRAP_PACKAGE.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                transDetailMoney.setText("+"+amount);
                transDetailState.setText("你领取了"+billDetail.getNickName()+"的红包");
            }else if (type.equals(BillType.OFFLINE_RECHARGE.toString())){ //增加
                transDetailTurn.setText(BillType.OFFLINE_RECHARGE.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                transDetailMoney.setText("+"+amount);
                transDetailState.setText("系统增加");
            }else if (type.equals(BillType.PACKAGE_REBATE.toString())){ //推荐返点
                if (userFlag==1){
                    transDetailTurn.setText("发放返点");
                    transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                    transDetailMoney.setText("-"+amount+"");
                    transDetailState.setText(billDetail.getNickName()+"获得了推荐佣金");
                }else {
                    transDetailTurn.setText(BillType.PACKAGE_REBATE.toName());
                    transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    transDetailMoney.setText("+"+amount);
                    transDetailState.setText("获得"+billDetail.getNickName()+"的游戏返佣");
                }

            }else if (type.equals(BillType.PACKAGE_RETURN.toString())){ //红包奖励结算
                transDetailTurn.setText(BillType.PACKAGE_RETURN.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                transDetailMoney.setText("+"+amount);
                transDetailState.setText("超时红包退回");
            }else if (type.equals(BillType.PACKAGE_REWARD.toString())){ //红包奖励
                if (userFlag==1){
                    transDetailTurn.setText("发放奖励");
                    transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                    transDetailMoney.setText("-"+amount+"");
                    transDetailState.setText(billDetail.getNickName()+"抢到了幸运数字（*.**）");
                }else {
                    transDetailTurn.setText(BillType.PACKAGE_REWARD.toName());
                    transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    transDetailMoney.setText("+"+amount);
                    transDetailState.setText("抢到幸运数字（**.**）");
                }
            }else if (type.equals(BillType.RECHARGE.toString())){ //充值
                transDetailTurn.setText(BillType.RECHARGE.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                transDetailMoney.setText("+"+amount);
                transDetailState.setText("充值成功");
            }else if (type.equals(BillType.SEND_PACKAGE.toString())){ //红包发布
                transDetailTurn.setText(BillType.SEND_PACKAGE.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                transDetailMoney.setText("-"+amount+"");
                transDetailState.setText("你发了红包");
            }else if (type.equals(BillType.THUNDER_PACKAGE.toString())){ //雷包扣款
                transDetailTurn.setText(BillType.THUNDER_PACKAGE.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                transDetailMoney.setText("-"+amount+"");
                transDetailState.setText("你踩了"+billDetail.getNickName()+"的红包地雷");
            }else if (type.equals(BillType.THUNDER_REWARD.toString())){ //雷包奖励
                transDetailTurn.setText(BillType.THUNDER_REWARD.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                transDetailMoney.setText("+"+amount);
                transDetailState.setText(billDetail.getNickName()+"踩中了你埋的雷包");
            }else if (type.equals(BillType.TRANSFER.toString())){ //转账
                transDetailTurn.setText(BillType.TRANSFER.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                transDetailMoney.setText(amount+"");
                transDetailState.setText("你转账给"+billDetail.getNickName());
            }else if (type.equals(BillType.REC_TRANSFER.toString())){//收到转账
                transDetailTurn.setText(BillType.TRANSFER.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                transDetailMoney.setText("+"+amount);
                transDetailState.setText("你收到"+billDetail.getNickName()+"的转账");
            }else if (type.equals(BillType.WITHDRAW.toString())){ //扣除
                transDetailTurn.setText(BillType.WITHDRAW.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                transDetailMoney.setText("-"+amount+"");
                transDetailState.setText("系统扣除");
            }else if (type.equals(BillType.FREEZE.toString())){ //冻结
                transDetailTurn.setText(BillType.FREEZE.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.black));
                transDetailMoney.setText("-"+amount+"");
                transDetailState.setText("积分冻结");
            } else if (type.equals(BillType.UNFREEZE.toString())){ //解冻
                transDetailTurn.setText(BillType.UNFREEZE.toName());
                transDetailMoney.setTextColor(AppContext.getResources().getColor(R.color.colorPrimaryDark));
                transDetailMoney.setText("+"+amount);
                transDetailState.setText("积分解冻");
            }
            mPresent.getAccountDetail(tranId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshBill(Bill bill) {

    }

    @Override
    public void refreshAccountDetaiol(AccountDetailBean accountDetailBean) {
        if (accountDetailBean!=null){

        }
    }
}
