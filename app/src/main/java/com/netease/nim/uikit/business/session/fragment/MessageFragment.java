package com.netease.nim.uikit.business.session.fragment;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.BaseBean;
import com.android.similarwx.beans.CanGrabBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.Transfer;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.inteface.MiViewInterface;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.inteface.message.TransCustomAttachment;
import com.android.similarwx.misdk.model.CustomAttachment;
import com.android.similarwx.present.CashPresent;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.audio.AudioPlayer;
import com.android.similarwx.utils.audio.MediaManager;
import com.android.similarwx.widget.dialog.RedResultDialogFragment;
import com.android.similarwx.widget.input.actions.BillAciton;
import com.android.similarwx.widget.input.actions.CashAction;
import com.android.similarwx.widget.input.actions.ContactAdminAction;
import com.android.similarwx.widget.input.actions.RechargeAciton;
import com.android.similarwx.widget.input.actions.RedAction;
import com.android.similarwx.widget.input.actions.ServiceAction;
import com.android.similarwx.widget.input.actions.TransferAciton;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.api.model.main.CustomPushContentProvider;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nim.uikit.business.ait.AitManager;
import com.netease.nim.uikit.business.session.actions.BaseAction;
import com.netease.nim.uikit.business.session.actions.ImageAction;
import com.netease.nim.uikit.business.session.actions.LocationAction;
import com.netease.nim.uikit.business.session.actions.VideoAction;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.business.session.constant.Extras;
import com.netease.nim.uikit.business.session.module.Container;
import com.netease.nim.uikit.business.session.module.ModuleProxy;
import com.netease.nim.uikit.business.session.module.input.InputPanel;
import com.netease.nim.uikit.business.session.module.list.MessageListPanelEx;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.MemberPushOption;
import com.netease.nimlib.sdk.msg.model.MessageReceipt;
import com.netease.nimlib.sdk.robot.model.NimRobotInfo;
import com.netease.nimlib.sdk.robot.model.RobotAttachment;
import com.netease.nimlib.sdk.robot.model.RobotMsgType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天界面基类
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class MessageFragment extends TFragment implements ModuleProxy, MiViewInterface {

    private View rootView;

    private SessionCustomization customization;

    protected static final String TAG = "MessageActivity";

    // 聊天对象
    protected String sessionId; // p2p对方Account或者群id

    protected SessionTypeEnum sessionType;

    // modules
    protected InputPanel inputPanel;
    protected MessageListPanelEx messageListPanel;

    protected AitManager aitManager;

    private MIPresent miPresent;
    private Gson gson;
    private int sound=0;
    Container container;
    IMMessage anchor;
    private ImmersionBar mImmersionBar;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mImmersionBar=ImmersionBar.with(getActivity(),this);
//        mImmersionBar.keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
//                //  .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
//                //                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //软键盘自动弹出
//                .statusBarDarkFont(false, 0.2f)
//                .statusBarColor(R.color.color_black_383A3E)
//                .fitsSystemWindows(true)
//                .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
//                    @Override
//                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
//                       if (isPopup){//isPopup为true，软键盘弹出，为false，软键盘关闭
//
//                       }
//                    }
//                })
//                .init();
        miPresent=new MIPresent(this, (AppCompatActivity) getActivity());

        gson=new Gson();
        sound=SharePreferenceUtil.getInt(getActivity(),AppConstants.USER_SOUND_SET);
        rootView = inflater.inflate(R.layout.nim_message_fragment_hhl, container, false);
        return rootView;
    }

    /**
     * ***************************** life cycle *******************************
     */

    @Override
    public void onPause() {
        super.onPause();

        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE,
                SessionTypeEnum.None);
        inputPanel.onPause();
        messageListPanel.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        messageListPanel.reload(container, anchor);
        messageListPanel.onResume();
        NIMClient.getService(MsgService.class).setChattingAccount(sessionId, sessionType);
        getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL); // 默认使用听筒播放
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messageListPanel.onDestroy();
        registerObservers(false);
        if (inputPanel != null) {
            inputPanel.onDestroy();
        }
        if (aitManager != null) {
            aitManager.reset();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    public boolean onBackPressed() {
        if (inputPanel.collapse(true)) {
            return true;
        }

        if (messageListPanel.onBackPressed()) {
            return true;
        }
        return false;
    }

    public void refreshMessageList() {
        messageListPanel.refreshMessageList();
    }

    private void parseIntent() {
        sessionId = getArguments().getString(Extras.EXTRA_ACCOUNT);
        sessionType = (SessionTypeEnum) getArguments().getSerializable(Extras.EXTRA_TYPE);
        anchor = (IMMessage) getArguments().getSerializable(Extras.EXTRA_ANCHOR);

        customization = (SessionCustomization) getArguments().getSerializable(Extras.EXTRA_CUSTOMIZATION);
        container = new Container(getActivity(), sessionId,"", sessionType, this);

        if (messageListPanel == null) {
            messageListPanel = new MessageListPanelEx(container, rootView, anchor, false, false);
        } else {
            messageListPanel.reload(container, anchor);
        }

        if (inputPanel == null) {
            inputPanel = new InputPanel(container, rootView, getActionList());
            inputPanel.setCustomization(customization);
        } else {
            inputPanel.reload(container, customization);
        }

        initAitManager();

        inputPanel.switchRobotMode(NimUIKitImpl.getRobotInfoProvider().getRobotByAccount(sessionId) != null);

        registerObservers(true);

        if (customization != null) {
            messageListPanel.setChattingBackground(customization.backgroundUri, customization.backgroundColor);
        }
    }

    private void initAitManager() {
        UIKitOptions options = NimUIKitImpl.getOptions();
        if (options.aitEnable) {
            aitManager = new AitManager(getContext(), options.aitTeamMember && sessionType == SessionTypeEnum.Team ? sessionId : null, options.aitIMRobot);
            inputPanel.addAitTextWatcher(aitManager);
            aitManager.setTextChangeListener(inputPanel);
        }
    }

    /**
     * ************************* 消息收发 **********************************
     */
    // 是否允许发送消息
    protected boolean isAllowSendMessage(final IMMessage message) {
        return true;
    }

    /**
     * ****************** 观察者 **********************
     */

    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(incomingMessageObserver, register);
        // 已读回执监听
        if (NimUIKitImpl.getOptions().shouldHandleReceipt) {
            service.observeMessageReceipt(messageReceiptObserver, register);
        }
    }

    /**
     * 消息接收观察者
     */
    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }
            for (IMMessage message:messages){
                if (message.getMsgType()==MsgTypeEnum.tip){
                    Map<String, Object> content=message.getRemoteExtension();
                    if (content!=null){
                        String redPacTipMessageType= (String) content.get("redPacTipMessageType");
                        String accId= (String) content.get("accId");
                        String myAccId=SharePreferenceUtil.getString(getActivity(),AppConstants.USER_ACCID,"");
                        if (redPacTipMessageType.equals("emptyTipsMessage")){//禁言tip
                            if (myAccId.equals(accId)){//对我进行禁言、解禁操作了
                                String status= (String) content.get("status");
                                AppConstants.USER_TEAM_IS_MUT=status;
                            }
                        }
                    }
                }
            }
            messageListPanel.onIncomingMessage(messages);
            sendMsgReceipt(); // 发送已读回执
        }
    };

    private Observer<List<MessageReceipt>> messageReceiptObserver = new Observer<List<MessageReceipt>>() {
        @Override
        public void onEvent(List<MessageReceipt> messageReceipts) {
            receiveReceipt();
        }
    };


    /**
     * ********************** implements ModuleProxy *********************
     */
    @Override
    public boolean sendMessage(IMMessage message) {
        if (!isAllowSendMessage(message)) {
            return false;
        }
        if (sessionType == SessionTypeEnum.P2P){//单聊
            String transfer= AppConstants.USER_TRANSFER;
            if (!TextUtils.isEmpty(transfer)){
                if (transfer.equals("0")){
                    if (message.getMsgType()==MsgTypeEnum.custom){
                        FragmentActivity activity=getActivity();
                        if (activity instanceof P2PMessageActivity){
                            boolean my=((P2PMessageActivity)activity).myIsNormal;
                            boolean you=((P2PMessageActivity)activity).youIsNormal;
                            if (my && you){
                                Toaster.toastShort("已禁止转账！");
                                return false;
                            }
                        }
                    }
                }
            }
            String personChat= AppConstants.USER_PERSON_CHAT;
            if (!TextUtils.isEmpty(personChat)){
                if (personChat.equals("0")){
                    if (!(message.getMsgType()==MsgTypeEnum.custom)){
                        FragmentActivity activity=getActivity();
                        if (activity instanceof P2PMessageActivity){
                            boolean my=((P2PMessageActivity)activity).myIsNormal;
                            boolean you=((P2PMessageActivity)activity).youIsNormal;
                            if (my && you){
                                Toaster.toastShort("已禁止私聊！");
                                return false;
                            }
                        }
                    }
                }
            }
        }else if (sessionType == SessionTypeEnum.Team){ //群聊
            String isMut=AppConstants.USER_TEAM_IS_MUT;
            if (!TextUtils.isEmpty(isMut)){
                if (isMut.equals("3")){
                    if (message.getMsgType()==MsgTypeEnum.custom){

                    }else if (message.getMsgType()==MsgTypeEnum.tip){

                    }else {
                        Toaster.toastShort("已禁言！");
                        return false;
                    }
                }
            }
        }
        appendTeamMemberPush(message);
        message = changeToRobotMsg(message);
        final IMMessage msg = message;
        appendPushConfig(message);
        // send message to server and save to db
        NIMClient.getService(MsgService.class).sendMessage(message, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {

            }

            @Override
            public void onFailed(int code) {
                sendFailWithBlackList(code, msg);
            }

            @Override
            public void onException(Throwable exception) {

            }
        });

        messageListPanel.onMsgSend(message);

        if (aitManager != null) {
            aitManager.reset();
        }
        //发送的声音
        if (sound==1)
            MediaManager.playSendMessageSoundDefault(getActivity(),null);
        return true;
    }

    public boolean sendMessage(IMMessage message,SendRed.SendRedBean data) {
        if (!isAllowSendMessage(message)) {
            return false;
        }
        if (sessionType == SessionTypeEnum.P2P){//单聊
            String transfer= AppConstants.USER_TRANSFER;
            if (!TextUtils.isEmpty(transfer)){
                if (transfer.equals("0")){
                    if (message.getMsgType()==MsgTypeEnum.custom){
                        FragmentActivity activity=getActivity();
                        if (activity instanceof P2PMessageActivity){
                            boolean my=((P2PMessageActivity)activity).myIsNormal;
                            boolean you=((P2PMessageActivity)activity).youIsNormal;
                            if (my && you){
                                Toaster.toastShort("已禁止转账！");
                                return false;
                            }
                        }
                    }
                }
            }
            String personChat= AppConstants.USER_PERSON_CHAT;
            if (!TextUtils.isEmpty(personChat)){
                if (personChat.equals("0")){
                    if (!(message.getMsgType()==MsgTypeEnum.custom)){
                        FragmentActivity activity=getActivity();
                        if (activity instanceof P2PMessageActivity){
                            boolean my=((P2PMessageActivity)activity).myIsNormal;
                            boolean you=((P2PMessageActivity)activity).youIsNormal;
                            if (my && you){
                                Toaster.toastShort("已禁止私聊！");
                                return false;
                            }
                        };
                    }
                }
            }
        }else if (sessionType == SessionTypeEnum.Team){ //群聊
            String isMut=AppConstants.USER_TEAM_IS_MUT;
            if (!TextUtils.isEmpty(isMut)){
                if (isMut.equals("3")){
                    if (message.getMsgType()==MsgTypeEnum.custom){

                    }else if (message.getMsgType()==MsgTypeEnum.tip){

                    }else {
                        Toaster.toastShort("已禁言！");
                        return false;
                    }
                }
            }
        }
        appendTeamMemberPush(message);
        message = changeToRobotMsg(message);
        final IMMessage msg = message;
        appendPushConfig(message);
        // send message to server and save to db
        NIMClient.getService(MsgService.class).sendMessage(message, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                if (data!=null){
                    String accid= data.getSysAccId();
                    if (!TextUtils.isEmpty(accid))
                        doYunXinTip(0,data);
                }
            }

            @Override
            public void onFailed(int code) {
                sendFailWithBlackList(code, msg);
            }

            @Override
            public void onException(Throwable exception) {

            }
        });

        messageListPanel.onMsgSend(message);

        if (aitManager != null) {
            aitManager.reset();
        }
        //发送的声音
        if (sound==1)
            MediaManager.playSendMessageSoundDefault(getActivity(),null);
        return true;
    }
    // 被对方拉入黑名单后，发消息失败的交互处理
    private void sendFailWithBlackList(int code, IMMessage msg) {
        if (code == ResponseCode.RES_IN_BLACK_LIST) {
            // 如果被对方拉入黑名单，发送的消息前不显示重发红点
            msg.setStatus(MsgStatusEnum.success);
            NIMClient.getService(MsgService.class).updateIMMessageStatus(msg);
            messageListPanel.refreshMessageList();
            // 同时，本地插入被对方拒收的tip消息
            IMMessage tip = MessageBuilder.createTipMessage(msg.getSessionId(), msg.getSessionType());
            tip.setContent(getActivity().getString(R.string.black_list_send_tip));
            tip.setStatus(MsgStatusEnum.success);
            CustomMessageConfig config = new CustomMessageConfig();
            config.enableUnreadCount = false;
            config.enablePush=false;
            tip.setConfig(config);
            NIMClient.getService(MsgService.class).saveMessageToLocal(tip, true);
        }
    }

    private void appendTeamMemberPush(IMMessage message) {
        if (aitManager == null) {
            return;
        }
        if (sessionType == SessionTypeEnum.Team) {
            List<String> pushList = aitManager.getAitTeamMember();
            if (pushList == null || pushList.isEmpty()) {
                return;
            }
            MemberPushOption memberPushOption = new MemberPushOption();
            memberPushOption.setForcePush(true);
            memberPushOption.setForcePushContent(message.getContent());
            memberPushOption.setForcePushList(pushList);
            message.setMemberPushOption(memberPushOption);
        }
    }

    private IMMessage changeToRobotMsg(IMMessage message) {
        if (aitManager == null) {
            return message;
        }
        if (message.getMsgType() == MsgTypeEnum.robot) {
            return message;
        }
        if (isChatWithRobot()) {
            if (message.getMsgType() == MsgTypeEnum.text && message.getContent() != null) {
                String content = message.getContent().equals("") ? " " : message.getContent();
                message = MessageBuilder.createRobotMessage(message.getSessionId(), message.getSessionType(), message.getSessionId(), content, RobotMsgType.TEXT, content, null, null);
            }
        } else {
            String robotAccount = aitManager.getAitRobot();
            if (TextUtils.isEmpty(robotAccount)) {
                return message;
            }
            String text = message.getContent();
            String content = aitManager.removeRobotAitString(text, robotAccount);
            content = content.equals("") ? " " : content;
            message = MessageBuilder.createRobotMessage(message.getSessionId(), message.getSessionType(), robotAccount, text, RobotMsgType.TEXT, content, null, null);

        }
        return message;
    }

    private boolean isChatWithRobot() {
        return NimUIKitImpl.getRobotInfoProvider().getRobotByAccount(sessionId) != null;
    }

    private void appendPushConfig(IMMessage message) {
        CustomPushContentProvider customConfig = NimUIKitImpl.getCustomPushContentProvider();
        if (customConfig != null) {
            String content = customConfig.getPushContent(message);
            Map<String, Object> payload = customConfig.getPushPayload(message);
            if(!TextUtils.isEmpty(content)){
                message.setPushContent(content);
            }
            if (payload != null) {
                message.setPushPayload(payload);
            }
        }
    }

    @Override
    public void onInputPanelExpand() {
        messageListPanel.scrollToBottom();
    }

    @Override
    public void shouldCollapseInputPanel() {
        inputPanel.collapse(false);
    }

    @Override
    public boolean isLongClickEnabled() {
        return !inputPanel.isRecording();
    }

    @Override
    public void onItemFooterClick(IMMessage message) {
        if (aitManager == null) {
            return;
        }
        if (messageListPanel.isSessionMode()) {
            RobotAttachment attachment = (RobotAttachment) message.getAttachment();
            NimRobotInfo robot = NimUIKitImpl.getRobotInfoProvider().getRobotByAccount(attachment.getFromRobotAccount());
            aitManager.insertAitRobot(robot.getAccount(), robot.getName(), inputPanel.getEditSelectionStart());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (aitManager != null) {
            aitManager.onActivityResult(requestCode, resultCode, data);
        }
        inputPanel.onActivityResult(requestCode, resultCode, data);
        messageListPanel.onActivityResult(requestCode, resultCode, data);
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();

        if(sessionType==SessionTypeEnum.Team){
            actions.add(new ImageAction());
            actions.add(new RedAction(this));
            actions.add(new ContactAdminAction());
            actions.add(new BillAciton());
            actions.add(new RechargeAciton());
            actions.add(new CashAction());
            actions.add(new ServiceAction());
        }else if (sessionType==SessionTypeEnum.P2P){
            actions.add(new ImageAction());
//            actions.add(new RedAction());
            actions.add(new TransferAciton(this));
        }

        if (customization != null && customization.actions != null) {
            actions.addAll(customization.actions);
        }
        return actions;
    }

    /**
     * 发送已读回执
     */
    private void sendMsgReceipt() {
        messageListPanel.sendReceipt();
    }

    /**
     * 收到已读回执
     */
    public void receiveReceipt() {
        messageListPanel.receiveReceipt();
    }

    private void doYunXinTip(int finishFlag,SendRed.SendRedBean data) {
        Map<String, Object> content = new HashMap<>(1);
        String accid= data.getSysAccId();
//        content.put("accId", mSendRedBean.getMyUserId());
        content.put("accId", accid);
        content.put("finishFlag",finishFlag);
        Gson gson=new Gson();
        content.put("sendRedBean", gson.toJson(data));
        content.put("redPacTipMessageType","redPacTip");
// 创建tip消息，teamId需要开发者已经存在的team的teamId
        IMMessage msg = MessageBuilder.createTipMessage(sessionId, SessionTypeEnum.Team);
        msg.setRemoteExtension(content);
// 自定义消息配置选项
        CustomMessageConfig config = new CustomMessageConfig();
// 消息不计入未读
        config.enableUnreadCount = false;
        config.enablePush=false;
        msg.setConfig(config);
// 消息发送状态设置为success
        msg.setStatus(MsgStatusEnum.success);

        sendMessage(msg);
    }
    /**
     * 一下是本地服务的几口==========================
     */
    public void senCustemRed(SendRed data) {
        if (data!=null){
            miPresent.sendRed(data);
        }
    }

    public void senCustemTran(Transfer data) {
        if (data!=null){
           sendMessage(createCustomMessage(data));
            Toaster.toastShort("发送成功");
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshCustemRed(SendRed.SendRedBean data) {
        if (data!=null){
            IMMessage imMessage=createCustomMessage(data);
            String sysRed=data.getCotent();
            if (imMessage!=null){
                sendMessage(imMessage,data);
                Toaster.toastShort("发送成功");
            }

        }
    }

    @Override
    public void grabRed(RspGrabRed bean) {

    }

    @Override
    public void canGrab(CanGrabBean bean) {

    }
    /**
     * 创建自定义消息
     * @param redDetailBean
     * @return
     */
    protected IMMessage createCustomMessage(SendRed.SendRedBean redDetailBean) {
        if (redDetailBean!=null){
            RedCustomAttachment redCustomAttachment=new RedCustomAttachment();
            redCustomAttachment.setSendRedBean(redDetailBean);
            return MessageBuilder.createCustomMessage(sessionId, sessionType, "红包",redCustomAttachment);
        }
        return null;
    }

    /**
     * 创建自定义消息
     * @param transfer
     * @return
     */
    protected IMMessage createCustomMessage(Transfer transfer) {
        if (transfer!=null){
            TransCustomAttachment transCustomAttachment=new TransCustomAttachment();
            transCustomAttachment.setTransfer(transfer);

            // 自定义消息配置选项
            CustomMessageConfig config = new CustomMessageConfig();
// 消息不计入未读
            config.enableUnreadCount = false;
            return MessageBuilder.createCustomMessage(sessionId, sessionType, "转账",transCustomAttachment,config);
        }
        return null;
    }
}
