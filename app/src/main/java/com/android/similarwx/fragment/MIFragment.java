package com.android.similarwx.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.AudioManager;
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
import com.android.similarwx.base.AndroidBug5497Workaround;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.CharImageBean;
import com.android.similarwx.beans.MIMultiItem;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.inteface.MiViewInterface;
import com.android.similarwx.misdk.model.CustomAttachment;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.widget.dialog.RedDialogFragment;
import com.android.similarwx.widget.dialog.RedResultDialogFragment;
import com.android.similarwx.widget.dialog.TwoButtonDialogBuilder;
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
import com.google.gson.Gson;
import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.netease.nimlib.sdk.media.player.OnPlayListener;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

//    protected InputPanel inputPanel;
    private SessionCustomization customization;
    // 聊天对象
    protected String sessionId; // p2p对方Account或者群id
    protected SessionTypeEnum sessionType;

    private int flag=DELETE_GROUP_EIGHT;

    Observer<List<IMMessage>> incomingMessageObserver;
    Observer<SystemMessage> systemMessageObserver;
    IMMessage author;
    AudioPlayer player;//播放器

    private MIPresent miPresent;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mi_layout;
    }

    @Override
    protected void onInitView(View contentView) {
        AndroidBug5497Workaround.assistActivity(activity);
//        miPresent=new MIPresent(this);
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
//        View rootView=contentView.findViewById(R.id.messageActivityBottomLayout);
        Container container = new Container(activity, sessionId, sessionType, this);
//        if (inputPanel == null) {
//            inputPanel = new InputPanel(container, contentView, getActionList());
////            inputPanel.setCustomization(customization);
//        } else {
//            inputPanel.reload(container, customization);
//        }

        multipleItemAdapter = new MultipleItemQuickAdapter(activity,null);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity);
        miRecyclerView.setLayoutManager(linearLayoutManager);
        miRecyclerView.setAdapter(multipleItemAdapter);
        addListener();

//        multipleItemAdapter.setUpFetchEnable(true);
//        multipleItemAdapter.setStartUpFetchPosition(0);
//        multipleItemAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
//            @Override
//            public void onUpFetch() {
//                multipleItemAdapter.setUpFetching(true);
//                Toaster.toastShort("Fetching");
//                multipleItemAdapter.setUpFetching(false);
////                multipleItemAdapter.loadMoreComplete();
////                multipleItemAdapter.loadMoreEnd();
////                multipleItemAdapter.loadMoreEnd(true);
//            }
//        });

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
        systemMessageObserver=new Observer<SystemMessage>() {
            @Override
            public void onEvent(SystemMessage systemMessage) {
                MultipleItem multipleItem=new MultipleItem();
                multipleItem.setSystemCotent(systemMessage.getContent());
                multipleItemAdapter.addData(multipleItem);
            }
        };
        mActionbar.getTitleView().requestFocus();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver,true);
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver,true);
    }

    private void addListener() {
        multipleItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleItem bean=multipleItemAdapter.getData().get(position);
                IMMessage imMessage=bean.getImMessage();
                switch (imMessage.getMsgType().getValue()){
                    case MultipleItem.ITEM_IMAGE://图片
                        break;
                    case MultipleItem.ITEM_AUDIO://音频
                        String s=imMessage.getAttachment().toJson(false);
                        Gson gson=new Gson();
                        CharImageBean charImageBean=gson.fromJson(s, CharImageBean.class);
                        String imagePath=charImageBean.getPath();
                        // 构造播放器对象
                        player = new AudioPlayer(activity,imagePath, listener);
                        player.start( AudioManager.STREAM_VOICE_CALL);
                        break;
                    case MultipleItem.ITEM_RED://红包
//                        RedResultDialogFragment.show(activity);
                        break;
                }

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
            actions.add(new ImageAction());
//            actions.add(new RedAction(this));
            actions.add(new ContactAdminAction());
            actions.add(new BillAciton());
            actions.add(new RechargeAciton());
            actions.add(new CashAction());
            actions.add(new ServiceAction());
        }else if (flag==DELETE_THREE){
            actions.add(new ImageAction());
//            actions.add(new RedAction());
//            actions.add(new TransferAciton(this));
        }

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        inputPanel.onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver,false);
        NIMClient.getService(SystemMessageObserver.class).observeReceiveSystemMsg(systemMessageObserver,false);
        multipleItemAdapter.getData().clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null)
            player.stop();
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
            switch (msg.getMsgType()){
                case custom://自定义
                    Log.e("sendMessg","custom");
                    break;
            }
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

    /**
     * 以下是音频播放的监听
     */
    // 定义一个播放进程回调类
    OnPlayListener listener = new OnPlayListener() {

        // 音频转码解码完成，会马上开始播放了
        public void onPrepared() {}

        // 播放结束
        public void onCompletion() {}

        // 播放被中断了
        public void onInterrupt() {}

        // 播放过程中出错。参数为出错原因描述
        public void onError(String error){}

        // 播放进度报告，每隔 500ms 会回调一次，告诉当前进度。 参数为当前进度，单位为毫秒，可用于更新 UI
        public void onPlaying(long curPosition) {}
    };

    //===========================一下是view接口实现

    /**
     * 创建自定义消息
     * @param redDetailBean
     * @return
     */
    protected IMMessage createCustomMessage(RedDetailBean  redDetailBean) {
        return MessageBuilder.createCustomMessage(sessionId, sessionType, "红包",
                new CustomAttachment<RedDetailBean>(redDetailBean));
    }
}
