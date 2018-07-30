package com.android.similarwx.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.present.SysNoticePresent;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MoneyFragment extends BaseFragment implements SysNoticeViewInterface {
    @BindView(R.id.my_money_friend_rl)
    RelativeLayout myMoneyFriendRl;
    @BindView(R.id.my_money_save_phone_rl)
    RelativeLayout myMoneySavePhoneRl;
    @BindView(R.id.my_money_share_circle_rl)
    RelativeLayout myMoneyShareCircleRl;
    @BindView(R.id.my_money_iv)
    ImageView myMoneyIv;
    Unbinder unbinder;

//    private BaseQuickAdapter adapter;
    private SysNoticePresent present;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_money;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.money_title);
        unbinder = ButterKnife.bind(this, contentView);
        present=new SysNoticePresent(this);
        init();
    }
    private void init() {
//        myMoneyRv.setLayoutManager(new LinearLayoutManager(activity));
//        adapter=new BaseQuickAdapter<Notice,BaseViewHolder>(R.layout.item_notice,null){
//
//            @Override
//            protected void convert(BaseViewHolder helper, Notice item) {
//                String content=item.getContent();
//                helper.setText(R.id.notice_item_title,item.getTitle());
//                helper.setText(R.id.notice_item_time,item.getModifyDate());
//                helper.setText(R.id.notice_item_content,item.getRemark());
//                if (!TextUtils.isEmpty(content)){
//                    String text= Html.fromHtml(content).toString();
//                    helper.setText(R.id.notice_item_content_detail,text);
//                }
//            }
//        };
//        myMoneyRv.setAdapter(adapter);
        present.getMoney();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_money_friend_rl, R.id.my_money_save_phone_rl, R.id.my_money_share_circle_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_money_friend_rl://分享好友
                break;
            case R.id.my_money_save_phone_rl://保存手机
                break;
            case R.id.my_money_share_circle_rl://分享朋友圈
                break;
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void refreshSysNotice(List<Notice> list) {
    }

    @Override
    public void refreshSysMoney(String url) {
        NetImageUtil.glideImageNormal(activity,url,myMoneyIv);
    }
}
