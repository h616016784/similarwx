package com.android.similarwx.utils.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.android.similarwx.R;
import com.android.similarwx.activity.SysNoticeActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseActivity;
import com.android.similarwx.base.NormalActivity;
import com.android.similarwx.fragment.NoticeFragment;
import com.android.similarwx.utils.SharePreferenceUtil;

/**
 * Created by hanhuailong on 2018/6/21.
 */

public class NotificationUtil {
    private Activity context;
    NotificationConfig config;
    private int id = 1;
    public NotificationUtil(Activity context,NotificationConfig config){
        this.context=context;
        this.config=config;
    }
    private static NotificationUtil mNotificationUtil;
    public static NotificationUtil getInstance(Activity context,NotificationConfig config){
        if (mNotificationUtil==null){
            mNotificationUtil= new NotificationUtil(context,config);
        }
        return mNotificationUtil;
    }
    public void notification() {
        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.logal);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        //设置小图标
        mBuilder.setSmallIcon(R.mipmap.logal);
        //设置大图标
        mBuilder.setLargeIcon(bitmap);
        //设置标题
        mBuilder.setContentTitle("这是标题");
        //设置通知正文
        mBuilder.setContentText("这是正文，当前ID是：" + id);
        //设置摘要
        mBuilder.setSubText("这是摘要");
        //通知在状态栏显示时的文本
        mBuilder.setTicker("在状态栏上显示的文本");
        if (config!=null){
            mBuilder.setContentTitle(config.getContentTitle());
            mBuilder.setContentText(config.getContentText());
            mBuilder.setSubText(config.getSubText());
            mBuilder.setTicker(config.getTicker());
        }

        //设置是否点击消息后自动clean
        mBuilder.setAutoCancel(true);
        //显示指定文本
        mBuilder.setContentInfo("Info");
        //与setContentInfo类似，但如果设置了setContentInfo则无效果
        //用于当显示了多个相同ID的Notification时，显示消息总数
        mBuilder.setNumber(1);

        //设置优先级
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        //自定义消息时间，以毫秒为单位，当前设置为比系统时间少一小时
//        mBuilder.setWhen(System.currentTimeMillis() - 3600000);
        //设置为一个正在进行的通知，此时用户无法清除通知
//        mBuilder.setOngoing(true);
        //设置消息的提醒方式，震动提醒：DEFAULT_VIBRATE     声音提醒：NotificationCompat.DEFAULT_SOUND
        //三色灯提醒NotificationCompat.DEFAULT_LIGHTS     以上三种方式一起：DEFAULT_ALL
        int sound= SharePreferenceUtil.getInt(context, AppConstants.USER_SOUND_SET);
        if (sound==1)
            mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        else
            mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        //设置震动方式，延迟零秒，震动一秒，延迟一秒、震动一秒
//        mBuilder.setVibrate(new long[]{0, 1000, 1000, 1000});
//        Intent intent = new Intent(context, SysNoticeActivity.class);
        Intent intent = new Intent(context, NormalActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(BaseActivity.ARGUMENT_EXTRA_FRAGMENT_NAME, NoticeFragment.class.getName());
        intent.putExtras(bundle);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(pIntent);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id++, mBuilder.build());
    }

    public static class NotificationConfig{
        public NotificationConfig(){
        }
        private String contentTitle;//标题
        private String contentText;//正文
        private String subText;//摘要
        private String ticker;//状态栏显示的文本

        public String getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
            this.contentTitle = contentTitle;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public String getSubText() {
            return subText;
        }

        public void setSubText(String subText) {
            this.subText = subText;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }
    }
}
