package com.android.similarwx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.RedDetialBean;
import com.android.similarwx.misdk.ScreenUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.utils.glide.NetImageUtil;
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
    private String type="LUCK";
    public RedDetailAdapter(int layoutResId,Context context,String type) {
        super(layoutResId);
        this.mContext=context;
        this.type=type;
    }
    public void setType(){

    }
    @Override
    protected void convert(BaseViewHolder helper, RedDetialBean item) {
        int pos=helper.getLayoutPosition();
        if (pos==this.getData().size()-1){
            if (TextUtils.isEmpty(type)){//默认是luck
                helper.setText(R.id.item_red_detail_money_tv,String.format("%.2f", item.getAmount())+" 元");
            }else {
                if (type.equals("LUCK")){
                    helper.setText(R.id.item_red_detail_money_tv,String.format("%.2f", item.getAmount())+" 元");
                }else {
                    String am=String.format("%.2f", item.getAmount());
                    String amNew=am.substring(0,am.length()-1);
                    helper.setText(R.id.item_red_detail_money_tv,amNew+"*"+" 元");
                }
            }
        }else {
            helper.setText(R.id.item_red_detail_money_tv,String.format("%.2f", item.getAmount())+" 元");
        }
        helper.setText(R.id.item_red_detail_name_tv,item.getName());
        helper.setText(R.id.item_red_detail_time_tv,item.getCreateDate());
//        helper.setText(R.id.item_red_detail_shouqi_tv,item.getShouqi());
        String imageUrl=item.getIcon();
        if (!TextUtils.isEmpty(imageUrl)){
            int width= ScreenUtil.dip2px(62);
            int heigth=ScreenUtil.dip2px(62);
            NetImageUtil.glideImageCorner(mContext,imageUrl,(ImageView) helper.getView(R.id.item_red_detail_iv), width,heigth);
        }
        boolean isLucky=item.isBestHand();
        if (isLucky){
            helper.setGone(R.id.item_red_detail_shouqi_rl,true);
        }else{
            helper.setGone(R.id.item_red_detail_shouqi_rl,false);
        }
    }
}
