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
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.adapter.HomeAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
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
import com.android.similarwx.fragment.ServiceFragment;
import com.android.similarwx.inteface.MainGroupView;
import com.android.similarwx.inteface.YCallBack;
import com.android.similarwx.model.APIYUNXIN;
import com.android.similarwx.present.GroupPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.notification.NotificationUtil;
import com.android.similarwx.widget.ListPopWindow;
import com.android.similarwx.widget.ListPopWindowHelper;
import com.android.similarwx.widget.dialog.EasyAlertDialog;
import com.android.similarwx.widget.dialog.EasyAlertDialogHelper;
import com.android.similarwx.widget.dialog.EditDialogSimple;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SystemMessageType;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.model.Team;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/1.
 */

public class MainChartrActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, MainGroupView {

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
    @BindView(R.id.main_my_chart)
    RelativeLayout mainMyChart;

    private HomeAdapter adapter;
    private List<GroupMessageBean.ListBean> mListData;
    GroupPresent groupPresent;
    private EditDialogSimple editDialogSimple;
    private ListPopWindowHelper listPopWindowHelper=null;
    private List<PopMoreBean> listMore=null;

    private User mUser;
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
        initYunXinSystemMsgListener();
        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(this,AppConstants.USER_OBJECT);
        if (mUser!=null){
            String userType=mUser.getUserType();
            if (!TextUtils.isEmpty(userType)){
                if (userType.equals("1")){
                    createGroupIv.setVisibility(View.VISIBLE);
                }
            }
        }
        editDialogSimple = new EditDialogSimple(this, null);
        initLoacalData();

        adapter = new HomeAdapter(R.layout.item_group, this, mListData);
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
                            String account=message.getFromAccount();
                            String target=message.getTargetId();
                            NotificationUtil.NotificationConfig config=new NotificationUtil.NotificationConfig();
                            config.setContentTitle("群组通知");
                            switch (message.getType().getValue()){
                                case 0:
                                    config.setContentText(account+"入群申请");
                                    break;
                                case 1:
                                    config.setContentText(account+"拒绝入群");
                                    break;
                                case 2:
                                    config.setContentText(account+"邀请入群");
                                    break;
                                case 3:
                                    config.setContentText(account+"拒绝邀请");
                                    break;
                                case 5:
                                    config.setContentText(account+"添加好友");
                                    break;
                                default:
                                    config.setContentText("未知");
                            }
                            config.setTicker("通知消息");
                            NotificationUtil.getInstance(MainChartrActivity.this,config).notification();
                        }
                    }
                }, true);
    }

    private void initLoacalData() {
        listMore=new ArrayList<>();
        PopMoreBean bean=new PopMoreBean();
        bean.setName("创建群组");
        bean.setImage(R.drawable.tooltip_icon_f);
        listMore.add(bean);

        PopMoreBean bean1=new PopMoreBean();
        bean1.setName("查找用户");
        bean1.setImage(R.drawable.tooltip_icon_f);
        listMore.add(bean1);

        PopMoreBean bean2=new PopMoreBean();
        bean2.setName("查找群组");
        bean2.setImage(R.drawable.tooltip_icon_f);
        listMore.add(bean2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        groupPresent.getGroupList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        listPopWindowHelper.destroy();
    }

    @OnClick({R.id.main_search_iv, R.id.main_rl_chart, R.id.main_my_chart,R.id.create_group_iv,R.id.main_rl_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_search_iv:
                break;
            case R.id.create_group_iv:
                listPopWindowHelper=new ListPopWindowHelper(MainChartrActivity.this,listMore);
                listPopWindowHelper.show(createGroupIv);
                listPopWindowHelper.setOnClickItem(new ListPopWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (position==0){
                            FragmentUtils.navigateToNormalActivity(MainChartrActivity.this, new AddGroupFragment(), null);
                        }
                    }
                });
                break;
            case R.id.main_rl_find://发现

//                FragmentUtils.navigateToNormalActivity(this, new ExplainFragment(), null);//此功能去掉
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
                String joinmode=bean.getJoinmode();
                if (!TextUtils.isEmpty(joinmode)){
                    if (joinmode.equals("0")){//允许任何人加入
                        doInGroup(bean);
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
                                String detail=gson.toJson(mUser);
                                APIYUNXIN.applyJoinTeam(bean.getGroupId(), detail, new YCallBack<Team>() {
                                    @Override
                                    public void callBack(Team team) {
                                        Toaster.toastShort("申请成功，等待群主审批");
                                    }
                                });
//                        doGroupApply(bean.getGroupId());
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
                            String detail=gson.toJson(mUser);
                            APIYUNXIN.applyJoinTeam(bean.getGroupId(), detail, new YCallBack<Team>() {
                                @Override
                                public void callBack(Team team) {
                                    Toaster.toastShort("申请成功，等待群主审批");
                                }
                            });
//                        doGroupApply(bean.getGroupId());
                        }
                    });
                    mDialog.show();
                }
            }else {//在群里  直接进入
                doInGroup(bean);
            }
        }
    }

    public void doInGroup(GroupMessageBean.ListBean bean){
//        Bundle bundle = new Bundle();
//        bundle.putInt(MIFragment.MIFLAG, MIFragment.DELETE_GROUP_EIGHT);
//        bundle.putSerializable(AppConstants.CHAT_TYPE, SessionTypeEnum.Team);
//        bundle.putString(AppConstants.CHAT_ACCOUNT_ID, bean.getGroupId());//群id号
//        bundle.putString(AppConstants.CHAT_ACCOUNT_NAME, bean.getGroupName());//群name
//        bundle.putSerializable(AppConstants.CHAT_GROUP_BEAN,bean);
//        FragmentUtils.navigateToNormalActivity(MainChartrActivity.this, new MIFragmentNew(), bundle);

        // 打开群聊界面
        NimUIKit.startTeamSession(this, bean.getGroupId());

//        NimUIKit.startP2PSession(this, bean.getGroupId());
    }

    /**
     * 申请加入群组,不用了。。。。
     */
    private void doGroupApply(String groupId) {
        groupPresent.doGroupAppley(groupId);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void groupRefresh(List<GroupMessageBean.ListBean> data) {
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
//        if (data!=null){
//            for (GroupMessageBean.ListBean listBean:data){
//                String hallDisplay=listBean.getHallDisplay();
//                if (!TextUtils.isEmpty(hallDisplay)){
//                    if (listBean.getHallDisplay().equals("1"))//正式的要加上这个过滤
//                        mListData.add(listBean);
//                }
//            }
//        }
        mListData.addAll(data);//正式要去掉这个
        adapter.addData(mListData);
    }

    @Override
    public void groupApply(String msg) {
        Toaster.toastShort("申请成功，等待群主审批");
    }


}
