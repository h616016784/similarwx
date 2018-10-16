package com.android.similarwx.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.adapter.RedDetailAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.beans.response.RspRedDetail;
import com.android.similarwx.inteface.RedDetailViewInterface;
import com.android.similarwx.misdk.ScreenUtil;
import com.android.similarwx.present.RedDetailPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/10.
 */

public class RedDetailFragment extends BaseFragment implements RedDetailViewInterface{
    public static String REDID="redId";
    public static String GROUPID="groupId";
    public static String SENDRED="sendRed";
    public static String GRAB="grab";
    @BindView(R.id.red_detail_name)
    TextView redDetailName;
    @BindView(R.id.red_detail_count)
    TextView redDetailCount;
    @BindView(R.id.red_detail_acount_tv)
    TextView redDetailAcountTv;
    @BindView(R.id.red_detail_acount_yuan_tv)
    TextView redDetailAcountYuanTv;
    @BindView(R.id.red_detail_take_tv)
    TextView redDetailTakeTv;
    @BindView(R.id.red_detail_take_time_tv)
    TextView redDetailTakeTimeTv;
    @BindView(R.id.red_detail_head_iv)
    ImageView redDetailHeadIv;
    @BindView(R.id.red_detail_head_rl)
    RelativeLayout redDetailHeadRl;
    @BindView(R.id.red_detail_name_iv)
    ImageView redDetailNameIv;
    @BindView(R.id.red_detail_rv)
    RecyclerView redDetailRv;
    Unbinder unbinder;

    private RedDetailAdapter redDetailAdapter;
    private RedDetailPresent mPresent;
    SendRed.SendRedBean sendRed;
    RspGrabRed.GrabRedBean grabRedBean;
    String redId;
    String groupId;
    public ImmersionBar mImmersionBar;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_red_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle("");
        mActionbar.setLeftText("红包详情");
        mActionbar.setLeftImage(R.drawable.left_red_new);
        mActionbar.setWholeBackground(R.color.colorRed3);
        mActionbar.setDividerBackground(R.color.colorRed3);
        mImmersionBar = ImmersionBar.with(activity);
        mImmersionBar.statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.colorRed3)
                .fitsSystemWindows(false).init();
        unbinder = ButterKnife.bind(this, contentView);
        mPresent=new RedDetailPresent(this,activity);
        Bundle bundle=getArguments();
        if (bundle!=null){
            redId=bundle.getString(REDID);
            groupId=bundle.getString(GROUPID);
            sendRed= (SendRed.SendRedBean) bundle.getSerializable(SENDRED);
//            grabRedBean= (RspGrabRed.GrabRedBean) bundle.getSerializable(GRAB);
            if (sendRed!=null){
                String accid=sendRed.getMyUserId();//云信的accid
                List accounts=new ArrayList();
                accounts.add(accid);
                NIMClient.getService(UserService.class).fetchUserInfo(accounts)
                        .setCallback(new RequestCallback<List<UserInfo>>() {
                            @Override
                            public void onSuccess(List<UserInfo> param) {
                                if (param!=null&& param.size()>0){
                                    UserInfo userInfo=param.get(0);
                                    String imageUrl=userInfo.getAvatar();
                                    redDetailName.setText(userInfo.getName());
                                    if (!TextUtils.isEmpty(imageUrl)){
                                        int width=ScreenUtil.dip2px(62);
                                        int heigth=ScreenUtil.dip2px(62);
                                        NetImageUtil.glideImageCorner(getActivity(),imageUrl,redDetailHeadIv, width,heigth);
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

        redDetailRv.setLayoutManager(new LinearLayoutManager(activity));
        redDetailAdapter=new RedDetailAdapter(R.layout.item_red_detial,activity,sendRed.getType());
        redDetailRv.setAdapter(redDetailAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void fetchData() {
        super.fetchData();
        mPresent.redDetailList(redId,groupId);
        initData();
    }

    @Override
    protected boolean isNeedFetch() {
        return true;

    }

    private void initData() {
//        redDetailAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @Override
    public void showErrorMessage(String err) {

    }
    boolean isHas=false;
    double amountTemp;
    @Override
    public void refreshRedDetail(RspRedDetail.RedListData redListData) {
        List<RedDetialBean> list=redListData.getRedPacDetailList();
        if (!TextUtils.isEmpty(redListData.getSpendSecond())){
            redDetailTakeTimeTv.setVisibility(View.VISIBLE);
            int time=Integer.parseInt(redListData.getSpendSecond());
            if (time<60){
                redDetailTakeTimeTv.setText(time+"秒内被抢光");
            }else if (time>=60 && time<60*60){//分钟
                int yu= time%60;
                if (yu==0)
                    redDetailTakeTimeTv.setText(time/60+"分钟被抢光");
                else{
                    if (yu<10){
                        redDetailTakeTimeTv.setText(time/60+"分钟0"+yu+"秒被抢光");
                    }else
                        redDetailTakeTimeTv.setText(time/60+"分钟"+yu+"秒被抢光");
                }
            }else {
                redDetailTakeTimeTv.setText("1小时以上被抢光！");
            }
//            else if (time>=60*60 && time<60*60*24){//小时
//                int yu= time%60*60;
//                if (yu==0)
//                    redDetailTakeTimeTv.setText(time/(60*60)+"小时内被抢光");
//                else
//                    redDetailTakeTimeTv.setText((time/(60*60)+1)+"小时内被抢光");
//            }
        }
        String textContent=sendRed.getThunder();
        if (TextUtils.isEmpty(sendRed.getThunder())){
            textContent=sendRed.getCount();
        }
        redDetailCount.setText(String.format("%.2f", Double.parseDouble(sendRed.getAmount()))+"-"+textContent);
        String type=sendRed.getType();
        if (!TextUtils.isEmpty(type)){
            if (type.equals("LUCK")){
                redDetailCount.setText(sendRed.getCotent());
                redDetailNameIv.setImageResource(R.drawable.red_pin);
            }else {
                redDetailNameIv.setImageResource(R.drawable.red_lei);
            }
        }

        String myAccid= SharePreferenceUtil.getString(activity, AppConstants.USER_ACCID,"");
        for (RedDetialBean bean:list){
            String accid=bean.getAccId();
            if (!TextUtils.isEmpty(accid)){
                if (myAccid.equals(accid)){
                    isHas=true;
                    amountTemp=bean.getAmount();
                    if (amountTemp==0){
                        redDetailAcountTv.setVisibility(View.GONE);
                        redDetailAcountYuanTv.setVisibility(View.GONE);
                        setHeadRlViewHigh();
                    }else
                        redDetailAcountTv.setText(String.format("%.2f", bean.getAmount()));
                }
            }
        }
        if (!isHas){
            redDetailAcountTv.setVisibility(View.GONE);
            redDetailAcountYuanTv.setVisibility(View.GONE);
            setHeadRlViewHigh();
        }

        redDetailAdapter.addData(list);
        redDetailTakeTv.setText(list.size()+"/"+sendRed.getCount());
    }
    private void setHeadRlViewHigh(){
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) redDetailHeadRl.getLayoutParams();
        redDetailHeadRl.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height=redDetailHeadRl.getMeasuredHeight();
        layoutParams.height=height-ScreenUtil.dip2px(80);
        redDetailHeadRl.setLayoutParams(layoutParams);
    }

}
