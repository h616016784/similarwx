package com.android.similarwx.adapter;

import android.content.Context;

import com.android.similarwx.R;
import com.android.similarwx.beans.MultipleItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    private Context context;
    public MultipleItemQuickAdapter(Context context,List<MultipleItem> data) {
        super(data);
        this.context=context;
        addItemType(MultipleItem.ITEM_TEXT, R.layout.item_mitext_type);
        addItemType(MultipleItem.ITEM_IMAGE, R.layout.item_mitext_type);
        addItemType(MultipleItem.ITEM_AUDIO,R.layout.item_red_type);
        addItemType(MultipleItem.ITEM_NOTIFICATION,R.layout.item_sys_type);
        addItemType(MultipleItem.ITEM_TIP,R.layout.item_sys_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.ITEM_TEXT:
                helper.setText(R.id.item_mitext_left_content, item.getContent());
                break;
            case MultipleItem.ITEM_IMAGE:
                helper.setText(R.id.item_mitext_left_content, item.getContent());
                break;
        }
    }

}
