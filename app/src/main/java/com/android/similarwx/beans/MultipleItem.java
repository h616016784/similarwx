package com.android.similarwx.beans;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem extends  BaseBean implements MultiItemEntity {
    /**
     *  文本：0   图片：1    视频：2    通知：5    tip:10    自定义100
     */
    public static final int ITEM_TEXT = 0;
    public static final int ITEM_IMAGE= 1;
    public static final int ITEM_AUDIO= 2;
    public static final int ITEM_RED= 100;//自定义消息类型
    public static final int ITEM_NOTIFICATION= 5;
    public static final int ITEM_TIP= 10;

    private int itemType;
    private int spanSize;

    private String content;
    private String name;

    private String systemCotent;
    private IMMessage imMessage;

    public MultipleItem() {

    }
    public MultipleItem(int itemType, int spanSize, String content) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.content = content;
    }
    public MultipleItem(IMMessage imMessage) {
        this.imMessage=imMessage;
    }
    public MultipleItem(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }
    public String getName() {
        if (TextUtils.isEmpty(name))
            return "测试11";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSystemCotent() {
        return systemCotent;
    }

    public void setSystemCotent(String systemCotent) {
        this.systemCotent = systemCotent;
    }

    public IMMessage getImMessage() {
        return imMessage;
    }

    public void setImMessage(IMMessage imMessage) {
        this.imMessage = imMessage;
    }


    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        if(imMessage==null){
            return ITEM_NOTIFICATION;
        }
        return imMessage.getMsgType().getValue();//云信msg的消息类型
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
