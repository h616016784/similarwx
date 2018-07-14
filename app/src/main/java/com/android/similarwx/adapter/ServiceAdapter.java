package com.android.similarwx.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.ServiceItemBean;
import com.android.similarwx.beans.response.RspService;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2018/4/7.
 */

public class ServiceAdapter extends BaseQuickAdapter<RspService.DataBean.ResUsersDetailListBean,BaseViewHolder> {
    private Context context;
    public ServiceAdapter(int layoutResId,Context context) {
        super(layoutResId);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RspService.DataBean.ResUsersDetailListBean item) {
        helper.setText(R.id.service_item_content_tv,item.getName());
        helper.setText(R.id.service_item_role_tv,item.getMobile());
//        Glide.with(context).load(item.getImageUrl()).apply(options).into((ImageView) helper.getView(R.id.service_item_iv));
        Glide.with(context).load(item.getIcon()).into((ImageView) helper.getView(R.id.service_item_iv));
    }
}
