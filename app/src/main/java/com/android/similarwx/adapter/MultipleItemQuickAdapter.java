package com.android.similarwx.adapter;

import android.content.Context;

import com.android.similarwx.R;
import com.android.similarwx.beans.MultipleItem;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

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
        addItemType(MultipleItem.ITEM_IMAGE, R.layout.item_mi_image_type);
        addItemType(MultipleItem.ITEM_AUDIO,R.layout.item_red_type);
        addItemType(MultipleItem.ITEM_NOTIFICATION,R.layout.item_sys_type);
        addItemType(MultipleItem.ITEM_TIP,R.layout.item_sys_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.ITEM_TEXT:
                IMMessage imMessage=item.getImMessage();
                if (imMessage.getDirect()== MsgDirectionEnum.Out){
                    helper.setVisible(R.id.item_mitext_right_iv,false);helper.setVisible(R.id.item_mitext_left_iv,true);
                    helper.setVisible(R.id.item_mitext_right_title,false);helper.setVisible(R.id.item_mitext_left_title,true);
                    helper.setVisible(R.id.item_mitext_right_content,false);helper.setVisible(R.id.item_mitext_left_content,true);
                    helper.setText(R.id.item_mitext_left_title,item.getName());
                    helper.setText(R.id.item_mitext_left_content,item.getImMessage().getContent());
                }else {
                    helper.setVisible(R.id.item_mitext_right_iv,true);helper.setVisible(R.id.item_mitext_left_iv,false);
                    helper.setVisible(R.id.item_mitext_right_title,true);helper.setVisible(R.id.item_mitext_left_title,false);
                    helper.setVisible(R.id.item_mitext_right_content,true);helper.setVisible(R.id.item_mitext_left_content,false);
                    helper.setText(R.id.item_mitext_right_title,item.getName());
                    helper.setText(R.id.item_mitext_right_content,item.getImMessage().getContent());
                }
                break;
            case MultipleItem.ITEM_IMAGE:
                helper.setText(R.id.item_mitext_left_content, item.getContent());
                break;
        }
    }

}
