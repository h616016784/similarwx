package com.android.similarwx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.utils.glide.CircleCrop;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanhuailong on 2018/4/10.
 */

public class RedDetailAdapter extends BaseQuickAdapter<RedDetialBean,BaseViewHolder> {
    private Context mContext;
    public RedDetailAdapter(int layoutResId,Context context) {
        super(layoutResId);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RedDetialBean item) {

        helper.setText(R.id.item_red_detail_name_tv,item.getName());
//        helper.setText(R.id.item_red_detail_time_tv,item.get);
        helper.setText(R.id.item_red_detail_money_tv,item.getAmount()+"");
//        helper.setText(R.id.item_red_detail_shouqi_tv,item.getShouqi());
        String imageUrl=item.getIcon();
        if (!TextUtils.isEmpty(imageUrl)){
            Glide.with(mContext).load(imageUrl).override(60,60).transform(new CircleCrop(mContext))
                    .placeholder(R.drawable.rp_avatar)
                    .error(R.drawable.rp_avatar)
                    .into((ImageView) helper.getView(R.id.item_red_detail_iv));
        }
        boolean isLucky=item.isLucky();
        if (isLucky){
            helper.setGone(R.id.item_red_detail_shouqi_tv,true);
        }else{
            helper.setGone(R.id.item_red_detail_shouqi_tv,false);
        }
    }
}
