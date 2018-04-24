package com.android.similarwx.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.MultipleItemQuickAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.MIMultiItem;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.dialog.RedDialogFragment;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;
import com.android.similarwx.widget.input.InputPanel;
import com.android.similarwx.widget.input.actions.BaseAction;
import com.android.similarwx.widget.input.actions.BillAciton;
import com.android.similarwx.widget.input.actions.CashAction;
import com.android.similarwx.widget.input.actions.ContactAdminAction;
import com.android.similarwx.widget.input.actions.ImageAction;
import com.android.similarwx.widget.input.actions.RechargeAciton;
import com.android.similarwx.widget.input.actions.RedAction;
import com.android.similarwx.widget.input.actions.ServiceAction;
import com.android.similarwx.widget.input.actions.TransferAciton;
import com.android.similarwx.widget.input.module.Container;
import com.android.similarwx.widget.input.module.ModuleProxy;
import com.android.similarwx.widget.input.sessions.Extras;
import com.android.similarwx.widget.input.sessions.SessionCustomization;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private int flag=DELETE_GROUP_EIGHT;

    Observer<List<IMMessage>> incomingMessageObserver;
    IMMessage author;
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
            sessionId = bundle.getString(AppConstants.CHAT_ACCOUNT_ID);
            sessionType = (SessionTypeEnum) bundle.getSerializable(AppConstants.CHAT_TYPE);
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

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        miRecyclerView.setLayoutManager(linearLayoutManager);
        miRecyclerView.setAdapter(multipleItemAdapter);
        multipleItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showRedDialog(position);
            }
        });
        multipleItemAdapter.setUpFetchEnable(true);
        multipleItemAdapter.setStartUpFetchPosition(2);
        multipleItemAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                multipleItemAdapter.setUpFetching(true);
                Toaster.toastShort("下拉加载了！");
                multipleItemAdapter.setUpFetching(false);
            }
        });
        miRecyclerView.requestFocus();
        //注册云信消息接受者
        incomingMessageObserver =new Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> imMessages) {
                for (IMMessage imMessage:imMessages){
                    if(imMessage.getSessionId().equals(sessionId)){
                        MultipleItem multipleItem=new MultipleItem(imMessage);
                        multipleItem.setName("paopao");
                        multipleItemAdapter.addData(multipleItem);
                    }
                }
            }
        };
        mActionbar.getTitleView().requestFocus();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver,true);
    }

    private void showRedDialog(int position) {
       RedDialogFragment redDialogFragment= RedDialogFragment.newInstance("");
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.add(redDialogFragment,"redDialog");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void fetchLocalData() {
        author=MessageBuilder.createTextMessage(sessionId,sessionType,"");
        NIMClient.getService(MsgService.class).queryMessageListEx(author, QueryDirectionEnum.QUERY_OLD,
                20, true).setCallback(new RequestCallback<List<IMMessage>>() {
            @Override
            public void onSuccess(List<IMMessage> param) {
                if (param!=null && param.size()>0){
                    author=param.get(0);
                    List<MultipleItem> list=new ArrayList<>();
                    for (IMMessage imMessage:param){
                        MultipleItem multipleItem=new MultipleItem(imMessage);
                        multipleItem.setName("测试11");
                        list.add(multipleItem);
                    }
                    multipleItemAdapter.addData(0,list);
                }
            }

            @Override
            public void onFailed(int code) {

            }

            @Override
            public void onException(Throwable exception) {

            }
        });
    }

    @Override
    protected void fetchData() {
        fetchLocalData();
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
            actions.add(new ImageAction());
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
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver,false);
        multipleItemAdapter.getData().clear();
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
        if(msg!=null){
            NIMClient.getService(MsgService.class).sendMessage(msg,false);
            MultipleItem multipleItem=new MultipleItem(msg);
            multipleItem.setName("测试11");
            multipleItemAdapter.addData(multipleItem);
        }

        return true;
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
