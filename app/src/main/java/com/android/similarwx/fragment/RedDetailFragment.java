package com.android.similarwx.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.adapter.RedDetailAdapter;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;
import com.android.similarwx.inteface.RedDetailViewInterface;
import com.android.similarwx.present.RedDetailPresent;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.bumptech.glide.Glide;
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
    @BindView(R.id.red_detail_take_tv)
    TextView redDetailTakeTv;
    @BindView(R.id.red_detail_head_iv)
    ImageView redDetailHeadIv;
    @BindView(R.id.red_detail_rv)
    RecyclerView redDetailRv;
    Unbinder unbinder;

    private RedDetailAdapter redDetailAdapter;
    private RedDetailPresent mPresent;
    SendRed.SendRedBean sendRed;
    RspGrabRed.GrabRedBean grabRedBean;
    String redId;
    String groupId;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_red_detail;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.red_detail_title);
        mActionbar.setWholeBackground(R.color.colorRed2);
        unbinder = ButterKnife.bind(this, contentView);
        mPresent=new RedDetailPresent(this);

        Bundle bundle=getArguments();
        if (bundle!=null){
            redId=bundle.getString(REDID);
            groupId=bundle.getString(GROUPID);
            sendRed= (SendRed.SendRedBean) bundle.getSerializable(SENDRED);
//            grabRedBean= (RspGrabRed.GrabRedBean) bundle.getSerializable(GRAB);
            if (sendRed!=null){
                String textContent=sendRed.getThunder();
                if (TextUtils.isEmpty(sendRed.getThunder())){
                    textContent=sendRed.getCount();
                }
                redDetailCount.setText(sendRed.getAmount()+"-"+textContent);
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
                                        NetImageUtil.glideImageCircle(getActivity(),imageUrl,redDetailHeadIv);
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
//            if (grabRedBean!=null){
//                redDetailAcountTv.setText(grabRedBean.getAmount()+"元");
//            }
        }

        redDetailRv.setLayoutManager(new LinearLayoutManager(activity));
        redDetailAdapter=new RedDetailAdapter(R.layout.item_red_detial,activity);
        redDetailRv.setAdapter(redDetailAdapter);
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
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshRedDetail(List<RedDetialBean> list) {
        String myAccid= SharePreferenceUtil.getString(activity, AppConstants.USER_ACCID,"");
        for (RedDetialBean bean:list){
            String accid=bean.getAccId();
            if (!TextUtils.isEmpty(accid)){
                if (myAccid.equals(accid))
                    redDetailAcountTv.setText(String.format("%.2f", bean.getAmount())+" 元");
            }
        }
        redDetailAdapter.addData(list);
        redDetailTakeTv.setText(list.size()+"/"+sendRed.getCount());
    }
}
