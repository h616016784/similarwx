package com.android.similarwx.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.ServiceItemBean;
import com.android.similarwx.beans.response.RspService;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.impl.NimUIKitImpl;

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
        String detailContent = NimUIKitImpl.getOnlineStateContentProvider().getDetailDisplay(item.getAccId());
        if (TextUtils.isEmpty(detailContent)){
            helper.setText(R.id.service_item_role_tv," (离线)");
        }else {
            helper.setText(R.id.service_item_role_tv," ("+detailContent+")");
        }
        helper.setText(R.id.service_item_content_tv,item.getName());
//        Glide.with(context).load(item.getImageUrl()).apply(options).into((ImageView) helper.getView(R.id.service_item_iv));
        Glide.with(context).load(item.getIcon()).into((ImageView) helper.getView(R.id.service_item_iv));
    }
}
