package com.android.similarwx.beans;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem extends  BaseBean implements MultiItemEntity {
    public static final int ITEM_TEXT = 0;
    public static final int ITEM_IMAGE= 1;
    public static final int ITEM_AUDIO= 2;
    public static final int ITEM_RED= 3;
    public static final int ITEM_NOTIFICATION= 5;
    public static final int ITEM_TIP= 10;

    private int itemType;
    private int spanSize;

    public String getName() {
        if (TextUtils.isEmpty(name))
            return "测试11";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public IMMessage getImMessage() {
        return imMessage;
    }

    public void setImMessage(IMMessage imMessage) {
        this.imMessage = imMessage;
    }

    private IMMessage imMessage;
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

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return imMessage.getMsgType().getValue();//云信msg的消息类型
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
