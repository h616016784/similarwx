package com.android.similarwx.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.BaseDecoration;
import com.android.similarwx.adapter.MIMultiItemQuickAdapter;
import com.android.similarwx.adapter.MultipleItemQuickAdapter;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.MIMultiItem;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.widget.dialog.RedDialogFragment;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;
import com.android.similarwx.widget.input.InputPanel;
import com.android.similarwx.widget.input.actions.BaseAction;
import com.android.similarwx.widget.input.actions.BillAciton;
import com.android.similarwx.widget.input.actions.CashAction;
import com.android.similarwx.widget.input.actions.ContactAdminAction;
import com.android.similarwx.widget.input.actions.RechargeAciton;
import com.android.similarwx.widget.input.actions.RedAction;
import com.android.similarwx.widget.input.actions.ServiceAction;
import com.android.similarwx.widget.input.actions.TransferAciton;
import com.android.similarwx.widget.input.module.Container;
import com.android.similarwx.widget.input.module.ModuleProxy;
import com.android.similarwx.widget.input.sessions.Extras;
import com.android.similarwx.widget.input.sessions.SessionCustomization;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/3/28.
 */

public class MIFragment extends BaseFragment implements ModuleProxy {
    public static final int DELETE_THREE=0;
    public static final int DELETE_GROUP_EIGHT=1;
    public static final String MIFLAG="miFlag";
    List<MIMultiItem> list;
    @BindView(R.id.mi_recyclerView)
    RecyclerView miRecyclerView;
    Unbinder unbinder;

    private MultipleItemQuickAdapter multipleItemAdapter;
    private List<MultipleItem> data;
    protected InputPanel inputPanel;
    private SessionCustomization customization;
    // 聊天对象
    protected String sessionId; // p2p对方Account或者群id
    protected SessionTypeEnum sessionType;

    private int flag=1;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mi_layout;
    }

    @Override
    protected void onInitView(View contentView) {
        Bundle bundle=getArguments();
        if (bundle!=null){
            flag=bundle.getInt(MIFLAG);
            customization = (SessionCustomization) bundle.getSerializable(Extras.EXTRA_CUSTOMIZATION);
            sessionId = bundle.getString(Extras.EXTRA_ACCOUNT);
            sessionType = (SessionTypeEnum) bundle.getSerializable(Extras.EXTRA_TYPE);
        }
        unbinder=ButterKnife.bind(this, contentView);
        if (flag==DELETE_THREE){
            mActionbar.setRightImage(R.drawable.action_right_delete);
            mActionbar.setRightImageOnClickListener(this);
        }else if(flag==DELETE_GROUP_EIGHT){
            mActionbar.setRightImage(R.drawable.action_right_delete);
            mActionbar.setRightImagePeople(R.drawable.action_right_people);
            mActionbar.setRightImageOnClickListener(this);
            mActionbar.setRightImagePeopleOnClickListener(this);
        }

        View rootView=contentView.findViewById(R.id.messageActivityBottomLayout);
        Container container = new Container(activity, sessionId, sessionType, this);
        if (inputPanel == null) {
            inputPanel = new InputPanel(container, rootView, getActionList());
//            inputPanel.setCustomization(customization);
        } else {
            inputPanel.reload(container, customization);
        }
        multipleItemAdapter = new MultipleItemQuickAdapter(activity,null);
        multipleItemAdapter.setUpFetchEnable(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        miRecyclerView.setLayoutManager(linearLayoutManager);
        miRecyclerView.setAdapter(multipleItemAdapter);
        multipleItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showRedDialog(position);
            }
        });

        multipleItemAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                multipleItemAdapter.setUpFetching(true);
                Toaster.toastShort("下拉加载了！");
                multipleItemAdapter.setUpFetching(false);
            }
        });
    }

    private void showRedDialog(int position) {
       RedDialogFragment redDialogFragment= RedDialogFragment.newInstance("");
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.add(redDialogFragment,"redDialog");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private List<MultipleItem> initData() {
        List<MultipleItem> list = new ArrayList<>();
        MultipleItem multipleItem=new MultipleItem(MultipleItem.ITEM_VIEW_TYPE_MSG, MultipleItem.IMG_SPAN_SIZE);
        multipleItem.setContent("这是我的第一次谈话记录，啊哈哈哈哈哈哈哈哈哈哈哈");
        list.add(multipleItem);
        MultipleItem multipleItem1=new MultipleItem(MultipleItem.ITEM_VIEW_TYPE_MSG_RED, MultipleItem.IMG_SPAN_SIZE);
        multipleItem1.setContent("16-2");
        list.add(multipleItem1);
        MultipleItem multipleItem2=new MultipleItem(MultipleItem.ITEM_VIEW_TYPE_MSG_SYS, MultipleItem.IMG_SPAN_SIZE);
        multipleItem2.setContent("啊格加入了该群！");
        list.add(multipleItem2);
        return list;
    }

    @Override
    protected void fetchData() {
        data= initData();
        multipleItemAdapter.addData(data);
    }

    /**
     * 如果需要创建fragment就加载数据的话 此方法返回true，就会调用fetchData方法
     *
     * @return
     */
    @Override
    protected boolean isNeedFetch() {
        return true;
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        if(flag==DELETE_GROUP_EIGHT){
//            actions.add(new ImageAction());
            actions.add(new RedAction());
            actions.add(new ContactAdminAction());
            actions.add(new BillAciton());
            actions.add(new RechargeAciton());
            actions.add(new CashAction());
            actions.add(new ServiceAction());
        }else if (flag==DELETE_THREE){
//            actions.add(new ImageAction());
            actions.add(new RedAction());
            actions.add(new TransferAciton());
        }


//        actions.add(new VideoAction());
//        actions.add(new LocationAction());

        if (customization != null && customization.actions != null) {
            actions.addAll(customization.actions);
        }
        return actions;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.right_image:
                showDeleteDialog();
                break;
            case R.id.right_image_people:
                FragmentUtils.navigateToNormalActivity(activity,new GroupInfoFragment(),null);
                break;
        }
    }

    private void showDeleteDialog() {
        TwoButtonDialogBuilder twoButtonDialogBuilder=new TwoButtonDialogBuilder(activity);
        twoButtonDialogBuilder.setMessage(R.string.chart_delete_dialog_message);
        twoButtonDialogBuilder.setConfirmButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        twoButtonDialogBuilder.create().show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
/*   ==========================================================*/
    /**
     * 以下是实现了ModuleProxy接口的方法
     * @param msg
     * @return
     */
    @Override
    public boolean sendMessage(IMMessage msg) {
        return false;
    }

    @Override
    public void onInputPanelExpand() {

    }

    @Override
    public void shouldCollapseInputPanel() {

    }

    @Override
    public boolean isLongClickEnabled() {
        return false;
    }

    @Override
    public void onItemFooterClick(IMMessage message) {

    }
}
