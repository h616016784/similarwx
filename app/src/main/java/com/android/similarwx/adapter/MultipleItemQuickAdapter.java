package com.android.similarwx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.CharImageBean;
import com.android.similarwx.beans.MultipleItem;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.SystemMessage;

import java.io.File;
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
        addItemType(MultipleItem.ITEM_AUDIO, R.layout.item_mi_auto_type);
        addItemType(MultipleItem.ITEM_RED,R.layout.item_red_type);
        addItemType(MultipleItem.ITEM_NOTIFICATION,R.layout.item_sys_type);
        addItemType(MultipleItem.ITEM_TIP,R.layout.item_sys_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        IMMessage imMessage=item.getImMessage();
        switch (helper.getItemViewType()) {
            case MultipleItem.ITEM_TEXT://文本
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
            case MultipleItem.ITEM_IMAGE://图片
                if (imMessage.getDirect()== MsgDirectionEnum.Out){

                    helper.setVisible(R.id.item_mi_image_right_iv,false);helper.setVisible(R.id.item_mi_image_left_iv,true);
                    helper.setVisible(R.id.item_mi_image_right_title,false);helper.setVisible(R.id.item_mi_image_left_title,true);
                    helper.setVisible(R.id.item_mi_image_right_content,false);helper.setVisible(R.id.item_mi_image_left_content,true);
                    helper.setText(R.id.item_mi_image_left_title,item.getName());
                    String s=imMessage.getAttachment().toJson(false);
                    Gson gson=new Gson();
                    CharImageBean charImageBean=gson.fromJson(s, CharImageBean.class);
                    String imagePath=charImageBean.getPath();
                    Glide.with(context)
                            .load(new File(imagePath))
                            .error(R.drawable.nim_default_img_failed)
                            .into((ImageView) helper.getView(R.id.item_mi_image_left_content));
                }else {
                    helper.setVisible(R.id.item_mi_image_right_iv,true);helper.setVisible(R.id.item_mi_image_left_iv,false);
                    helper.setVisible(R.id.item_mi_image_right_title,true);helper.setVisible(R.id.item_mi_image_left_title,false);
                    helper.setVisible(R.id.item_mi_image_right_content,true);helper.setVisible(R.id.item_mi_image_left_content,false);
                    helper.setText(R.id.item_mi_image_right_title,item.getName());

                    String s=imMessage.getAttachment().toJson(false);
                    Gson gson=new Gson();
                    CharImageBean charImageBean=gson.fromJson(s, CharImageBean.class);
                    String imagePath=charImageBean.getPath();
                    Glide.with(context).load(new File(imagePath)).into((ImageView) helper.getView(R.id.item_mi_image_right_content));
                }
                break;
            case MultipleItem.ITEM_AUDIO://音频
                if (imMessage.getDirect()== MsgDirectionEnum.Out){

                    helper.setVisible(R.id.item_mi_auto_right_iv,false);helper.setVisible(R.id.item_mi_auto_left_iv,true);
                    helper.setVisible(R.id.item_mi_auto_right_title,false);helper.setVisible(R.id.item_mi_auto_left_title,true);
                    helper.setVisible(R.id.item_mi_auto_right_content,false);helper.setVisible(R.id.item_mi_auto_left_content,true);
                    helper.setBackgroundRes(R.id.item_mi_auto_content_rl,R.drawable.mi_chatfrom_bg_normal);
                    helper.setText(R.id.item_mi_auto_left_title,item.getName());
                }else {
                    helper.setVisible(R.id.item_mi_auto_right_iv,true);helper.setVisible(R.id.item_mi_auto_left_iv,false);
                    helper.setVisible(R.id.item_mi_auto_right_title,true);helper.setVisible(R.id.item_mi_auto_left_title,false);
                    helper.setVisible(R.id.item_mi_auto_right_content,true);helper.setVisible(R.id.item_mi_auto_left_content,false);
                    helper.setBackgroundRes(R.id.item_mi_auto_content_rl,R.drawable.ease_chatfrom_bg_normal_right);
                    helper.setText(R.id.item_mi_auto_right_title,item.getName());
                }

                break;
            case MultipleItem.ITEM_NOTIFICATION://通知类信息
                String content= item.getSystemCotent();
                if (!TextUtils.isEmpty(content))
                     helper.setText(R.id.item_sys_tv,item.getImMessage().getContent());
                break;
            case MultipleItem.ITEM_RED://红包类信息
                if (imMessage.getDirect()== MsgDirectionEnum.Out){
                    helper.setVisible(R.id.item_red_right_iv,false);helper.setVisible(R.id.item_red_left_iv,true);
                    helper.setVisible(R.id.item_red_right_content,false);helper.setVisible(R.id.item_red_left_content,true);
                    helper.setVisible(R.id.item_red_packet_right_rl,false);helper.setVisible(R.id.item_red_packet_rl,true);
                }else {
                    helper.setVisible(R.id.item_red_left_iv,false);helper.setVisible(R.id.item_red_right_iv,true);
                    helper.setVisible(R.id.item_red_left_content,false);helper.setVisible(R.id.item_red_right_content,true);
                    helper.setVisible(R.id.item_red_packet_rl,false);helper.setVisible(R.id.item_red_packet_right_rl,true);
                }
                break;
        }
    }

}
