package com.android.similarwx.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.ServiceItemBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2018/4/7.
 */

public class ServiceAdapter extends BaseQuickAdapter<ServiceItemBean,BaseViewHolder> {
    private Context context;
    RequestOptions options;
    public ServiceAdapter(int layoutResId,Context context) {
        super(layoutResId);
        this.context=context;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.pic_1_cs)
                .error(R.drawable.pic_1_cs);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceItemBean item) {
        helper.setText(R.id.service_item_content_tv,item.getContent());
        helper.setText(R.id.service_item_role_tv,item.getRole());
//        Glide.with(context).load(item.getImageUrl()).apply(options).into((ImageView) helper.getView(R.id.service_item_iv));
//        Glide.with(context).load(item.getImageUrl()).into((ImageView) helper.getView(R.id.service_item_iv));
    }
}
