package com.android.similarwx.adapter;

import android.content.Context;

import com.android.similarwx.R;
import com.android.similarwx.beans.GroupMessageBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class HomeAdapter extends BaseQuickAdapter<GroupMessageBean,BaseViewHolder>{
    private Context context;
    public HomeAdapter(int layoutResId,Context context,List<GroupMessageBean> mListData){
        super(layoutResId,mListData);
        this.context=context;
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
//            helper.setVisible(R.id.item_group_count_tv,true);
//            Glide.with(context).load(item.getImageUrl()).
//            into((ImageView) helper.getView(R.id.item_group_iv));
        }
    }
}
