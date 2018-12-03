package com.android.similarwx.widget.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.BaseBean;
import com.android.similarwx.beans.CanGrabBean;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.inteface.MiViewInterface;
import com.android.similarwx.inteface.RedDetailViewInterface;
import com.android.similarwx.inteface.message.RedCustomAttachment;
import com.android.similarwx.misdk.ScreenUtil;
import com.android.similarwx.present.MIPresent;
import com.android.similarwx.present.RedDetailPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.Util;
import com.android.similarwx.utils.audio.MediaManager;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.nim.uikit.business.session.module.ModuleProxy;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/9.
 */

public class RedResultNewDialogFragment extends DialogFragment implements View.OnClickListener, MiViewInterface{
    private static ModuleProxy proxy;

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
    public static RedResultNewDialogFragment newInstance(SendRed.SendRedBean sendRed,String sessionId,IMMessage imMessage) {
        RedResultNewDialogFragment redDialogFragment = new RedResultNewDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", sendRed);
        bundle.putSerializable("sessionId", sessionId);
        bundle.putSerializable("message", imMessage);
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
    private TextView dialog_red_result_money_tv;

    SendRed.SendRedBean mSendRedBean;
    private MIPresent miPresent;
    IMMessage message;
    private String sessionId;
    int flag=0;//0是未抢完   1是抢完
    int canFlag=0;//0能抢包
    private int sound=0;
    AssetFileDescriptor afd;
    String myAccid;
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
        dialog_red_result_money_tv=view.findViewById(R.id.dialog_red_result_money_tv);
        myAccid = SharePreferenceUtil.getString(getActivity(),AppConstants.USER_ACCID,"");
        sound=SharePreferenceUtil.getInt(getActivity(),AppConstants.USER_SOUND_SET);
        Bundle bundle=getArguments();
        if (bundle!=null){
            mSendRedBean= (SendRed.SendRedBean) bundle.getSerializable("info");
            message= (IMMessage) bundle.getSerializable("message");
            sessionId= bundle.getString("sessionId");
            miPresent=new MIPresent(this, (AppCompatActivity) getActivity());
            if (mSendRedBean!=null){
                miPresent.canGrab(mSendRedBean.getRedPacId());//请求是否能抢红包
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
                                        int width= ScreenUtil.dip2px(45);
                                        int heigth=ScreenUtil.dip2px(45);
                                        NetImageUtil.glideImageCorner(getActivity(),imageUrl,dialog_red_result_head_iv,width,heigth);
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
                if (myAccid.equals(mSendRedBean.getMyUserId())){
                    dialog_red_result_bottom_tv.setText("查看大家的手气");
                }else {
                    dialog_red_result_bottom_tv.setText("查看领取详情");
                }

                String type=mSendRedBean.getType();
                if (TextUtils.isEmpty(type)){
                    dialog_red_result_tips_tv.setText(mSendRedBean.getCotent());
                }else {
                    if (type.equals("LUCK")){
                        dialog_red_result_tips_tv.setText(mSendRedBean.getCotent());
                    }
                }
            }
        }

        AssetManager am = getActivity().getAssets();
        try {
            afd = am.openFd("open_red.mp3");
        } catch (IOException e) {
            e.printStackTrace();
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
    public static RedResultNewDialogFragment show(Activity activity, SendRed.SendRedBean sendRed,String sessionId,IMMessage imMessage,ModuleProxy proxyN){
        proxy=proxyN;
        RedResultNewDialogFragment redResultDialogFragment= RedResultNewDialogFragment.newInstance(sendRed,sessionId,imMessage);
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
                if (myAccid.equals(mSendRedBean.getMyUserId())){
                    Bundle bundle=new Bundle();
                    if (mSendRedBean!=null){
                        bundle.putString(RedDetailFragment.GROUPID,mSendRedBean.getGroupId());
                        bundle.putString(RedDetailFragment.REDID,mSendRedBean.getRedPacId());
                        bundle.putSerializable(RedDetailFragment.SENDRED,mSendRedBean);
                    }
                    FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),bundle);
                    disMiss(getActivity());
                }else {
                    if (canFlag==1){
                        Bundle bundle=new Bundle();
                        if (mSendRedBean!=null){
                            bundle.putString(RedDetailFragment.GROUPID,mSendRedBean.getGroupId());
                            bundle.putString(RedDetailFragment.REDID,mSendRedBean.getRedPacId());
                            bundle.putSerializable(RedDetailFragment.SENDRED,mSendRedBean);
                        }
                        FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),bundle);
                        disMiss(getActivity());
                    }else if (canFlag==0){
                        Toaster.toastShort("您未抢包，不能查看详情");
                    } else {
                        Toaster.toastShort(dialog_red_result_tips_tv.getText().toString());
                    }
                }
                break;
            case R.id.dialog_red_result_kai_tv://开红包

                dialog_red_result_kai_tv.setImageResource(R.drawable.red_loading);
                AnimationDrawable animationDrawable = (AnimationDrawable) dialog_red_result_kai_tv.getDrawable();
                animationDrawable.start();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (animationDrawable.isRunning())
                            animationDrawable.stop();
                        miPresent.grabRed(mSendRedBean.getRedPacId(), (AppCompatActivity) getActivity());
                    }
                },1000);
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
    Handler handler=new Handler(Looper.getMainLooper());
    @Override
    public void grabRed(RspGrabRed bean) {
        if (bean!=null) {
            String result = bean.getResult();
            if (result.equals("success")) {
                RspGrabRed.GrabRedBean bena=bean.getData();
                if (bena!=null){
                    String code=bena.getRetCode();
                    if (code.equals("0000")){
                        if (message!=null){
                            RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
                            mSendRedBean.setClick("0000");
                            attachment.setSendRedBean(mSendRedBean);
                            message.setAttachment(attachment);
                            NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
//                            Map localExtention=new HashMap();
//                            localExtention.put("redStatus","0000");
//                            message.setLocalExtension(localExtention);
//                            NIMClient.getService(MsgService.class).updateIMMessage(message);
                        }
                        if (sound==1){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    MediaManager.playSendMessageSound(getActivity(), afd, new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            MediaManager.release();
                                        }
                                    });
                                }
                            });
                            doToRedDetail(bena);
                        }else
                            doToRedDetail(bena);
                    }else if (code.equals("8889")){
                        setErrorText("手慢了，红包派光了");
                        dialog_red_result_money_tv.setVisibility(View.GONE);
                        if (message!=null){
                            RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
                            mSendRedBean.setClick("8889");
                            attachment.setSendRedBean(mSendRedBean);
                            message.setAttachment(attachment);
                            NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
                        }
                    }else {
                        setErrorText(bena.getRetMsg());
                        dialog_red_result_money_tv.setVisibility(View.GONE);
                        if (message!=null){
                            RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
                            mSendRedBean.setClick(code);
                            attachment.setSendRedBean(mSendRedBean);
                            message.setAttachment(attachment);
                            NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
                        }
                    }
                }
            }else {
                Toaster.toastShort(bean.getErrorMsg());
            }
        }
    }

    private void doToRedDetail(RspGrabRed.GrabRedBean bena) {
        Bundle bundle=new Bundle();
        if (mSendRedBean!=null){
            //抢包成功、发送一个提示消息
            String isComplete=bena.getComplete();

            if (TextUtils.isEmpty(isComplete)){
                flag=0;
            }else {
                if (isComplete.equals("true"))
                    flag=1;
                else
                    flag=0;
            }
            doYunXinTip(sessionId,flag);
            bundle.putString(RedDetailFragment.GROUPID,mSendRedBean.getGroupId());
            bundle.putString(RedDetailFragment.REDID,mSendRedBean.getRedPacId());
            bundle.putSerializable(RedDetailFragment.SENDRED,mSendRedBean);
            bundle.putSerializable(RedDetailFragment.GRAB,bena);
            FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),bundle);
            disMiss(getActivity());
        }
    }

    private void doYunXinTip(String sessionId,int finishFlag) {
        Map<String, Object> content = new HashMap<>(1);
        String accid= SharePreferenceUtil.getString(getActivity(), AppConstants.USER_ACCID,"");
//        content.put("accId", mSendRedBean.getMyUserId());
        content.put("accId", accid);
        content.put("finishFlag",finishFlag);
        Gson gson=new Gson();
        content.put("sendRedBean", gson.toJson(mSendRedBean));
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

        if (proxy!=null){
            proxy.sendMessage(msg);
        }
    }

    @Override
    public void canGrab(CanGrabBean bean) {
        if (bean!=null){
            String code = bean.getRetCode();
            if (code.equals("0000")) {
                canFlag=0;
                dialog_red_result_kai_tv.setVisibility(View.VISIBLE);
                dialog_red_result_bottom_tv.setVisibility(View.VISIBLE);
                dialog_red_result_money_tv.setVisibility(View.VISIBLE);
                dialog_red_result_tips_tv.setText("发了一个红包，金额随机");
                if (mSendRedBean!=null){
                    String text=null;
                    if (TextUtils.isEmpty(mSendRedBean.getThunder())){
                        text=mSendRedBean.getCount();
                        dialog_red_result_money_tv.setText(mSendRedBean.getCotent());
                    }else {
                        text=mSendRedBean.getThunder();
                        if (Util.isIntegerForDouble(Double.parseDouble(mSendRedBean.getAmount()))){
                            dialog_red_result_money_tv.setText(mSendRedBean.getAmount()+"-"+text);
                        }else {
                            dialog_red_result_money_tv.setText(String.format("%.2f", Double.parseDouble(mSendRedBean.getAmount()))+"-"+text);
                        }
                    }
                }
            } else if (code.equals("8889")){//红包已拆分完毕。
                dialog_red_result_kai_tv.setVisibility(View.GONE);
                dialog_red_result_bottom_tv.setVisibility(View.VISIBLE);
                dialog_red_result_money_tv.setVisibility(View.GONE);
                canFlag=1;
                setErrorText("手慢了，红包派光了");
                if (message!=null){
                    RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
                    mSendRedBean.setClick("8889");
                    attachment.setSendRedBean(mSendRedBean);
                    message.setAttachment(attachment);
                    NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
                }
            } else if (code.equals("9000")){//红包已过期退回。
                dialog_red_result_kai_tv.setVisibility(View.GONE);
                dialog_red_result_bottom_tv.setVisibility(View.VISIBLE);
                dialog_red_result_money_tv.setVisibility(View.GONE);
                canFlag=1;
                setErrorText(bean.getRetMsg());
                if (message!=null){
                    RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
                    mSendRedBean.setClick("9000");
                    attachment.setSendRedBean(mSendRedBean);
                    message.setAttachment(attachment);
                    NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
                }
            } else if(code.equals("0010")){
                dialog_red_result_kai_tv.setVisibility(View.GONE);
                dialog_red_result_bottom_tv.setVisibility(View.VISIBLE);
                dialog_red_result_money_tv.setVisibility(View.GONE);
                canFlag=2;
                setErrorText("您的积分不足请充值");
                if (message!=null){
                    RedCustomAttachment attachment = (RedCustomAttachment) message.getAttachment();
                    mSendRedBean.setClick("0010");
                    attachment.setSendRedBean(mSendRedBean);
                    message.setAttachment(attachment);
                    NIMClient.getService(MsgService.class).updateIMMessageStatus(message);
                }
            } else {
                dialog_red_result_kai_tv.setVisibility(View.GONE);
                dialog_red_result_bottom_tv.setVisibility(View.VISIBLE);
                dialog_red_result_money_tv.setVisibility(View.GONE);
                canFlag=2;
                setErrorText(bean.getRetMsg());
            }
        }
    }
}
