package com.android.similarwx.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.outbaselibrary.primary.Log;
import com.android.similarwx.R;
import com.android.similarwx.beans.CharImageBean;
import com.android.similarwx.beans.MultipleItem;
import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.misdk.helper.TeamNotificationHelper;
import com.android.similarwx.utils.glide.CircleCrop;
//import com.android.similarwx.widget.emoji.EmojiManager;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.NotificationType;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.SystemMessage;
import com.netease.nimlib.sdk.team.model.MemberChangeAttachment;
import com.netease.nimlib.sdk.team.model.UpdateTeamAttachment;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    private Context context;
    private Gson gson;
    public MultipleItemQuickAdapter(Context context,List<MultipleItem> data) {
        super(data);
        gson=new Gson();
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
        List accounts=new ArrayList();
        accounts.add(item.getImMessage().getFromAccount());
        NIMClient.getService(UserService.class).fetchUserInfo(accounts)
                .setCallback(new RequestCallback<List<UserInfo>>() {
                    @Override
                    public void onSuccess(List<UserInfo> param) {
                        if (param!=null&& param.size()>0){
                            String imageUrl=param.get(0).getAvatar();
                            switch (helper.getItemViewType()) {
                                case MultipleItem.ITEM_TEXT://文本
                                    if (imMessage.getDirect()== MsgDirectionEnum.Out){
                                        loadHeadImage(imageUrl,helper,R.id.item_mitext_left_iv);
                                    }else {
                                        loadHeadImage(imageUrl,helper,R.id.item_mitext_right_iv);
                                    }
                                    break;
                                case MultipleItem.ITEM_IMAGE://图片
                                    if (imMessage.getDirect()== MsgDirectionEnum.Out){
                                        loadHeadImage(imageUrl,helper,R.id.item_mi_image_left_iv);
                                    }else {
                                        loadHeadImage(imageUrl,helper,R.id.item_mi_image_right_iv);
                                    }
                                    break;
                                case MultipleItem.ITEM_AUDIO://音频
                                    if (imMessage.getDirect()== MsgDirectionEnum.Out){
                                        loadHeadImage(imageUrl,helper,R.id.item_mi_auto_left_iv);
                                    }else {
                                        loadHeadImage(imageUrl,helper,R.id.item_mi_auto_right_iv);
                                    }
                                    break;
                                case MultipleItem.ITEM_NOTIFICATION://通知类信息

                                    break;
                                case MultipleItem.ITEM_RED://红包类信息
                                    if (imMessage.getDirect()== MsgDirectionEnum.Out){
                                        loadHeadImage(imageUrl,helper,R.id.item_red_left_iv);
                                    }else {
                                        loadHeadImage(imageUrl,helper,R.id.item_red_right_iv);
                                    }
                                    break;

                            }
                        }
                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
        switch (helper.getItemViewType()) {
            case MultipleItem.ITEM_TEXT://文本

                if (imMessage.getDirect()== MsgDirectionEnum.Out){
                    helper.setVisible(R.id.item_mitext_right_iv,false);helper.setVisible(R.id.item_mitext_left_iv,true);
                    helper.setVisible(R.id.item_mitext_right_title,false);helper.setVisible(R.id.item_mitext_left_title,true);
                    helper.setVisible(R.id.item_mitext_right_content,false);helper.setVisible(R.id.item_mitext_left_content,true);
                    helper.setText(R.id.item_mitext_left_title,item.getImMessage().getFromNick());
//                    helper.setText(R.id.item_mitext_left_content,item.getImMessage().getContent());
                    String textContent=item.getImMessage().getContent();
                    if (!TextUtils.isEmpty(textContent)){
                        filterTextContext(textContent , (TextView) helper.getView(R.id.item_mitext_left_content));
                    }

                }else {
                    helper.setVisible(R.id.item_mitext_right_iv,true);helper.setVisible(R.id.item_mitext_left_iv,false);
                    helper.setVisible(R.id.item_mitext_right_title,true);helper.setVisible(R.id.item_mitext_left_title,false);
                    helper.setVisible(R.id.item_mitext_right_content,true);helper.setVisible(R.id.item_mitext_left_content,false);
                    helper.setText(R.id.item_mitext_right_title,item.getImMessage().getFromNick());
//                    helper.setText(R.id.item_mitext_right_content,item.getImMessage().getContent());
                    String textContent=item.getImMessage().getContent();
                    if (!TextUtils.isEmpty(textContent)){
                        filterTextContext(textContent , (TextView) helper.getView(R.id.item_mitext_right_content));
                    }

                }
                break;
            case MultipleItem.ITEM_IMAGE://图片
                if (imMessage.getDirect()== MsgDirectionEnum.Out){

                    helper.setVisible(R.id.item_mi_image_right_iv,false);helper.setVisible(R.id.item_mi_image_left_iv,true);
                    helper.setVisible(R.id.item_mi_image_right_title,false);helper.setVisible(R.id.item_mi_image_left_title,true);
                    helper.setVisible(R.id.item_mi_image_right_content,false);helper.setVisible(R.id.item_mi_image_left_content,true);
                    helper.setText(R.id.item_mi_image_left_title,item.getImMessage().getFromNick());
                    String s=imMessage.getAttachment().toJson(false);
                    Gson gson=new Gson();
                    CharImageBean charImageBean=gson.fromJson(s, CharImageBean.class);
                    String imagePath=charImageBean.getPath();
                    if (!TextUtils.isEmpty(imagePath)){
                        Glide.with(context)
                                .load(new File(imagePath))
//                                .override(120,120)
//                                .error(R.drawable.nim_default_img_failed)
                                .into((ImageView) helper.getView(R.id.item_mi_image_left_content));
                    }

                }else {
                    helper.setVisible(R.id.item_mi_image_right_iv,true);helper.setVisible(R.id.item_mi_image_left_iv,false);
                    helper.setVisible(R.id.item_mi_image_right_title,true);helper.setVisible(R.id.item_mi_image_left_title,false);
                    helper.setVisible(R.id.item_mi_image_right_content,true);helper.setVisible(R.id.item_mi_image_left_content,false);
                    helper.setText(R.id.item_mi_image_right_title,item.getImMessage().getFromNick());

                    String s=imMessage.getAttachment().toJson(false);
                    Gson gson=new Gson();
                    CharImageBean charImageBean=gson.fromJson(s, CharImageBean.class);
                    String imagePath=charImageBean.getPath();
                    if (!TextUtils.isEmpty(imagePath)){
                        Glide.with(context).load(new File(imagePath))
//                                .override(120,120)
                                .into((ImageView) helper.getView(R.id.item_mi_image_right_content));
                    }

                }
                break;
            case MultipleItem.ITEM_AUDIO://音频
                if (imMessage.getDirect()== MsgDirectionEnum.Out){

                    helper.setVisible(R.id.item_mi_auto_right_iv,false);helper.setVisible(R.id.item_mi_auto_left_iv,true);
                    helper.setVisible(R.id.item_mi_auto_right_title,false);helper.setVisible(R.id.item_mi_auto_left_title,true);
                    helper.setVisible(R.id.item_mi_auto_right_content,false);helper.setVisible(R.id.item_mi_auto_left_content,true);
                    helper.setBackgroundRes(R.id.item_mi_auto_content_rl,R.drawable.mi_chatfrom_bg_normal);
                    helper.setText(R.id.item_mi_auto_left_title,item.getImMessage().getFromNick());

                }else {
                    helper.setVisible(R.id.item_mi_auto_right_iv,true);helper.setVisible(R.id.item_mi_auto_left_iv,false);
                    helper.setVisible(R.id.item_mi_auto_right_title,true);helper.setVisible(R.id.item_mi_auto_left_title,false);
                    helper.setVisible(R.id.item_mi_auto_right_content,true);helper.setVisible(R.id.item_mi_auto_left_content,false);
                    helper.setBackgroundRes(R.id.item_mi_auto_content_rl,R.drawable.ease_chatfrom_bg_normal_right);
                    helper.setText(R.id.item_mi_auto_right_title,item.getImMessage().getFromNick());

                }

                break;
            case MultipleItem.ITEM_NOTIFICATION://通知类信息

                String noticeContexnt=TeamNotificationHelper.getMsgShowText(imMessage);
                helper.setText(R.id.item_sys_tv,noticeContexnt);
                break;
            case MultipleItem.ITEM_RED://红包类信息
                MsgAttachment attachment=imMessage.getAttachment();
                if (imMessage.getDirect()== MsgDirectionEnum.Out){
                    helper.setVisible(R.id.item_red_right_iv,false);helper.setVisible(R.id.item_red_left_iv,true);
                    helper.setVisible(R.id.item_red_right_content,false);helper.setVisible(R.id.item_red_left_content,true);
                    helper.setVisible(R.id.item_red_packet_right_rl,false);helper.setVisible(R.id.item_red_packet_rl,true);

                }else {
                    helper.setVisible(R.id.item_red_left_iv,false);helper.setVisible(R.id.item_red_right_iv,true);
                    helper.setVisible(R.id.item_red_left_content,false);helper.setVisible(R.id.item_red_right_content,true);
                    helper.setVisible(R.id.item_red_packet_rl,false);helper.setVisible(R.id.item_red_packet_right_rl,true);

                }
                if (attachment!=null){
                    String json=attachment.toJson(false);
                    if (!TextUtils.isEmpty(json)){
                        SendRed bean=gson.fromJson(json, SendRed.class);
                        String fromNick=item.getImMessage().getFromNick();
                        String amount=null;
                        String title=null;
                        String click=null;
                        String textContent=null;
                        if (bean!=null){
                            SendRed.SendRedBean sendRedBean=bean.getData();
                            if (sendRedBean!=null){
                                amount =sendRedBean.getAmount();
                                title =sendRedBean.getTitle();
                                click=sendRedBean.getClick();
                                if (TextUtils.isEmpty(sendRedBean.getThunder()))
                                    textContent=sendRedBean.getAmount()+"-"+sendRedBean.getCount();
                                else
                                    textContent=sendRedBean.getAmount()+"-"+sendRedBean.getThunder();
                            }

                        }
                        if (imMessage.getDirect()== MsgDirectionEnum.Out){
                            if (!TextUtils.isEmpty(fromNick))
                                helper.setText(R.id.item_red_left_content,fromNick);
                            helper.setText(R.id.item_red_packet_explain_tv,title);
                            if (!TextUtils.isEmpty(click)){
                                if (click.equals(1))
                                    helper.setText(R.id.item_red_packet_take_tv,"已抢过");
                            }
                            helper.setText(R.id.item_red_packet_count_tv,textContent);
                        }else {
                            if (!TextUtils.isEmpty(fromNick))
                                helper.setText(R.id.item_red_right_content,fromNick);
                            if (!TextUtils.isEmpty(click)){
                                if (click.equals(1))
                                    helper.setText(R.id.item_red_packet_take_right_tv,"已抢过");
                            }
                            helper.setText(R.id.item_red_packet_count_right_tv,textContent);
                        }
                    }
                }
                break;
        }
    }

    private void loadHeadImage(String imageUrl,BaseViewHolder helper,int res){
        if (!TextUtils.isEmpty(imageUrl)){
            Glide.with(context).load(imageUrl)
//                    .override(60,60)
//                    .transform(new CircleCrop(context))
//                    .placeholder(R.drawable.rp_avatar)
//                    .error(R.drawable.rp_avatar)
                    .into((ImageView) helper.getView(res));
        }
    }
    private void filterTextContext(String textContent, TextView textView) {
        textView.setText("");
        boolean isFin=true;
        char[] chars=textContent.toCharArray();
        List<Character> tempCharList=new ArrayList<>();
        List<Character> tempEmojiCharList=new ArrayList<>();
        int length=chars.length;

        if (chars!=null && length>0){
            for (int i=0;i<length;i++){
                if (chars[i]=='[')
                    isFin=false;
                if (isFin){
                    tempCharList.add(chars[i]);
                }else {
                    //添加字符串串到textview
                    if (tempCharList.size()>0){
                        String temp="";
                        for (Character c:tempCharList){
                            temp+=String.valueOf(c);
                        }
                        textView.append(temp);
                        tempCharList.clear();
                    }
                    //开始添加emoji表情字符串
                    tempEmojiCharList.add(chars[i]);
                }
                if (chars[i]==']'){
                    isFin=true;
                    if (tempEmojiCharList.size()>0){
                        //获得emoji表情
                        String temp="";
                        for (Character c:tempEmojiCharList){
                            temp+=String.valueOf(c);
                        }
//                        String emoji=tempEmojiCharList.toString();
//                        Drawable d=EmojiManager.getDrawable(context,temp);
                        Drawable d=null;
                        SpannableString ss = new SpannableString("emoji");
                        //得到drawable对象，即所要插入的图片
                        d.setBounds(0, 0, d.getIntrinsicWidth()/2, d.getIntrinsicHeight()/2);
                        //用这个drawable对象代替字符串easy
                        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                        //包括0但是不包括"easy".length()即：4。[0,4)。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
                        ss.setSpan(span, 0, "emoji".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        textView.append(ss);

                        //最后清空
                        tempEmojiCharList.clear();
                    }
                }
            }
            //
            if (tempCharList.size()>0){
                String temp="";
                for (Character c:tempCharList){
                    temp+=String.valueOf(c);
                }
                textView.append(temp);
                tempCharList.clear();
            }
            if (tempEmojiCharList.size()>0){
                String temp="";
                for (Character c:tempEmojiCharList){
                    temp+=String.valueOf(c);
                }
                textView.append(temp);
                tempEmojiCharList.clear();
            }
        }
    }

}
