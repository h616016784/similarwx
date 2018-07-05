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

import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.BaseBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.glide.CircleCrop;
import com.bumptech.glide.Glide;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

/**
 * Created by Administrator on 2018/4/9.
 */

public class RedResultDialogFragment extends DialogFragment implements View.OnClickListener {
    public static RedResultDialogFragment newInstance(BaseBean bean,SendRed sendRed) {
        RedResultDialogFragment redDialogFragment = new RedResultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("isGood", bean);
        bundle.putSerializable("info", sendRed);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    public static RedResultDialogFragment newInstance(String title, String message) {
        RedResultDialogFragment redDialogFragment = new RedResultDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    private ImageView dialog_red_result_cancel_iv;
    private ImageView dialog_red_result_kai_tv;
    private TextView dialog_red_result_bottom_tv;
    private TextView dialog_red_result_name_tv;
    private TextView dialog_red_result_tips_tv;

    private static OnOpenClick mClicker;
    static SendRed mSendRed;
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
        dialog_red_result_bottom_tv=view.findViewById(R.id.dialog_red_result_bottom_tv);
        dialog_red_result_name_tv=view.findViewById(R.id.dialog_red_result_name_tv);
        dialog_red_result_tips_tv=view.findViewById(R.id.dialog_red_result_tips_tv);
        dialog_red_result_kai_tv=view.findViewById(R.id.dialog_red_result_kai_tv);

        Bundle bundle=getArguments();
        if (bundle!=null){
            BaseBean bean= (BaseBean) bundle.getSerializable("isGood");
            SendRed sendRed= (SendRed) bundle.getSerializable("info");
            if (sendRed!=null){
                String accid=sendRed.getData().getMyUserId();//云信的accid
                NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(accid);
                dialog_red_result_tips_tv.setText(user.getName());
                String url=user.getAvatar();
                if (!TextUtils.isEmpty(url)){
                    Glide.with(getActivity()).load(url).override(60,60).transform(new CircleCrop(getActivity()))
                            .placeholder(R.drawable.rp_avatar)
                            .error(R.drawable.rp_avatar)
                            .into(dialog_red_result_cancel_iv);
                }
            }
            if (bean!=null){
                String code = bean.getRetCode();
                if (code.equals("0000")) {
                    dialog_red_result_kai_tv.setVisibility(View.VISIBLE);
                    if (sendRed!=null){
                        String text=null;
                        if (TextUtils.isEmpty(sendRed.getData().getThunder())){
                            text=sendRed.getData().getCount();
                        }else {
                            text=sendRed.getData().getThunder();
                        }
                        dialog_red_result_tips_tv.setText(sendRed.getData().getAmount()+"-"+text);
                    }
                } else {
                    dialog_red_result_kai_tv.setVisibility(View.GONE);
                    dialog_red_result_tips_tv.setText(bean.getRetMsg());
                }
            }

        }
    }
    private void addClickListener() {
        dialog_red_result_cancel_iv.setOnClickListener(this);
        dialog_red_result_bottom_tv.setOnClickListener(this);
        dialog_red_result_kai_tv.setOnClickListener(this);
    }

    public static RedResultDialogFragment show(Activity activity, BaseBean bean, SendRed sendRed, OnOpenClick onOpenClick){
        mClicker=onOpenClick;
        mSendRed=sendRed;
        RedResultDialogFragment redResultDialogFragment= RedResultDialogFragment.newInstance(bean,sendRed);
        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(redResultDialogFragment,"redResultDialog");
        transaction.addToBackStack(null);
        transaction.commit();
        return redResultDialogFragment;
    }
    public static void disMiss(Activity activity){
        FragmentManager transaction=activity.getFragmentManager();
        Fragment prev = transaction.findFragmentByTag("redResultDialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_red_result_cancel_iv:
                dismiss();
                break;
            case R.id.dialog_red_result_bottom_tv:
                Bundle bundle=new Bundle();
                if (mSendRed!=null){
                    bundle.putString(RedDetailFragment.GROUPID,mSendRed.getData().getGroupId());
                    bundle.putString(RedDetailFragment.REDID,mSendRed.getData().getRedPacId());
                }
                FragmentUtils.navigateToNormalActivity(getActivity(),new RedDetailFragment(),bundle);
                dismiss();
                break;
            case R.id.dialog_red_result_kai_tv://开红包
                if (mClicker!=null)
                    mClicker.onOpenClick();
//                dismiss();
                break;
        }
    }
    public void setErrorText(String text){
        dialog_red_result_tips_tv.setText(text);
        dialog_red_result_kai_tv.setVisibility(View.GONE);
    }
    public interface OnOpenClick{
        void onOpenClick();
    }
}
