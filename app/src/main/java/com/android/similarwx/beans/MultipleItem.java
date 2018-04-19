package com.android.similarwx.beans;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem extends  BaseBean implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    public static final int IMG_TEXT = 3;
    public static final int TEXT_SPAN_SIZE = 3;
    public static final int IMG_SPAN_SIZE = 1;
    public static final int IMG_TEXT_SPAN_SIZE = 4;
    public static final int IMG_TEXT_SPAN_SIZE_MIN = 2;

    public static final int ITEM_VIEW_TYPE_MSG = 10;
    public static final int ITEM_VIEW_TYPE_MSG_RIGHT = 11;
    public static final int ITEM_VIEW_TYPE_MSG_SYS= 12;
    public static final int ITEM_VIEW_TYPE_MSG_RED= 13;
    public static final int ITEM_VIEW_TYPE_MSG_RED_RIGHT= 14;
    public static final int ITEM_VIEW_TYPE_MSG_VEDIO= 15;
    public static final int ITEM_VIEW_TYPE_MSG_VEDIO_RIGHT= 16;
    //0是文本消息/语音消息/图片，1与0相反、本人发送，2是系统消息（谁进了谁退出群了等），3是红包，4与3相反、本人发的红包，5视频,6与5相反、本人发的视频
    private int itemType;
    private int spanSize;

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
        return imMessage.getDirect().getValue();//0是发送出去，1是接收的消息
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
