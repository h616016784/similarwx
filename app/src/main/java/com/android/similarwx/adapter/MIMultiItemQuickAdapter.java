package com.android.similarwx.adapter;

import com.android.similarwx.R;
import com.android.similarwx.beans.MIMultiItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hanhuailong on 2018/3/30.
 */

public class MIMultiItemQuickAdapter extends BaseMultiItemQuickAdapter<MIMultiItem,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MIMultiItemQuickAdapter(List<MIMultiItem> data) {
        super(data);
        addItemType(MIMultiItem.TEXT, R.layout.item_text_view);
        addItemType(MIMultiItem.IMG,R.layout.item_image_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MIMultiItem item) {
        switch (helper.getItemViewType()) {
            case MIMultiItem.TEXT:
//                helper.setText(R.id.item_mutil_tv,"item_mutil_tv");
                break;
            case MIMultiItem.IMG:
                break;
        }
    }
}
