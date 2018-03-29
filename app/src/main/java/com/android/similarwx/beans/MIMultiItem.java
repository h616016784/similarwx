package com.android.similarwx.beans;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by hanhuailong on 2018/3/29.
 */

public class MIMultiItem implements MultiItemEntity {

    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;

    public MIMultiItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
