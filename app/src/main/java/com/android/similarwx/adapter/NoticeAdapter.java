package com.android.similarwx.adapter;

import com.android.similarwx.R;
import com.android.similarwx.beans.Notice;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2018/4/6.
 */

public class NoticeAdapter extends BaseQuickAdapter<Notice,BaseViewHolder> {

    public NoticeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Notice item) {
        helper.setText(R.id.notice_item_title,item.getTitle());
        helper.setText(R.id.notice_item_time,item.getCreateDate());
        helper.setText(R.id.notice_item_content,item.getType());
        helper.setText(R.id.notice_item_content_detail,item.getContent());
    }
}
