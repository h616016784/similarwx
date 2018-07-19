package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.base.BillType;
import com.android.similarwx.beans.Bill;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.beans.RedTakeBean;
import com.android.similarwx.inteface.AcountViewInterface;
import com.android.similarwx.present.AcountPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.ListPopWindow;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/3.
 */

public class MyDetailFragment extends BaseFragment implements AcountViewInterface{
    @BindView(R.id.my_detail_zong_tv)
    TextView myDetailZongTv;
    @BindView(R.id.my_detail_freeze_tv)
    TextView myDetailFreezeTv;
    @BindView(R.id.my_detail_start_tv)
    TextView myDetailStartTv;
    @BindView(R.id.my_detail_start_ll)
    LinearLayout myDetailStartLl;
    @BindView(R.id.my_detail_end_tv)
    TextView myDetailEndTv;
    @BindView(R.id.my_detail_end_ll)
    LinearLayout myDetailEndLl;
    @BindView(R.id.my_detail_sum_tv)
    TextView myDetailSumTv;
    @BindView(R.id.my_detail_class_tv)
    TextView myDetailClassTv;
    @BindView(R.id.my_detail_class_ll)
    LinearLayout myDetailClassLl;
    @BindView(R.id.my_detail_rv)
    RecyclerView myDetailRv;
    Unbinder unbinder;

    private BaseQuickAdapter adapter;
    private List<Bill.BillDetail> list;
    private TimePickerView pvTime;
    private ListPopWindow listPopWindow;
    private List<PopMoreBean> moreList;

//    public GroupMessageBean.ListBean listBean;
    private AcountPresent mPresent;
    String userId;
    private int timeFlag=0;//0 表示开始时间，1表示结束时间
    private String mType;
    private String mStart;
    private String mEnd;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("我的明细");
        unbinder = ButterKnife.bind(this, contentView);
        mPresent=new AcountPresent(this);
        userId= SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ID,"无");
        mType=BillType.SEND_PACKAGE.toString();
//        Bundle bundle=getArguments();
//        if (bundle!=null){
//            listBean= (GroupMessageBean.ListBean) bundle.getSerializable(AppConstants.CHAT_GROUP_BEAN);
//        }
        iniData();
        initView();

    }

    private void initView() {
        myDetailRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<Bill.BillDetail,BaseViewHolder>(R.layout.item_my_detail,list) {
            @Override
            protected void convert(BaseViewHolder helper, Bill.BillDetail item) {
                String type=item.getTradeType();
                String name=name(type);
                helper.setText(R.id.item_my_detail_name_tv,name);
                helper.setText(R.id.item_my_detail_time_tv,item.getCreateDate());
                double amount=item.getAmount();
                if (amount>0){
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimary));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+item.getAmount());
                }else {
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.black));
                    helper.setText(R.id.item_my_detail_money_tv,item.getAmount()+"");
                }

            }
        };
        myDetailRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bill.BillDetail billDetail= (Bill.BillDetail) adapter.getData().get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable(AppConstants.TRANSFER_BILL_BEAN,billDetail);
                FragmentUtils.navigateToNormalActivity(getActivity(),new TransDetailFragment(),bundle);
            }
        });
        //获取bill信息
        Calendar cal=Calendar.getInstance();
        Date endTime=cal.getTime();
        mEnd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime);
        cal.add(Calendar.DATE,-1);
        Date starTime=cal.getTime();
        mStart=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(starTime);
        myDetailStartTv.setText(mStart);
        myDetailEndTv.setText(mEnd);
        mPresent.getAcountList(userId, "".toString(),mStart,mEnd);

        listPopWindow=new ListPopWindow(activity,moreList);
        listPopWindow.setOnClickItem(new ListPopWindow.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                adapter.getData().clear();
                mType=moreList.get(position).getContent();
                mPresent.getAcountList(userId,mType.toString(),mStart,mEnd);
            }
        });
        pvTime=new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (timeFlag==0){
                    mStart=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    myDetailStartTv.setText(mStart);
                }else if (timeFlag==1){
                    mEnd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    myDetailEndTv.setText(mEnd);
                }
                adapter.getData().clear();
                mPresent.getAcountList(userId,mType,mStart,mEnd);
            }
        }).build();
    }


    @OnClick({R.id.my_detail_start_ll, R.id.my_detail_end_ll, R.id.my_detail_class_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_detail_start_ll:
                timeFlag=0;
                pvTime.show();
                break;
            case R.id.my_detail_end_ll:
                timeFlag=1;
                pvTime.show();
                break;
            case R.id.my_detail_class_ll:
                listPopWindow.show(myDetailClassTv);
                break;
        }
    }


    private void iniData() {

        moreList=new ArrayList<>();
        PopMoreBean bean=new PopMoreBean();
        bean.setId("-1");
        bean.setName("全部");
        bean.setContent("");
        moreList.add(bean);
        PopMoreBean bean2=new PopMoreBean();
        bean2.setId("0");
        bean2.setName("红包发布");
        bean2.setContent("SEND_PACKAGE");
        moreList.add(bean2);
        PopMoreBean bean3=new PopMoreBean();
        bean3.setId("1");
        bean3.setName("红包领取");
        bean3.setContent("GRAP_PACKAGE");
        moreList.add(bean3);
        PopMoreBean bean4=new PopMoreBean();
        bean4.setId("2");
        bean4.setName("红包返点");
        bean4.setContent("PACKAGE_REBATE");
        moreList.add(bean4);
        PopMoreBean bean5=new PopMoreBean();
        bean5.setId("3");
        bean5.setName("红包奖励");
        bean5.setContent("PACKAGE_REWARD");
        moreList.add(bean5);
        PopMoreBean bean6=new PopMoreBean();
        bean6.setId("4");
        bean6.setName("中了雷包");
        bean6.setContent("THUNDER_PACKAGE");
        moreList.add(bean6);
        PopMoreBean bean7=new PopMoreBean();
        bean7.setId("5");
        bean7.setName("收到雷包");
        bean7.setContent("THUNDER_REWARD");
        moreList.add(bean7);
        PopMoreBean bean8=new PopMoreBean();
        bean8.setId("6");
        bean8.setName("红包积分结算");
        bean8.setContent("PACKAGE_RETURN");
        moreList.add(bean8);
        PopMoreBean bean9=new PopMoreBean();
        bean9.setId("7");
        bean9.setName("充值");
        bean9.setContent("RECHARGE");
        moreList.add(bean9);
        PopMoreBean bean10=new PopMoreBean();
        bean10.setId("8");
        bean10.setName("扣除");
        bean10.setContent("WITHDRAW");
        moreList.add(bean10);
        PopMoreBean bean11=new PopMoreBean();
        bean11.setId("9");
        bean11.setName("转账");
        bean11.setContent("TRANSFER");
        moreList.add(bean11);
        PopMoreBean bean12=new PopMoreBean();
        bean12.setId("10");
        bean12.setName("增加");
        bean12.setContent("OFFLINE_RECHARGE");
        moreList.add(bean12);

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
        if (listPopWindow!=null){
            listPopWindow.destroy();
            listPopWindow=null;
        }
        unbinder.unbind();
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshBill(Bill bill) {
        if (bill!=null){
            myDetailZongTv.setText(bill.getTotalAmount());
            myDetailFreezeTv.setText(bill.getFreezeAmount());
            myDetailSumTv.setText(bill.getSumAmount());
            adapter.addData(bill.getAccountDetailList());
        }
    }
}
