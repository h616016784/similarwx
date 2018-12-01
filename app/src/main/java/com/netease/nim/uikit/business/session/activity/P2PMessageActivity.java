package com.netease.nim.uikit.business.session.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspConfig;
import com.android.similarwx.inteface.ClientDetailInfoViewInterface;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.present.ClientDetailInfoPresent;
import com.android.similarwx.present.SysNoticePresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.contact.ContactChangedObserver;
import com.netease.nim.uikit.api.model.main.OnlineStateChangeObserver;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nim.uikit.api.model.user.UserInfoObserver;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.business.session.constant.Extras;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;
import com.netease.nim.uikit.business.uinfo.UserInfoHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 点对点聊天界面
 * <p/>
 * Created by huangjun on 2015/2/1.
 */
public class P2PMessageActivity extends BaseMessageActivity implements SysNoticeViewInterface, ClientDetailInfoViewInterface {

    private boolean isResume = false;
    SysNoticePresent sysNoticePresent;
    ClientDetailInfoPresent mPresent;
    Timer timer ;
    TimerTask alarmTask ;
    public boolean myIsNormal=true;
    public boolean youIsNormal=true;
    public static void start(Context context, String contactId, SessionCustomization customization, IMMessage anchor) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        if (anchor != null) {
            intent.putExtra(Extras.EXTRA_ANCHOR, anchor);
        }
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresent=new ClientDetailInfoPresent(this,this);
        sysNoticePresent=new SysNoticePresent(this,this);
        User mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(this,AppConstants.USER_OBJECT);
        int serviceFlag=mUser.getServiceFlg();
        int systermFlay=mUser.getSystemFlg();
        int adminFlag=mUser.getAdminFlg();
        if (serviceFlag==0&& systermFlay==0&& adminFlag==0){//普通用户
            myIsNormal=true;

        }else {
            myIsNormal=false;
        }
        //获取用户信息
//        if (!TextUtils.isEmpty(mUser.getAccId()))
            mPresent.getUserInfoByParams("",sessionId,0);

        // 单聊特例话数据，包括个人信息，
        requestBuddyInfo();
        displayOnlineState();
        registerObservers(true);
        registerOnlineStateChangeListener(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        registerOnlineStateChangeListener(false);
        AppConstants.USER_PERSON_CHAT="";
        AppConstants.USER_TRANSFER="";
        if (alarmTask!=null){
            alarmTask.cancel();
            alarmTask=null;
        }
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    private void requestBuddyInfo() {
        setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
    }

    private void registerObservers(boolean register) {
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(commandObserver, register);
        NimUIKit.getContactChangedObservable().registerObserver(friendDataChangedObserver, register);
    }

    ContactChangedObserver friendDataChangedObserver = new ContactChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }
    };

    private UserInfoObserver uinfoObserver;

    OnlineStateChangeObserver onlineStateChangeObserver = new OnlineStateChangeObserver() {
        @Override
        public void onlineStateChange(Set<String> accounts) {
            // 更新 toolbar
            if (accounts.contains(sessionId)) {
                // 按照交互来展示
                displayOnlineState();
            }
        }
    };

    private void registerOnlineStateChangeListener(boolean register) {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        NimUIKitImpl.getOnlineStateChangeObservable().registerOnlineStateChangeListeners(onlineStateChangeObserver, register);
    }

    private void displayOnlineState() {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        String detailContent = NimUIKitImpl.getOnlineStateContentProvider().getDetailDisplay(sessionId);
        detailContent="";
        setSubTitle(detailContent);
    }

    private void registerUserInfoObserver() {
        if (uinfoObserver == null) {
            uinfoObserver = new UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    if (accounts.contains(sessionId)) {
                        requestBuddyInfo();
                    }
                }
            };
        }
        NimUIKit.getUserInfoObservable().registerObserver(uinfoObserver, true);
    }

    private void unregisterUserInfoObserver() {
        if (uinfoObserver != null) {
            NimUIKit.getUserInfoObservable().registerObserver(uinfoObserver, false);
        }
    }

    /**
     * 命令消息接收观察者
     */
    Observer<CustomNotification> commandObserver = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            if (!sessionId.equals(message.getSessionId()) || message.getSessionType() != SessionTypeEnum.P2P) {
                return;
            }
            showCommandMessage(message);
        }
    };

    protected void showCommandMessage(CustomNotification message) {
        if (!isResume) {
            return;
        }

        String content = message.getContent();
        try {
            JSONObject json = JSON.parseObject(content);
            int id = json.getIntValue("id");
            if (id == 1) {
                // 正在输入
                Toast.makeText(P2PMessageActivity.this, "对方正在输入...", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(P2PMessageActivity.this, "command: " + content, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }
    }

    @Override
    protected MessageFragment fragment() {
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.nim_message_activity;
    }

    @Override
    protected void initToolBar() {
        ToolBarOptions options = new NimToolBarOptions();
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshSysNotice(List<Notice> list) {

    }

    @Override
    public void refreshSysMoney(String url) {

    }

    @Override
    public void refreshSysConfig(RspConfig.ConfigBean bean) {
        String personChat=bean.getPersonChat();
        String transfer=bean.getTransfer();
        AppConstants.USER_PERSON_CHAT=personChat;
        AppConstants.USER_TRANSFER=transfer;
    }

    @Override
    public void refreshUserInfo(User user,int flag) {
        if (user!=null){
            int serviceFlag=user.getServiceFlg();
            int systermFlay=user.getSystemFlg();
            int adminFlag=user.getAdminFlg();
            if (serviceFlag==0&& systermFlay==0&& adminFlag==0){//普通用户
                //获取禁言和转账标示
                sysNoticePresent.getConfig();
                youIsNormal=true;
                timer = new Timer();
                alarmTask = new TimerTask() {
                    @Override
                    public void run() {
                        //获取禁言和转账标示
                        sysNoticePresent.getConfig();
                    }
                };
                timer.schedule(alarmTask,10,10*60*1000);//10分钟后每隔10分钟执行一次

            }else {
                youIsNormal=false;
            }
        }
    }

    @Override
    public void refreshUpdateUser() {

    }

    @Override
    public void refreshUpdateUserStatus(String userStatus) {

    }

    @Override
    public void refreshDeleteUser() {

    }
}
