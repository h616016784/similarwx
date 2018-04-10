package com.android.similarwx.adapter;

import com.android.similarwx.R;
import com.android.similarwx.beans.RedDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by hanhuailong on 2018/4/10.
 */

public class RedDetailAdapter extends BaseQuickAdapter<RedDetailBean,BaseViewHolder> {
    public RedDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedDetailBean item) {
        helper.setText(R.id.item_red_detail_name_tv,item.getName());
        helper.setText(R.id.item_red_detail_time_tv,item.getTime());
        helper.setText(R.id.item_red_detail_money_tv,item.getMoney());
        helper.setText(R.id.item_red_detail_shouqi_tv,item.getShouqi());
    }
}
