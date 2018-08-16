package com.android.similarwx.widget.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.beans.BaseBean;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.inteface.MiViewInterface;
import com.android.similarwx.inteface.RedDetailViewInterface;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.present.RedDetailPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/9.
 */

public class RedResultNewDialogFragment extends DialogFragment implements View.OnClickListener, MiViewInterface, RedDetailViewInterface {

    public static RedResultNewDialogFragment newInstance(SendRed.SendRedBean sendRed,IMMessage message) {
        RedResultNewDialogFragment redDialogFragment = new RedResultNewDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", sendRed);
        bundle.putSerializable("message", message);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }
    public static RedResultNewDialogFragment newInstance(SendRed.SendRedBean sendRed,String sessionId) {
        RedResultNewDialogFragment redDialogFragment = new RedResultNewDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", sendRed);
        bundle.putSerializable("sessionId", sessionId);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }
    public static RedResultNewDialogFragment newInstance(String title, String message) {
        RedResultNewDialogFragment redDialogFragment = new RedResultNewDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    private ImageView dialog_red_result_cancel_iv;
    private ImageView dialog_red_result_head_iv;
    private ImageView dialog_red_result_kai_tv;
    private TextView dialog_red_result_bottom_tv;
    private TextView dialog_red_result_name_tv;
    private TextView dialog_red_result_tips_tv;

    SendRed.SendRedBean mSendRedBean;
    private MIPresent miPresent;
    private RedDetailPresent mPresent;
//    IMMessage message;
    private String sessionId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_red_result,container);
        initView(view);
        addClickListener();
        return view;
    }

    private void initView(View view) {
        dialog_red_result_cancel_iv=view.findViewById(R.id.dialog_red_result_cancel_iv);
        dialog_red_result_head_iv=view.findViewById(R.id.dialog_red_result_head_iv);
        dialog_red_result_bottom_tv=view.findViewById(R.id.dialog_red_result_bottom_tv);
        dialog_red_result_name_tv=view.findViewById(R.id.dialog_red_result_name_tv);
        dialog_red_result_tips_tv=view.findViewById(R.id.dialog_red_result_tips_tv);
        dialog_red_result_kai_tv=view.findViewById(R.id.dialog_red_result_kai_tv);

        Bundle bundle=getArguments();
        if (bundle!=null){
            mSendRedBean= (SendRed.SendRedBean) bundle.getSerializable("info");
//            message= (IMMessage) bundle.getSerializable("message");
            sessionId= bundle.getString("sessionId");
            miPresent=new MIPresent(this);
            mPresent=new RedDetailPresent(this);
            if (mSendRedBean!=null){
                miPresent.canGrab(mSendRedBean.getRedPacId(),getActivity());//请求是否能抢红包
                String accid=mSendRedBean.getMyUserId();//云信的accid
                List accounts=new ArrayList();
                accounts.add(accid);
                NIMClient.getService(UserService.class).fetchUserInfo(accounts)
                        .setCallback(new RequestCallback<List<UserInfo>>() {
                            @Override
                            public void onSuccess(List<UserInfo> param) {
                                if (param!=null&& param.size()>0){
                                    UserInfo userInfo=param.get(0);
                                    String imageUrl=userInfo.getAvatar();
                                    dialog_red_result_name_tv.setText(userInfo.getName());
                                    if (!TextUtils.isEmpty(imageUrl)){
                                        NetImageUtil.glideImageNormal(getActivity(),imageUrl,dialog_red_result_head_iv);
                                    }
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


        }
    }
    private void addClickListener() {
        dialog_red_result_cancel_iv.setOnClickListener(this);
        dialog_red_result_bottom_tv.setOnClickListener(this);
        dialog_red_result_kai_tv.setOnClickListener(this);
    }

    public static RedResultNewDialogFragment show(Activity activity, SendRed.SendRedBean sendRed,IMMessage imMessage){
        RedResultNewDialogFragment redResultDialogFragment= RedResultNewDialogFragment.newInstance(sendRed,imMessage);
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(redResultDialogFragment,"redResultDialogBean");
        transaction.addToBackStack(null);
        transaction.commit();
        return redResultDialogFragment;
    }
    public static RedResultNewDialogFragment show(Activity activity, SendRed.SendRedBean sendRed,String sessionId){
        RedResultNewDialogFragment redResultDialogFragment= RedResultNewDialogFragment.newInstance(sendRed,sessionId);
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(redResultDialogFragment,"redResultDialogBean");
        transaction.addToBackStack(null);
        transaction.commit();
        return redResultDialogFragment;
    }
    public static void disMiss(Activity activity){
        FragmentManager transaction=activity.getFragmentManager();
        Fragment prev = transaction.findFragmentByTag("redResultDialogBean");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
            transaction.popBackStack();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_red_result_cancel_iv:
                disMiss(getActivity());
                break;
            case R.id.dialog_red_result_bottom_tv:
                Bundle bundle=new Bundle();
                if (mSendRedBean!=null){
                    bundle.putString(RedDetailFragment.GROUPID,mSendRedBean.getGroupId());
                    bundle.putString(RedDetailFragment.REDID,mSendRedBean.getRedPacId());
                    bundle.putSerializable(RedDetailFragment.SENDRED,mSendRedBean);
                }
                FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),bundle);
                disMiss(getActivity());
                break;
            case R.id.dialog_red_result_kai_tv://开红包
                miPresent.grabRed(mSendRedBean.getRedPacId(),getActivity());
                break;
        }
    }
    private void setErrorText(String text){
        dialog_red_result_tips_tv.setText(text);
        dialog_red_result_kai_tv.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void reFreshCustemRed(SendRed.SendRedBean data) {

    }

    @Override
    public void grabRed(RspGrabRed bean) {
        if (bean!=null) {
            String result = bean.getResult();
            if (result.equals("success")) {
                RspGrabRed.GrabRedBean bena=bean.getData();
                if (bena!=null){
                    String code=bena.getRetCode();
                    if (code.equals("0000")){
                        Bundle bundle=new Bundle();
                        if (mSendRedBean!=null){

                            mPresent.redDetailList(mSendRedBean.getRedPacId(),mSendRedBean.getGroupId());

                            bundle.putString(RedDetailFragment.GROUPID,mSendRedBean.getGroupId());
                            bundle.putString(RedDetailFragment.REDID,mSendRedBean.getRedPacId());
                            bundle.putSerializable(RedDetailFragment.SENDRED,mSendRedBean);
                            bundle.putSerializable(RedDetailFragment.GRAB,bena);
                            FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),bundle);
                            disMiss(getActivity());
                        }
                    }else {
                        setErrorText(bena.getRetMsg());
                    }
                }
            }else {
                Toaster.toastShort(bean.getErrorMsg());
            }
        }
    }

    private void doYunXinTip(String sessionId,int finishFlag) {
        Map<String, Object> content = new HashMap<>(1);
        content.put("accId", mSendRedBean.getMyUserId());
        content.put("finishFlag",finishFlag);
        Gson gson=new Gson();
        content.put("sendRedBean", gson.toJson(mSendRedBean));
// 创建tip消息，teamId需要开发者已经存在的team的teamId
        IMMessage msg = MessageBuilder.createTipMessage(sessionId, SessionTypeEnum.Team);
        msg.setRemoteExtension(content);
// 自定义消息配置选项
        CustomMessageConfig config = new CustomMessageConfig();
// 消息不计入未读
        config.enableUnreadCount = false;
        msg.setConfig(config);
// 消息发送状态设置为success
        msg.setStatus(MsgStatusEnum.success);

        NIMClient.getService(MsgService.class).sendMessage(msg, true).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // 保存消息到本地数据库，但不发送到服务器
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    @Override
    public void canGrab(BaseBean bean) {
        if (bean!=null){
            String code = bean.getRetCode();
            if (code.equals("0000")) {
                dialog_red_result_kai_tv.setVisibility(View.VISIBLE);
                if (mSendRedBean!=null){
                    String text=null;
                    if (TextUtils.isEmpty(mSendRedBean.getThunder())){
                        text=mSendRedBean.getCount();

                    }else {
                        text=mSendRedBean.getThunder();
                    }
                    dialog_red_result_tips_tv.setText(String.format("%.2s", mSendRedBean.getAmount())+"-"+text);
                }
            } else {
                setErrorText(bean.getRetMsg());
            }
        }
    }

    @Override
    public void refreshRedDetail(List<RedDetialBean> list) {
        int flag=0;
        if (list!=null && mSendRedBean!=null){
            String count=mSendRedBean.getCount();
            if (!TextUtils.isEmpty(count)){
                if (list.size()==Integer.parseInt(count)){
                    flag=1;
                }else {
                    flag=0;
                }
            }
        }
        //抢包成功、发送一个提示消息
        doYunXinTip(sessionId,flag);
    }
}
