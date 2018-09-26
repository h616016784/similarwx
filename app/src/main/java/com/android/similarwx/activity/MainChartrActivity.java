package com.android.similarwx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.HomeAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.BaseDialog;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.beans.User;
import com.android.similarwx.fragment.AddGroupFragment;
import com.android.similarwx.fragment.ChartFragment;
import com.android.similarwx.fragment.ExplainFragment;
import com.android.similarwx.fragment.MIFragment;
import com.android.similarwx.fragment.MIFragmentNew;
import com.android.similarwx.fragment.MyFragment;
import com.android.similarwx.fragment.NoticeFragment;
import com.android.similarwx.fragment.SearchFragment;
import com.android.similarwx.fragment.ServiceFragment;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.inteface.MainGroupView;
import com.android.similarwx.inteface.NoticeViewInterface;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.present.NoticePresent;
import com.android.similarwx.service.reminder.ReminderItem;
import com.android.similarwx.service.reminder.ReminderManager;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.NetUtil;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.notification.NotificationUtil;
import com.android.similarwx.widget.ListPopWindow;
import com.android.similarwx.widget.ListPopWindowHelper;
import com.android.similarwx.widget.dialog.CancelDialogBuilder;
import com.android.similarwx.widget.dialog.EasyAlertDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
import com.android.similarwx.widget.dialog.EditDialogBuilder;
import com.android.similarwx.widget.dialog.EditDialogSimple;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.SimpleCallback;
import com.netease.nim.uikit.business.recent.TeamMemberAitHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/1.
 */

public class MainChartrActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, MainGroupView, NoticeViewInterface, ReminderManager.UnreadNumChangedCallback {

    Unbinder unbinder;
    @BindView(R.id.main_search_iv)
    ImageView mainSearchIv;
    @BindView(R.id.create_group_iv)
    ImageView createGroupIv;
    @BindView(R.id.main_search_et)
    EditText mainSearchEt;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.main_rl_explain)
    RelativeLayout mainRlExplain;
    @BindView(R.id.main_rl_chart)
    RelativeLayout mainRlChart;
    @BindView(R.id.main_net_rl)
    RelativeLayout mainNeyRl;
    @BindView(R.id.main_load_rl)
    RelativeLayout mainLoadRl;
    @BindView(R.id.main_my_chart)
    RelativeLayout mainMyChart;
    @BindView(R.id.main_explain_red_iv)
    ImageView mainExplainRedIv;
    @BindView(R.id.main_rl_chart_red_iv)
    ImageView mainChartRedIv;

    private HomeAdapter adapter;
    private List<GroupMessageBean.ListBean> mListData;
    GroupPresent groupPresent;
    private ListPopWindowHelper listPopWindowHelper=null;
    private List<PopMoreBean> listMore=null;

    private User mUser;
    private NoticePresent noticePresent;
    private long tempMsgId=-1;
    private boolean isNormal=true;
    BaseDialog dialog;
    public static void start(Activity context) {
        Intent intent = new Intent(context, MainChartrActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian_lt);
        unbinder = ButterKnife.bind(this);
        groupPresent = new GroupPresent(this);
        noticePresent=new NoticePresent(this);
        initYunXinSystemMsgListener();
        registerMsgUnreadInfoObserver(true);
        registerUserOnlineStatus(true);
//        requestSystemMessageUnreadCount();
        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(this,AppConstants.USER_OBJECT);

        if (mUser!=null){
            int systemFlg=mUser.getSystemFlg();
            int adminFlg=mUser.getAdminFlg();
            int serviceFlg=mUser.getServiceFlg();
            if (systemFlg==0 && adminFlg==0 && serviceFlg==0 ){
                isNormal=true;
                createGroupIv.setVisibility(View.VISIBLE);
            }else {
                isNormal=false;
                createGroupIv.setVisibility(View.VISIBLE);
            }
        }else {
            Toaster.toastShort("获取本地数据异常！");
            finishApp();
        }
        initLoacalData();

        adapter = new HomeAdapter(R.layout.item_group, this, mListData,mainChartRedIv,mainExplainRedIv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.requestFocus();
        adapter.setOnItemClickListener(this);

        hideKeyboard();
    }
    private void initYunXinSystemMsgListener() {
        NIMClient.getService(SystemMessageObserver.class)
                .observeReceiveSystemMsg(new Observer<SystemMessage>() {
                    @Override
                    public void onEvent(SystemMessage message) {
                        // 收到系统通知，可以做相应操作
                        if (message!=null){
                            if (!(tempMsgId==-1)){
                                if (tempMsgId==message.getMessageId()){
                                    return;
                                }
                            }
                            String account=message.getFromAccount();
                            String target=message.getTargetId();
                            NimUserInfo fromUser = NIMClient.getService(UserService.class).getUserInfo(account);//发来群消息的用户信息
                            Team team=NIMClient.getService(TeamService.class).queryTeamBlock(target);
                            String teamName=team.getName();
                            if (TextUtils.isEmpty(teamName))
                                teamName="";
                            NotificationUtil.NotificationConfig config=new NotificationUtil.NotificationConfig();
                            config.setContentTitle("群组通知");
                            switch (message.getType().getValue()){
                                case 0:
                                    config.setContentText(fromUser.getName()+" 入群申请 "+teamName);
                                    break;
                                case 1:
                                    config.setContentText(fromUser.getName()+" 拒绝入群 "+teamName);
                                    break;
                                case 2:
                                    config.setContentText(fromUser.getName()+" 邀请入群 "+teamName );
                                    break;
                                case 3:
                                    config.setContentText(fromUser.getName()+" 拒绝邀请 "+teamName);
                                    break;
                                case 5:
                                    config.setContentText(fromUser.getName()+" 添加好友 "+teamName);
                                    break;
                                default:
                                    config.setContentText("未知");
                            }
                            config.setTicker("通知消息");
                            NotificationUtil.getInstance(MainChartrActivity.this,config).notification();

                            tempMsgId=message.getMessageId();
                        }
                    }
                }, true);
    }

    private void initLoacalData() {
        listMore=new ArrayList<>();
        if (!isNormal){
            PopMoreBean bean=new PopMoreBean();
            bean.setName("创建群组");
            bean.setImage(R.drawable.icon_top_add);
            listMore.add(bean);
        }
        PopMoreBean bean1=new PopMoreBean();
        bean1.setName("查找用户");
        bean1.setImage(R.drawable.icon_top_search);
        listMore.add(bean1);

        PopMoreBean bean2=new PopMoreBean();
        bean2.setName("查找群组");
        bean2.setImage(R.drawable.icon_top_search);
        listMore.add(bean2);
    }


    @Override
    protected void onResume() {
        super.onResume();
        groupPresent.getGroupList();
        mainLoadRl.setVisibility(View.VISIBLE);
        checkNet();
        enableMsgNotification(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        enableMsgNotification(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        registerUserOnlineStatus(false);
//        listPopWindowHelper.destroy();
    }

    private void checkNet() {
        if (NetUtil.getAPNType(this)==0){//没有网络
            mainNeyRl.setVisibility(View.VISIBLE);
        }else {
            mainNeyRl.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.main_search_iv, R.id.main_rl_chart, R.id.main_my_chart,R.id.create_group_iv,R.id.main_rl_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_search_iv:
                break;
            case R.id.create_group_iv:
                listPopWindowHelper=new ListPopWindowHelper(MainChartrActivity.this,listMore);
                listPopWindowHelper.show(createGroupIv,0,24);
                listPopWindowHelper.setOnClickItem(new ListPopWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PopMoreBean popMoreBean=listMore.get(position);
                        if (popMoreBean.getName().equals("创建群组")){
                            FragmentUtils.navigateToNormalActivity(MainChartrActivity.this, new AddGroupFragment(), null);
                        }else if (popMoreBean.getName().equals("查找用户")){
                            Bundle bundle=new Bundle();
                            bundle.putInt(AppConstants.TRANSFER_BASE,1);
                            FragmentUtils.navigateToNormalActivity(MainChartrActivity.this, new SearchFragment(), bundle);
                        }else if (popMoreBean.getName().equals("查找群组")){
                            Bundle bundle=new Bundle();
                            bundle.putInt(AppConstants.TRANSFER_BASE,2);
                            FragmentUtils.navigateToNormalActivity(MainChartrActivity.this, new SearchFragment(), bundle);
                        }
                    }
                });
                break;
            case R.id.main_rl_find://发现
                FragmentUtils.navigateToNormalActivity(this, new ExplainFragment(), null);//此功能去掉
                break;
            case R.id.main_rl_chart://消息
                Bundle bundle = new Bundle();
                bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_THREE);
                FragmentUtils.navigateToNormalActivity(this, new ChartFragment(), bundle);
//                FragmentUtils.navigateToNormalActivity(this,new MIFragment(),bundle);
                break;
            case R.id.main_my_chart://我的
                FragmentUtils.navigateToNormalActivity(this, new MyFragment(), null);
                break;
        }
    }

    /**
     * 点击群列表回调函数
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
        if (position == 0) {//通知
            startActivity(new Intent(this,SysNoticeActivity.class));
//            FragmentUtils.navigateToNormalActivity(this, new NoticeFragment(), null);
        } else if (position == 1) {//在线客服
            FragmentUtils.navigateToNormalActivity(this, new ServiceFragment(), null);
        } else {
            final GroupMessageBean.ListBean bean=mListData.get(position);
            if (bean.getUserExists().equals("0")){//不在群里
                //先调用云信的判断
                NimUIKit.getTeamProvider().fetchTeamById(bean.getGroupId(), new SimpleCallback<Team>() {
                    @Override
                    public void onResult(boolean success, Team result, int code) {
                        if (success && result != null) {
                            if (result.isMyTeam()){//在群里面
                                NimUIKit.startTeamSession(MainChartrActivity.this, bean.getGroupId());
                            }else {
                                //这回真的不在群里面了
                                String joinmode=bean.getJoinmode();
                                if (!TextUtils.isEmpty(joinmode)){
                                    if (joinmode.equals("0")){//允许任何人加入
                                        doInGroupByAnyOne(bean);
                                    }else if (joinmode.equals("1")){
                                        EasyAlertDialog  mDialog=EasyAlertDialogHelper.createOkCancelDiolag(MainChartrActivity.this,bean.getGroupName(),"是否加入该群?","是","否",true, new EasyAlertDialogHelper.OnDialogActionListener() {
                                            @Override
                                            public void doCancelAction() {

                                            }
                                            @Override
                                            public void doOkAction() {
                                                Gson gson=new Gson();
                                                mUser.setPasswd("申请加入该群");
                                                mUser.setPasswdStr(bean.getGroupName());
                                                Map<String,String> map=SharePreferenceUtil.getHashMapData(MainChartrActivity.this,AppConstants.USER_MAP_OBJECT);
                                                if (map!=null){
                                                    String applyFlag=map.get(bean.getGroupId());
                                                    if (TextUtils.isEmpty(applyFlag)){
                                                        APIYUNXIN.applyJoinTeam(MainChartrActivity.this,bean.getGroupId(), "", new YCallBack<Team>() {
                                                            @Override
                                                            public void callBack(Team team) {
                                                                Toaster.toastShort("申请成功，等待群主审批");
                                                            }
                                                        });
                                                    }else {
                                                        showQuitDialog("已经发过申请，请等待");
                                                    }
                                                }

                                            }
                                        });
                                        mDialog.show();
                                    }else {

                                    }
                                }else {
                                    EasyAlertDialog  mDialog=EasyAlertDialogHelper.createOkCancelDiolag(MainChartrActivity.this,bean.getGroupName(),"是否加入该群?","是","否",true, new EasyAlertDialogHelper.OnDialogActionListener() {
                                        @Override
                                        public void doCancelAction() {

                                        }
                                        @Override
                                        public void doOkAction() {
                                            Gson gson=new Gson();
                                            mUser.setPasswd("申请加入该群");
                                            mUser.setPasswdStr(bean.getGroupName());
                                             Map<String,String> map=SharePreferenceUtil.getHashMapData(MainChartrActivity.this,AppConstants.USER_MAP_OBJECT);
                                            if (map!=null){
                                                String applyFlag=map.get(bean.getGroupId());
                                                if (TextUtils.isEmpty(applyFlag)){
                                                    APIYUNXIN.applyJoinTeam(MainChartrActivity.this,bean.getGroupId(), "", new YCallBack<Team>() {
                                                        @Override
                                                        public void callBack(Team team) {
                                                            Toaster.toastShort("申请成功，等待群主审批");
                                                        }
                                                    });
                                                }else {
                                                    showQuitDialog("已经发过申请，请等待");
                                                }
                                            }
                                        }
                                    });
                                    mDialog.show();
                                }
                            }
                        }else {
                            Toaster.toastShort("获取群组信息失败!");
                        }
                    }
                });
            }else {//在群里  直接进入
                NimUIKit.startTeamSession(this, bean.getGroupId());
            }
        }
    }

    /**
     * 展示申请信息
     * @param msg
     */
    private void showQuitDialog(String msg) {
        final CancelDialogBuilder cancel_dialogBuilder = CancelDialogBuilder
                .getInstance(this);

        cancel_dialogBuilder.setTitleText(msg);
        cancel_dialogBuilder.setDetermineText("确定");

        cancel_dialogBuilder.isCancelableOnTouchOutside(true)
                .setCancelNone()
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_dialogBuilder.dismiss();
                    }
        }).show();
    }
    private void doInGroupByAnyOne(GroupMessageBean.ListBean bean) {
        String accid=SharePreferenceUtil.getString(this,AppConstants.USER_ACCID,"");
        noticePresent.doAddGroupUser(bean.getGroupId(),accid);
    }

    /**
     * 申请加入群组,不用了。。。。
     */
    private void doGroupApply(String groupId) {
        groupPresent.doGroupAppley(groupId);
    }
    /**
     * 查询系统消息未读数,这个项目没有用到
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }
    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
//        if (register) {
//            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
//        } else {
//            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
//        }
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(messageReceiverObserver, register);
    }

    /**
     * 注册用户在线状态
     * @param b
     */
    private void registerUserOnlineStatus(boolean b) {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode> () {
                    public void onEvent(StatusCode status) {
                        if (status==StatusCode.KICKOUT) {
                            // 被踢出
                            Toaster.toastShort("用户在其他设备上登录！！！！");
                            finish();
                            startActivity(new Intent(MainChartrActivity.this,LoginActivity.class));
                        }
                    }
                }, b);
    }

    private void enableMsgNotification(boolean enable) {
        if (enable) {
            /**
             * 设置最近联系人的消息为已读
             *
             * @param account,    聊天对象帐号，或者以下两个值：
             *                    {@link #MSG_CHATTING_ACCOUNT_ALL} 目前没有与任何人对话，但能看到消息提醒（比如在消息列表界面），不需要在状态栏做消息通知
             *                    {@link #MSG_CHATTING_ACCOUNT_NONE} 目前没有与任何人对话，需要状态栏消息通知
             */
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
        }
    }
    // 暂存消息，当RecentContact 监听回来时使用，结束后清掉
    private Map<String, Set<IMMessage>> cacheMessages = new HashMap<>();

    //监听在线消息中是否有@我
    private Observer<List<IMMessage>> messageReceiverObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> imMessages) {
            if (imMessages != null) {
                boolean isTeam=false;
                for (IMMessage imMessage : imMessages) {
                    if (imMessage.getSessionType()==SessionTypeEnum.Team)
                        isTeam=true;
                    else {
                        mainChartRedIv.setVisibility(View.VISIBLE);
                    }
                    if (isTeam){
                        //查询最近联系人已获取未读数
                        NIMClient.getService(MsgService.class).queryRecentContacts()
                                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                                    @Override
                                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                                        // recents参数即为最近联系人列表（最近会话列表）
                                        adapter.setRecentContacts(recents);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                    }
                }
            }
        }
    };
    @Override
    public void onUnreadNumChanged(ReminderItem item) {

    }
    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    private boolean quit = false; //设置退出标识

    @Override
    public void onBackPressed() {
        if (!quit) { //询问退出程序
            Toaster.toastShort("再按一次退出程序");
            new Timer(true).schedule(new TimerTask() { //启动定时任务
                @Override
                public void run() {
                    quit = false; //重置退出标识
                }
            }, 2000); //2秒后运行run()方法
            quit = true;
        } else { //确认退出程序
            super.onBackPressed();
            finishApp();
        }
    }

    public void finishApp(){
        while (!AppContext.getActivitiesStack().isEmpty()){
            AppContext.getActivitiesStack().pop().finish();
        }
    }
    @Override
    public void showErrorMessage(String err) {
        mainLoadRl.setVisibility(View.GONE);
        Toaster.toastShort(err);
    }

    @Override
    public void groupRefresh(List<GroupMessageBean.ListBean> data) {
        mainLoadRl.setVisibility(View.GONE);
        if (mListData==null)
            mListData = new ArrayList<>();
        mListData.clear();
        adapter.getData().clear();
        GroupMessageBean.ListBean bean = new GroupMessageBean.ListBean();
        bean.setGroupName(AppContext.getString(R.string.main_notice));
        mListData.add(bean);
        GroupMessageBean.ListBean bean1 = new GroupMessageBean.ListBean();
        bean1.setGroupName(AppContext.getString(R.string.main_online_answer));
        mListData.add(bean1);
        if (data!=null){
            for (GroupMessageBean.ListBean listBean:data){
                String hallDisplay=listBean.getHallDisplay();
                if (!TextUtils.isEmpty(hallDisplay)){
                    if (listBean.getHallDisplay().equals("1"))//正式的要加上这个过滤
                        mListData.add(listBean);
                    else {
                        if (listBean.getUserExists().equals("1")){//在群里
                            mListData.add(listBean);
                        }
                    }
                }
            }
        }
//        mListData.addAll(data);//正式要去掉这个
        //查询最近联系人已获取未读数
        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {

                        if (data!=null){
                            int flag=0;
                            for (GroupMessageBean.ListBean listBean:data){
                                String joinmode=listBean.getHallDisplay();
                                if (!TextUtils.isEmpty(joinmode)){
                                    if (Integer.parseInt(joinmode)==1){//大厅显示的群

                                    }else {//非大厅显示的群
                                        if (listBean.getUserExists().equals("1")){//在群里

                                        }
                                    }
                                    if (flag==1)
                                        break;
                                    if (recents!=null){
                                        for (RecentContact recentContact:recents){
                                            if (recentContact.getSessionType()== SessionTypeEnum.Team){
                                                if (recentContact.getContactId().equals(listBean.getGroupId())){
                                                    int unReadCount=recentContact.getUnreadCount();
                                                    if (unReadCount>0){
                                                        flag=1;
                                                        mainExplainRedIv.setVisibility(View.VISIBLE);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                            if (flag==0)
                                mainExplainRedIv.setVisibility(View.GONE);
                        }
                        // recents参数即为最近联系人列表（最近会话列表）
                        adapter.setRecentContacts(recents);
                        adapter.addData(mListData);

                        for (RecentContact recentContact:recents){
                            if (recentContact.getSessionType()== SessionTypeEnum.P2P){
                                int unReadCount=recentContact.getUnreadCount();

                                int unread = NIMClient.getService(SystemMessageService.class) .querySystemMessageUnreadCountBlock();
                                unReadCount+=unread;
                                if (unReadCount>0){
                                    mainChartRedIv.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                            mainChartRedIv.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void groupApply(String msg) {
        Toaster.toastShort("申请成功，等待群主审批");
    }


    @Override
    public void aggreeView(SystemMessage systemMessage) {

    }

    @Override
    public void aggreeView(String code,String groupId) {
        if (!TextUtils.isEmpty(code)){
            if (code.equals("0000") ){//添加成功或者
                APIYUNXIN.applyJoinTeam(MainChartrActivity.this,groupId, "", new YCallBack<Team>() {
                    @Override
                    public void callBack(Team team) {
                        // 打开群聊界面
                        NimUIKit.startTeamSession(MainChartrActivity.this, groupId);
                    }
                });
            }else if (code.equals("2045")){//以在群里了
                // 打开群聊界面
                NimUIKit.startTeamSession(this, groupId);
            }
            else {
                Toaster.toastShort("异常操作!!");
            }
        }
    }

}
