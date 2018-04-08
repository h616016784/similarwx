package com.android.similarwx.adapter;

import android.content.Context;

import com.android.similarwx.R;
import com.android.similarwx.beans.MultipleItem;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    private Context context;
    RequestOptions options;
    public MultipleItemQuickAdapter(Context context,List<MultipleItem> data) {
        super(data);
        this.context=context;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        addItemType(MultipleItem.ITEM_VIEW_TYPE_MSG, R.layout.item_mitext_type);
        addItemType(MultipleItem.ITEM_VIEW_TYPE_MSG_RED,R.layout.item_red_type);
        addItemType(MultipleItem.ITEM_VIEW_TYPE_MSG_SYS,R.layout.item_sys_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.ITEM_VIEW_TYPE_MSG:
                helper.setText(R.id.item_mitext_left_content, item.getContent());
                break;
            case MultipleItem.ITEM_VIEW_TYPE_MSG_RED:
                helper.setText(R.id.item_red_packet_count_tv, item.getContent());
                break;
            case MultipleItem.ITEM_VIEW_TYPE_MSG_SYS:
                helper.setText(R.id.item_sys_tv, item.getContent());
                break;
        }
    }

}
