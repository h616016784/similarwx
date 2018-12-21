package com.android.similarwx.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.android.similarwx.R;

import static com.android.similarwx.service.ForegroundService.NOTIFICATION_ID;

/**
 * Created by hanhuailong on 2018/12/21.
 */

public class InnerService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        //发送与KeepLiveService中ID相同的Notification，然后将其取消并取消自己的前台显示
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        startForeground(NOTIFICATION_ID, builder.build());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopForeground(true);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(NOTIFICATION_ID);
                stopSelf();
            }
        },100);

    }
}
