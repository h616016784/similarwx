package com.android.similarwx.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
    ListPopupWindow listPopupWindow;
    private List<BillType> billTypeList;

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
        mType=BillType.ALL.toString();
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
                helper.setText(R.id.item_my_detail_time_tv,item.getCreateDate());
                double amount=item.getAmount();
                if (type.equals(BillType.ALL.toString())){
                    helper.setText(R.id.item_my_detail_name_tv,BillType.ALL.toName());
                }else if (type.equals(BillType.GRAP_PACKAGE.toString())){ //红包领取
                    helper.setText(R.id.item_my_detail_name_tv,BillType.GRAP_PACKAGE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                }else if (type.equals(BillType.OFFLINE_RECHARGE.toString())){ //增加
                    helper.setText(R.id.item_my_detail_name_tv,BillType.OFFLINE_RECHARGE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                }else if (type.equals(BillType.PACKAGE_REBATE.toString())){ //推荐返点
                    helper.setText(R.id.item_my_detail_name_tv,BillType.PACKAGE_REBATE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                }else if (type.equals(BillType.PACKAGE_RETURN.toString())){ //红包奖励结算
                    helper.setText(R.id.item_my_detail_name_tv,BillType.PACKAGE_RETURN.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                }else if (type.equals(BillType.PACKAGE_REWARD.toString())){ //红包奖励
                    helper.setText(R.id.item_my_detail_name_tv,BillType.PACKAGE_REWARD.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                }else if (type.equals(BillType.RECHARGE.toString())){ //充值
                    helper.setText(R.id.item_my_detail_name_tv,BillType.RECHARGE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                }else if (type.equals(BillType.SEND_PACKAGE.toString())){ //红包发布
                    helper.setText(R.id.item_my_detail_name_tv,BillType.SEND_PACKAGE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.black));
                    helper.setText(R.id.item_my_detail_money_tv,"-"+amount+"");
                }else if (type.equals(BillType.THUNDER_PACKAGE.toString())){ //雷包扣款
                    helper.setText(R.id.item_my_detail_name_tv,BillType.THUNDER_PACKAGE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.black));
                    helper.setText(R.id.item_my_detail_money_tv,"-"+amount+"");
                }else if (type.equals(BillType.THUNDER_REWARD.toString())){ //雷包奖励
                    helper.setText(R.id.item_my_detail_name_tv,BillType.THUNDER_REWARD.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                }else if (type.equals(BillType.TRANSFER.toString())){ //转账
                    if (amount>0){
                        helper.setText(R.id.item_my_detail_name_tv,BillType.TRANSFER.toName());
                        helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                        helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
                    }else {
                        helper.setText(R.id.item_my_detail_name_tv,BillType.TRANSFER.toName());
                        helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.black));
                        helper.setText(R.id.item_my_detail_money_tv,amount+"");
                    }
                }else if (type.equals(BillType.WITHDRAW.toString())){ //扣除
                    helper.setText(R.id.item_my_detail_name_tv,BillType.WITHDRAW.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.black));
                    helper.setText(R.id.item_my_detail_money_tv,"-"+amount+"");
                } else if (type.equals(BillType.FREEZE.toString())){ //冻结
                    helper.setText(R.id.item_my_detail_name_tv,BillType.FREEZE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.black));
                    helper.setText(R.id.item_my_detail_money_tv,"-"+amount+"");
                } else if (type.equals(BillType.UNFREEZE.toString())){ //解冻
                    helper.setText(R.id.item_my_detail_name_tv,BillType.UNFREEZE.toName());
                    helper.setTextColor(R.id.item_my_detail_money_tv,AppContext.getResources().getColor(R.color.colorPrimaryDark));
                    helper.setText(R.id.item_my_detail_money_tv,"+"+amount);
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
        //展示类别list
        listPopupWindow=new ListPopupWindow(activity);
        listPopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);//设置宽度
        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);//设置高度
        listPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景色
        listPopupWindow.setModal(true);//设置为true响应物理键listPopupWindow.setHorizontalOffset(100);//垂直间距listPopupWindow.setVerticalOffset(100);//水平间距
        listPopupWindow.setAdapter(new PopupWindowAdapter(activity));
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myDetailClassTv.setText(billTypeList.get(position).toName());
                adapter.getData().clear();
                mType=billTypeList.get(position).toString();
                mPresent.getAcountList(userId,mType.toString(),mStart,mEnd);
                if (listPopupWindow!=null){
                    listPopupWindow.dismiss();
                }
            }
        });
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
                listPopupWindow.setAnchorView(myDetailClassLl);
                listPopupWindow.show();
//                listPopWindow.show(myDetailClassTv);
                break;
        }
    }


    private void iniData() {
        billTypeList=new ArrayList<>();
        billTypeList.add(BillType.ALL);
        billTypeList.add(BillType.GRAP_PACKAGE);
        billTypeList.add(BillType.OFFLINE_RECHARGE);
        billTypeList.add(BillType.PACKAGE_REBATE);
        billTypeList.add(BillType.PACKAGE_RETURN);
        billTypeList.add(BillType.PACKAGE_REWARD);
        billTypeList.add(BillType.RECHARGE);
        billTypeList.add(BillType.SEND_PACKAGE);
        billTypeList.add(BillType.THUNDER_PACKAGE);
        billTypeList.add(BillType.THUNDER_REWARD);
        billTypeList.add(BillType.TRANSFER);
        billTypeList.add(BillType.WITHDRAW);
        billTypeList.add(BillType.FREEZE);
        billTypeList.add(BillType.UNFREEZE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listPopupWindow!=null){
            listPopupWindow.dismiss();
            listPopupWindow=null;
        }
        unbinder.unbind();
    }

    public class PopupWindowAdapter extends BaseAdapter {
        private Context context;
        public PopupWindowAdapter(Context context) {
            this.context= context;
        }
        @Override
        public int getCount() {
            if (billTypeList!=null)
                return billTypeList.size();
            return 10;
        }
        @Override
        public Object getItem(int position) {
            return billTypeList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position,View convertView,ViewGroup parent) {

            ViewHolder viewHolder;
            if(convertView ==null) {
                viewHolder=new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_pop_client_more, null);
                viewHolder.textView=convertView.findViewById(R.id.item_pop_client_more_tv);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(billTypeList.get(position).toName());
            return convertView;
        }
        class ViewHolder {
            TextView textView;
        }
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
