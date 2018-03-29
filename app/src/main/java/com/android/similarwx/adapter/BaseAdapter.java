package com.android.similarwx.adapter;

import android.support.annotation.Nullable;

import com.android.similarwx.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by hanhuailong on 2018/3/29.
 */

public class BaseAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public BaseAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_test_tv,item);
        helper.addOnClickListener(R.id.item_test_bt);
        helper.addOnClickListener(R.id.item_test_bt2);
    }
}
