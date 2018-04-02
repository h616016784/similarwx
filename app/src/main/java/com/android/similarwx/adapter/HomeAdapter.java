package com.android.similarwx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.GroupMessageBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseQuickAdapter;
import com.netease.nim.uikit.common.ui.recyclerview.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class HomeAdapter extends BaseQuickAdapter<GroupMessageBean,BaseViewHolder> {
    private Context context;
    RequestOptions options;
    public HomeAdapter(Context context,RecyclerView recyclerView, int layoutResId, List<GroupMessageBean> data) {
        super(recyclerView, layoutResId, data);
        this.context=context;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupMessageBean item, int position, boolean isScrolling) {
        helper.setText(R.id.item_group_tv,item.getName());
        helper.setText(R.id.item_group_count_tv,item.getMsgCount());
        if(position==0){
            helper.setImageResource(R.id.item_group_iv,R.drawable.online_notice);
            helper.setVisible(R.id.item_group_count_tv,false);
        }else if(position==1){
            helper.setVisible(R.id.item_group_count_tv,false);
            helper.setImageResource(R.id.item_group_iv,R.drawable.online_answer);
        }else{
            helper.setVisible(R.id.item_group_count_tv,true);
            Glide.with(context).load(item.getImageUrl()).apply(options).into((ImageView) helper.getView(R.id.item_group_iv));
        }
    }
}
