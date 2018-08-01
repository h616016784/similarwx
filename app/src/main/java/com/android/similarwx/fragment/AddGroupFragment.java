package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.GroupRule;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.beans.RewardRule;
import com.android.similarwx.beans.request.ReqGroup;
import com.android.similarwx.beans.response.RspGroupInfo;
import com.android.similarwx.inteface.AddGroupViewInterface;
import com.android.similarwx.present.AddGroupPresent;
import com.android.similarwx.utils.DigestUtil;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.ListPopWindow;
import com.android.similarwx.widget.dialog.BottomBaseDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
import com.android.similarwx.widget.dialog.EditDialogSimple;
import com.android.similarwx.widget.dialog.RuleDialogFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by hanhuailong on 2018/6/13.
 */

public class AddGroupFragment extends BaseFragment implements AddGroupViewInterface{
    Unbinder unbinder;
    @BindView(R.id.create_group_name_et)
    TextView createGroupNameEt;
    @BindView(R.id.create_group_name_rl)
    RelativeLayout createGroupNameRl;
    @BindView(R.id.create_group_set_tv)
    TextView createGroupSetTv;
    @BindView(R.id.create_group_set_rl)
    RelativeLayout createGroupSetRl;
    @BindView(R.id.create_group_notice_et)
    TextView createGroupNoticeEt;
    @BindView(R.id.create_group_notice_rl)
    RelativeLayout createGroupNoticeRl;
    @BindView(R.id.create_group_must_et)
    TextView createGroupMustEt;
    @BindView(R.id.create_group_must_rl)
    RelativeLayout createGroupMustRl;
    @BindView(R.id.create_group_home_tv)
    TextView createGroupHomeTv;
    @BindView(R.id.create_group_home_rl)
    RelativeLayout createGroupHomeRl;
    @BindView(R.id.create_group_range_high_et)
    TextView createGroupRangeHighEt;
    @BindView(R.id.create_group_range_high_rl)
    RelativeLayout createGroupRangeHighRl;
    @BindView(R.id.create_group_range_low_et)
    TextView createGroupRangeLowEt;
    @BindView(R.id.create_group_range_low_rl)
    RelativeLayout createGroupRangeLowRl;
    @BindView(R.id.create_group_num_et)
    TextView createGroupNumEt;
    @BindView(R.id.create_group_num_rl)
    RelativeLayout createGroupNumRl;
    @BindView(R.id.create_group_lei_et)
    TextView createGroupLeiEt;
    @BindView(R.id.create_group_in_set_tv)
    TextView createGroupInSetTv;
    @BindView(R.id.create_group_in_set_rl)
    RelativeLayout createGroupInSetRl;
    @BindView(R.id.create_group_add_rule_iv)
    ImageView createGroupAddRuleIv;
    @BindView(R.id.create_group_rule_rv)
    RecyclerView createGroupRuleRv;
    @BindView(R.id.create_group_new_bt)
    Button createGroupNewBt;
    @BindView(R.id.create_group_lei_ll)
    LinearLayout creatGroupLeiLl;

    private ListPopWindow groupTypePop;
    private List<PopMoreBean> groupTypeList;

    private BaseQuickAdapter adapter;
    private List<GroupRule> groupRuleList;
    private List<PopMoreBean> redNumList;
    private List<PopMoreBean> redHighList;
    private List<PopMoreBean> redLowList;
    private List<PopMoreBean> groupInList;
    private AddGroupPresent mPresent;
    RspGroupInfo.GroupInfo reqGroup;
    public RspGroupInfo.GroupInfo groupInfo;
    private int editType=0;//0为增加，1为编辑
    private List<GroupRule> ruleList;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_group;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("创建群组");
        unbinder = ButterKnife.bind(this, contentView);
        reqGroup=new RspGroupInfo.GroupInfo();
        mPresent=new AddGroupPresent(this);
        initGroupList();
//        groupTypePop=new ListPopWindow(activity,groupTypeList);
//        groupTypePop.setOnClickItem(new ListPopWindow.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//            }
//        });
        createGroupRuleRv.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new BaseQuickAdapter<GroupRule,BaseViewHolder>(R.layout.item_group_rule,groupRuleList){

            @Override
            protected void convert(BaseViewHolder helper, GroupRule item) {
                helper.setText(R.id.item_create_group_rule_name,item.getRewardName());
                helper.setText(R.id.item_create_group_rule_grab,item.getRewardValue());
                helper.setText(R.id.item_create_group_rule_get,item.getAmountReward());
            }
        };
        createGroupRuleRv.setAdapter(adapter);
        Bundle bundle=getArguments();
        if (bundle!=null){
            groupInfo= (RspGroupInfo.GroupInfo) bundle.getSerializable(AppConstants.TRANSFER_GROUP_INFO);
            if (groupInfo!=null){
                editType=1;
                mActionbar.setTitle("编辑群组");
                createGroupNewBt.setText("编辑群组");
                initOriView(groupInfo);//初始化原群组信息
            }
        }
        RxView.clicks(createGroupNewBt).throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object value) {//反之抖动   添加群组
                        String groupName=createGroupNameEt.getText().toString();
                        String notice=createGroupNoticeEt.getText().toString();
                        String must=createGroupMustEt.getText().toString();
                        String leilv=createGroupLeiEt.getText().toString();
                        String low=createGroupRangeLowEt.getText().toString();
                        String high=createGroupRangeHighEt.getText().toString();

                        if (TextUtils.isEmpty(groupName)){
                            Toaster.toastShort("群名称不能为空！");
                            return;
                        }
                        if (TextUtils.isEmpty(notice)){
                            Toaster.toastShort("群公告不能为空！");
                            return;
                        }
                        if (TextUtils.isEmpty(must)){
                            Toaster.toastShort("群须知不能为空！");
                            return;
                        }
                        if (TextUtils.isEmpty(low)){
                            Toaster.toastShort("最低值不能为空！");
                            return;
                        }
                        if (TextUtils.isEmpty(high)){
                            Toaster.toastShort("最高值不能为空！");
                            return;
                        }
                        String id=SharePreferenceUtil.getString(activity, AppConstants.USER_ID,"无");
                        reqGroup.setGroupName(groupName);
                        reqGroup.setCreateId(id);
                        reqGroup.setNotice(notice);
                        reqGroup.setRequirement(must);
                        if (!TextUtils.isEmpty(leilv))
                            reqGroup.setMultipleRate(Double.parseDouble(leilv));
                        if (!TextUtils.isEmpty(low))
                            reqGroup.setStartRange(Integer.parseInt(low));
                        if (!TextUtils.isEmpty(high))
                            reqGroup.setEndRange(Integer.parseInt(high));

                        List<GroupRule> list=adapter.getData();
                        Gson gson=new Gson();
                        String rules=gson.toJson(list);
                        reqGroup.setRewardRules(rules);
                        if (editType==0)
                            mPresent.addGroup(reqGroup);
                        else{
                            String userId=SharePreferenceUtil.getString(activity, AppConstants.USER_ID,"无");
                            mPresent.updateGroup(reqGroup,userId);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toaster.toastShort(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete","点击了！");
                    }
                });
    }

    private void initOriView(RspGroupInfo.GroupInfo groupInfo) {
        reqGroup=groupInfo;
        createGroupNameEt.setText(groupInfo.getGroupName());
        createGroupNoticeEt.setText(groupInfo.getNotice());
        createGroupMustEt.setText(groupInfo.getRequirement());
        if (TextUtils.isEmpty(groupInfo.getThunderNumber())){
            createGroupLeiEt.setText("");
        }else {
            createGroupLeiEt.setText(groupInfo.getThunderNumber()+"");
        }
        createGroupRangeLowEt.setText(groupInfo.getStartRange()+"");
        createGroupRangeHighEt.setText(groupInfo.getEndRange()+"");

        String hall=groupInfo.getHallDisplay();
        if (!TextUtils.isEmpty(hall)){
            if (hall.equals("0")){
                createGroupHomeTv.setText("否");
            }else {
                createGroupHomeTv.setText("是");
            }
        }
        String groupType=groupInfo.getGroupType();
        if (!TextUtils.isEmpty(groupType)){
            if (groupType.equals("1")){
                createGroupSetTv.setText("游戏群");
            }else {
                createGroupSetTv.setText("普通群");
            }
        }
        String joinMode=groupInfo.getJoinmode();
        if (!TextUtils.isEmpty(joinMode)){
            if (joinMode.equals("0")){
                createGroupInSetTv.setText("允许任何人");
            }else if (joinMode.equals("1")){
                createGroupInSetTv.setText("需要验证");
            }else {
                createGroupInSetTv.setText("拒绝任何人");
            }
        }
        String rules=groupInfo.getRewardRules();
        if (!TextUtils.isEmpty(rules)){
            Gson gson=new Gson();
            ruleList=gson.fromJson(rules,new TypeToken<List<GroupRule>>() {
            }.getType());
            if (ruleList!=null)
                adapter.addData(ruleList);
        }
    }

    private void initGroupList() {
        groupTypeList=new ArrayList<>();
        PopMoreBean bean=new PopMoreBean();
        bean.setId("1");
        bean.setName("游戏群");
        PopMoreBean bean2=new PopMoreBean();
        bean2.setId("2");
        bean2.setName("普通群");
        groupTypeList.add(bean);
        groupTypeList.add(bean2);
        groupRuleList=new ArrayList<>();

        redNumList=new ArrayList<>();
        PopMoreBean redNumBean=new PopMoreBean();
        redNumBean.setName("5");
        redNumList.add(redNumBean);
        PopMoreBean redNumBean1=new PopMoreBean();
        redNumBean1.setName("7");
        redNumList.add(redNumBean1);
        PopMoreBean redNumBean2=new PopMoreBean();
        redNumBean2.setName("10");
        redNumList.add(redNumBean2);

        redHighList=new ArrayList<>();
        PopMoreBean redHighBean=new PopMoreBean();
        redHighBean.setName("30");
        redHighList.add(redHighBean);
        PopMoreBean redHighBean1=new PopMoreBean();
        redHighBean1.setName("50");
        redHighList.add(redHighBean1);
        PopMoreBean redHighBean2=new PopMoreBean();
        redHighBean2.setName("100");
        redHighList.add(redHighBean2);

        redLowList=new ArrayList<>();
        PopMoreBean redLowBean=new PopMoreBean();
        redLowBean.setName("10");
        redLowList.add(redLowBean);
        PopMoreBean redLowBean1=new PopMoreBean();
        redLowBean1.setName("20");
        redLowList.add(redLowBean1);
        PopMoreBean redLowBean2=new PopMoreBean();
        redLowBean2.setName("30");
        redLowList.add(redLowBean2);

        groupInList=new ArrayList<>();
        PopMoreBean groupInBean=new PopMoreBean();
        groupInBean.setId("0");
        groupInBean.setName("允许任何人");
        groupInList.add(groupInBean);
        PopMoreBean groupInBean1=new PopMoreBean();
        groupInBean1.setId("1");
        groupInBean1.setName("需要验证");
        groupInList.add(groupInBean1);
        PopMoreBean groupInBean2=new PopMoreBean();
        groupInBean2.setId("2");
        groupInBean2.setName("拒绝任何人");
        groupInList.add(groupInBean2);
    }


    @OnClick({R.id.create_group_set_rl, R.id.create_group_home_rl, R.id.create_group_in_set_rl, R.id.create_group_add_rule_iv,R.id.create_group_lei_ll
            ,R.id.create_group_num_rl,R.id.create_group_range_high_rl,R.id.create_group_range_low_rl,R.id.create_group_must_rl,R.id.create_group_notice_rl,R.id.create_group_name_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.create_group_set_rl:
                BottomBaseDialog dialog=new BottomBaseDialog(activity);
                dialog.setTitle("群类型设置");
                dialog.setList(groupTypeList);
                dialog.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PopMoreBean bean=groupTypeList.get(position);
                        createGroupSetTv.setText(bean.getName());
                        if (bean.getId().equals("1"))
                            creatGroupLeiLl.setVisibility(View.VISIBLE);
                        else if (bean.getId().equals("2"))
                            creatGroupLeiLl.setVisibility(View.GONE);
                        reqGroup.setGroupType(bean.getId());
                    }
                });
                dialog.show();
                break;
            case R.id.create_group_lei_ll://雷
                EditDialogSimple simpleLei=new EditDialogSimple(activity,"中雷赔率");
                simpleLei.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                    @Override
                    public void onClickListener(String text){
                        if (TextUtils.isEmpty(text))
                            Toaster.toastShort("中雷赔率不能为空！");
                        else
                            createGroupLeiEt.setText(text);
                    }
                });
                simpleLei.show();
                break;
            case R.id.create_group_num_rl://红包个数
                BottomBaseDialog dialogRedNum=new BottomBaseDialog(activity);
                dialogRedNum.setTitle("红包个数");
                dialogRedNum.setList(redNumList);
                dialogRedNum.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PopMoreBean bean=redNumList.get(position);
                        createGroupNumEt.setText(bean.getName());
                    }
                });
                dialogRedNum.show();
                break;
            case R.id.create_group_range_high_rl://红包上限
                BottomBaseDialog dialogRedHigh=new BottomBaseDialog(activity);
                dialogRedHigh.setTitle("红包上限");
                dialogRedHigh.setList(redHighList);
                dialogRedHigh.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PopMoreBean bean=redHighList.get(position);
                        createGroupRangeHighEt.setText(bean.getName());
                    }
                });
                dialogRedHigh.show();
                break;
            case R.id.create_group_range_low_rl://红包下限
                BottomBaseDialog dialogRedLow=new BottomBaseDialog(activity);
                dialogRedLow.setTitle("红包下线");
                dialogRedLow.setList(redLowList);
                dialogRedLow.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PopMoreBean bean=redLowList.get(position);
                        createGroupRangeLowEt.setText(bean.getName());
                    }
                });
                dialogRedLow.show();
                break;
            case R.id.create_group_must_rl://须知
                EditDialogSimple simpleMust=new EditDialogSimple(activity,"群须知");
                simpleMust.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                    @Override
                    public void onClickListener(String text){
                        if (TextUtils.isEmpty(text))
                            Toaster.toastShort("群须知不能为空！");
                        else
                            createGroupMustEt.setText(text);
                    }
                });
                simpleMust.show();
                break;
            case R.id.create_group_notice_rl://公告
                EditDialogSimple simpleNotice=new EditDialogSimple(activity,"群公告");
                simpleNotice.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                    @Override
                    public void onClickListener(String text){
                        if (TextUtils.isEmpty(text))
                            Toaster.toastShort("群公告不能为空！");
                        else
                            createGroupNoticeEt.setText(text);
                    }
                });
                simpleNotice.show();
                break;
            case R.id.create_group_name_rl://群名称
                EditDialogSimple simpleName=new EditDialogSimple(activity,"群名称");
                simpleName.setOnConfirmClickListener(new EditDialogSimple.ConfirmClickListener() {
                    @Override
                    public void onClickListener(String text){
                        if (TextUtils.isEmpty(text))
                            Toaster.toastShort("群名称不能为空！");
                        else
                            createGroupNameEt.setText(text);
                    }
                });
                simpleName.show();
                break;
            case R.id.create_group_home_rl:
                EasyAlertDialogHelper.createOkCancelDiolag(activity, "提示", "是否大厅显示？","是","否", true, new EasyAlertDialogHelper.OnDialogActionListener() {
                    @Override
                    public void doCancelAction() {
                        createGroupHomeTv.setText("否");
                        reqGroup.setHallDisplay("0");
                    }

                    @Override
                    public void doOkAction() {
                        createGroupHomeTv.setText("是");
                        reqGroup.setHallDisplay("1");
                    }
                }).show();
                break;
            case R.id.create_group_in_set_rl:
//                EasyAlertDialogHelper.createOkCancelDiolag(activity, "提示", "是否进群验证？","是","否", true, new EasyAlertDialogHelper.OnDialogActionListener() {
//                    @Override
//                    public void doCancelAction() {
//                        createGroupInSetTv.setText("否");
//                        reqGroup.setJoinmode("0");
//                    }
//
//                    @Override
//                    public void doOkAction() {
//                        createGroupInSetTv.setText("是");
//                        reqGroup.setJoinmode("1");
//                    }
//                }).show();

                BottomBaseDialog dialogSet=new BottomBaseDialog(activity);
                dialogSet.setTitle("进群验证");
                dialogSet.setList(groupInList);
                dialogSet.setOnClickItem(new BottomBaseDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PopMoreBean bean=groupInList.get(position);
                        reqGroup.setJoinmode(bean.getId());
                        createGroupInSetTv.setText(bean.getName());
                    }
                });
                dialogSet.show();
                break;
            case R.id.create_group_add_rule_iv:
                RuleDialogFragment.show(activity, new RuleDialogFragment.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClickListener(GroupRule groupRule) {
                        if (groupRule!=null){
                            adapter.addData(groupRule);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void refreshAddGroup() {
        if (editType==0)
            Toaster.toastShort("创建群组成功！");
        else
            Toaster.toastShort("编辑群组成功！");
        activity.finish();
    }

    @Override
    public void showErrorMessage(String err) {

    }
}
