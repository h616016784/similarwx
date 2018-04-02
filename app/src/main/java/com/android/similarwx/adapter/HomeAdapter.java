package com.android.similarwx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.GroupMessageBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class HomeAdapter extends BaseQuickAdapter<GroupMessageBean,BaseViewHolder>{
    private Context context;
    RequestOptions options;
    public HomeAdapter(int layoutResId,Context context,List<GroupMessageBean> mListData){
        super(layoutResId,mListData);
        this.context=context;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

    }

    @Override
    protected void convert(BaseViewHolder helper, GroupMessageBean item) {
        helper.setText(R.id.item_group_tv,item.getName());
        helper.setText(R.id.item_group_count_tv,item.getMsgCount());
        if(helper.getLayoutPosition()==0){
            helper.setImageResource(R.id.item_group_iv,R.drawable.online_notice);
            helper.setVisible(R.id.item_group_count_tv,false);
        }else if(helper.getLayoutPosition()==1){
            helper.setVisible(R.id.item_group_count_tv,false);
            helper.setImageResource(R.id.item_group_iv,R.drawable.online_answer);
        }else{
            helper.setVisible(R.id.item_group_count_tv,true);
            Glide.with(context).load(item.getImageUrl()).apply(options).into((ImageView) helper.getView(R.id.item_group_iv));
        }
    }
}
