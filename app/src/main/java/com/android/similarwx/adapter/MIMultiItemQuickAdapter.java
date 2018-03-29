package com.android.similarwx.adapter;

import com.android.similarwx.R;
import com.android.similarwx.beans.MIMultiItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hanhuailong on 2018/3/29.
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
        addItemType(MIMultiItem.TEXT, R.layout.item_test);
        addItemType(MIMultiItem.IMG, R.layout.item_text_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, MIMultiItem item) {
        switch (helper.getItemViewType()){
            case MIMultiItem.TEXT:
                helper.setText(R.id.item_test_tv,item.toString());
                break;
            case MIMultiItem.IMG:

                break;
        }
    }
}
