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
import com.android.similarwx.beans.Bill;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.beans.RedTakeBean;
import com.android.similarwx.inteface.AcountViewInterface;
import com.android.similarwx.present.AcountPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.ListPopWindow;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
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
    private List<RedTakeBean> list;
    private TimePickerView pvTime;
    private ListPopWindow listPopWindow;
    private List<PopMoreBean> moreList;

//    public GroupMessageBean.ListBean listBean;
    private AcountPresent mPresent;
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
//        Bundle bundle=getArguments();
//        if (bundle!=null){
//            listBean= (GroupMessageBean.ListBean) bundle.getSerializable(AppConstants.CHAT_GROUP_BEAN);
//        }
        iniData();
        initView();

    }

    private void initView() {
        myDetailRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<RedTakeBean,BaseViewHolder>(R.layout.item_my_detail,list) {
            @Override
            protected void convert(BaseViewHolder helper, RedTakeBean item) {
                helper.setText(R.id.item_my_detail_name_tv,item.getRedType());
                helper.setText(R.id.item_my_detail_time_tv,item.getTime());
                helper.setText(R.id.item_my_detail_money_tv,item.getMoney());
            }
        };
        myDetailRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        getAccountList();//获取bill信息

        listPopWindow=new ListPopWindow(activity,moreList);
        pvTime=new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(activity, date.toString(), Toast.LENGTH_SHORT).show();
            }
        }).build();
    }


    @OnClick({R.id.my_detail_start_ll, R.id.my_detail_end_ll, R.id.my_detail_class_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_detail_start_ll:
                pvTime.show();
                break;
            case R.id.my_detail_end_ll:
                pvTime.show();
                break;
            case R.id.my_detail_class_ll:
                listPopWindow.show(myDetailClassTv);
                break;
        }
    }


    private void iniData() {
        list=new ArrayList<>();
        RedTakeBean redTakeBean=new RedTakeBean();
        redTakeBean.setRedType("红包领取");
        redTakeBean.setTime("2017-02-12");
        redTakeBean.setMoney("+10");
        list.add(redTakeBean);

        moreList=new ArrayList<>();
        PopMoreBean bean=new PopMoreBean();
        bean.setId("-1");
        bean.setName("全部");
        moreList.add(bean);
        PopMoreBean bean2=new PopMoreBean();
        bean2.setId("0");
        bean2.setName("充值");
        moreList.add(bean2);
        PopMoreBean bean3=new PopMoreBean();
        bean3.setId("1");
        bean3.setName("转账");
        moreList.add(bean3);
        PopMoreBean bean4=new PopMoreBean();
        bean4.setId("2");
        bean4.setName("扣除");
        moreList.add(bean4);
        PopMoreBean bean5=new PopMoreBean();
        bean5.setId("3");
        bean5.setName("红包发布");
        moreList.add(bean5);
        PopMoreBean bean6=new PopMoreBean();
        bean6.setId("4");
        bean6.setName("红包领取");
        moreList.add(bean6);
        PopMoreBean bean7=new PopMoreBean();
        bean7.setId("5");
        bean7.setName("收到雷包");
        moreList.add(bean7);
        PopMoreBean bean8=new PopMoreBean();
        bean8.setId("6");
        bean8.setName("中了雷包");
        moreList.add(bean8);
        PopMoreBean bean9=new PopMoreBean();
        bean9.setId("7");
        bean9.setName("中了雷包");
        moreList.add(bean9);
        PopMoreBean bean10=new PopMoreBean();
        bean10.setId("8");
        bean10.setName("红包奖励");
        moreList.add(bean10);
        PopMoreBean bean11=new PopMoreBean();
        bean11.setId("9");
        bean11.setName("红包积分结算");
        moreList.add(bean11);
        PopMoreBean bean12=new PopMoreBean();
        bean12.setId("10");
        bean12.setName("红包冻结");
        moreList.add(bean12);
        PopMoreBean bean13=new PopMoreBean();
        bean13.setId("11");
        bean13.setName("红包解冻");
        moreList.add(bean13);
        PopMoreBean bean14=new PopMoreBean();
        bean14.setId("12");
        bean14.setName("本局输赢");
        moreList.add(bean14);
        PopMoreBean bean15=new PopMoreBean();
        bean15.setId("13");
        bean15.setName("发包返点");
        moreList.add(bean15);
        PopMoreBean bean16=new PopMoreBean();
        bean16.setId("14");
        bean16.setName("推荐返点");
        moreList.add(bean16);

    }

    private void getAccountList() {
        String userId= SharePreferenceUtil.getString(AppContext.getContext(),AppConstants.USER_ID,"无");
//        mPresent.getAcountList();
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

    }
}
