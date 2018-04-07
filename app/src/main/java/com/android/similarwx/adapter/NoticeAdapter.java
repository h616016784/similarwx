package com.android.similarwx.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.similarwx.R;
import com.android.similarwx.beans.NoticeItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

public class NoticeAdapter extends BaseQuickAdapter<NoticeItemBean,BaseViewHolder> {

    public NoticeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeItemBean item) {
        helper.setText(R.id.notice_item_title,item.getTitle());
        helper.setText(R.id.notice_item_time,item.getTime());
        helper.setText(R.id.notice_item_content,item.getContent());
        helper.setText(R.id.notice_item_content_detail,item.getContentdetail());
    }
}
