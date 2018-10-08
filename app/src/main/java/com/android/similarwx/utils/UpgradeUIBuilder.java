package com.android.similarwx.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.similarwx.R;
import com.android.similarwx.widget.dialog.DownLoadFragmentDialog;

/**
 * 下载APP
 *
 */
@SuppressLint("NewApi")
public class UpgradeUIBuilder {

	private static final String TAG = "AppUpgrade";
	public static final int NOTIFY_ID = 0;
	private Context mContext = null;
	private File updateFile = null;
	private NotificationManager mNotificationManager = null;
	private long downloadFileSize = 0;
	private long fileSize = 0;
	public Notification mNotification = null;
	private boolean cancelled = true;
	private String mAppUpgradeURL = "";
	private String pkgPath = "";

	// broadcastReceiver 指令
	public static final String ACTION_UPGRADE_DOWNLOAD_BEGIN = "com.appupgrade.downloadbegin";
	public static final String ACTION_UPGRADE_DOWNLOAD_SUCCESS = "com.appupgrade.downloadsuccess";
	public static final String ACTION_UPGRADE_NETWORK_DISCONNECTED = "com.appupgrade.networkdisconnected";
	public static final String ACTION_UPGRADE_IOEXCEPTION = "com.appupgrade.ioexception";

	private Handler progressHandler = new Handler() {

		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int rate = msg.arg1;
				// Log.i(TAG, "handleMessage: ---------1--------" + rate);
				if (rate < 100) {
					if (rate % 10 == 0) {
						Log.i(TAG, "rate:" + rate);
					}
					// 更新进度
//					 RemoteViews contentView = mNotification.contentView;
//					 contentView.setTextViewText(R.id.rate, rate + "%");
//					 contentView.setProgressBar(R.id.progress, 100, rate,
//					 false);
					// 最后别忘了通知一下,否则不会更新
//					 mNotificationManager.notify(NOTIFY_ID, mNotification);
				} else {
					// 下载完毕后变换通知形式
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					// android应用升级接口
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(updateFile), "application/vnd.android.package-archive");
					Log.i(TAG, "intent:" + intent.getAction());

					// 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
//					PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent,
//							PendingIntent.FLAG_UPDATE_CURRENT);
//					mNotification.contentIntent = contentIntent;
//					CharSequence contentTitle = mContext.getApplicationInfo().loadLabel(mContext.getPackageManager());
//					mNotification.setLatestEventInfo(mContext, contentTitle, "New version available", contentIntent);
//					// 最后别忘了通知一下,否则不会更新
//					mNotificationManager.notify(NOTIFY_ID, mNotification);
//					mContext.sendBroadcast(new Intent(ACTION_UPGRADE_DOWNLOAD_SUCCESS));
				}
				break;
			case 0:
				// 取消通知
				Log.i(TAG, "handleMessage: ---------0--------");
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case 2://更新dialog
//				int pos=0;
//				int total=0;
//				int percent=0;
				dialog.setText(percent+"");
				dialog.setProgress(pos);
				break;
			case 3://设置最大值
				dialog.setProgressMax(total);
				break;
			case 4://下载成功
				if(dialog!=null){
					dialog.dismiss();
				}
				if(updateFile!=null){
					openFile(updateFile);
				}
				break;
			}
		};
	};

	public UpgradeUIBuilder(Context context, String URL) {
		this.mContext = context;
		this.mAppUpgradeURL = URL;
		pkgPath = Environment.getDataDirectory() + "/data/" + context.getPackageName();
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Log.i(TAG, "mNotificationManager:" + mNotificationManager);
	}

	public void downloadNewApp() {
		mContext.sendBroadcast(new Intent(ACTION_UPGRADE_DOWNLOAD_BEGIN));
		setUpNotification();
		new Thread() {
			public void run() {
//				downloadFile(mAppUpgradeURL);//httclient
				downloadAppFile(mAppUpgradeURL);//httpurlconnection
			};
		}.start();

		new Thread() {
			public void run() {
				readRate();
			};
		}.start();
	}
	
	DownLoadFragmentDialog dialog;
	public void downloadNewAppNew() {
		Activity mactivity=(Activity) mContext;
		dialog=new DownLoadFragmentDialog();
		dialog.setCancelable(false);
		dialog.show(mactivity.getFragmentManager(), "DownLoadFragmentDialog");
		new Thread() {
			public void run() {
//				downloadFile(mAppUpgradeURL);//httclient
				downloadAppFileNew(mAppUpgradeURL);//httpurlconnection
			};
		}.start();
	}

	/**
	 * 创建通知
	 */
	private void setUpNotification() {
		int icon = mContext.getApplicationInfo().icon;
		CharSequence tickerText = mContext.getApplicationInfo().loadLabel(mContext.getPackageManager());
		Log.i(TAG, "tickerText:" + tickerText);
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		
		//  放置在"正在运行"栏目中
		 mNotification.flags = Notification.FLAG_ONGOING_EVENT;

//		 RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
//		 R.layout.download_notification_layout);
//		 contentView.setTextViewText(R.id.fileName, "Action.apk");
//
//		//  添加停止按钮 停止下载
//		 Intent stopIntent = new Intent();
//		 contentView.setOnClickFillInIntent(R.id.noti_stop_button,
//		 stopIntent);
//		 // 指定个性化视图
//		 mNotification.contentView = contentView;
//
////		 Intent intent = new Intent(context, FileMgrActivity2.class);
//		 Intent intent = new Intent();
//		 PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
//		 intent,
//		 PendingIntent.FLAG_UPDATE_CURRENT);
//		 // 指定内容意图
//		 mNotification.contentIntent = contentIntent;
//		 mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	/**
	 * 下载模块
	 */
	private void readRate() {
		cancelled = false;
		int rate = 0;
		while (!cancelled && rate < 100) {
			try {
				// 读取下载进度
				Thread.sleep(500);
				if (fileSize > 0) {
					rate = (int) (downloadFileSize * 100 / fileSize);
					// rate = 100;
					// Log.i(TAG, "readRate:" + rate);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Message msg = progressHandler.obtainMessage();
			msg.what = 1;
			msg.arg1 = rate;
			progressHandler.sendMessage(msg);
			// progress = rate;
		}
		if (cancelled) {
			Message msg = progressHandler.obtainMessage();
			msg.what = 0;
			progressHandler.sendMessage(msg);
		}
	}
//
//	private void downloadFile(final String url) {
//		cancelled = false;
//		HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet(url);
//		HttpResponse response;
//		try {
//			createUpdateFile();
//
//			response = client.execute(get);
//			HttpEntity entity = response.getEntity();
//			fileSize = entity.getContentLength();
//			Log.i(TAG, "downloadFile --fileSize--" + fileSize);
//			InputStream is = entity.getContent();
//			FileOutputStream fileOutputStream = null;
//
//			if (is != null) {
//				fileOutputStream = new FileOutputStream(updateFile);
//				Log.i(TAG, "downloadFile --localFilePath--" + updateFile.getAbsolutePath());
//				byte[] buf = new byte[1024];
//				int ch = -1;
//				while ((!cancelled) && ((ch = is.read(buf)) != -1)) {
//					fileOutputStream.write(buf, 0, ch);
//					downloadFileSize += ch;
//				}
//			}
//			fileOutputStream.flush();
//			if (fileOutputStream != null) {
//				fileOutputStream.close();
//			}
//			Log.i(TAG, "downloadFile 下载成功");
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//			mContext.sendBroadcast(new Intent(ACTION_UPGRADE_NETWORK_DISCONNECTED));
//		} catch (IOException e) {
//			e.printStackTrace();
//			mContext.sendBroadcast(new Intent(ACTION_UPGRADE_IOEXCEPTION));
//		}
//	}

	public void downloadAppFile(final String url) {
		cancelled = false;
		HttpURLConnection conn =null;
		InputStream ins=null;
		FileOutputStream fileOutputStream = null;
		createUpdateFile();
		
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET"); // 设置请求方法, 注意: 必须大写
			conn.setConnectTimeout(60000); // 连接的超时时间
			conn.setReadTimeout(60000); // 读取的超时时间
			int responseCode = conn.getResponseCode(); // 获得服务器返回的响应码
			if (responseCode == 200) {
				fileSize = conn.getContentLength();
				ins = conn.getInputStream();
				if (ins != null) {
					fileOutputStream = new FileOutputStream(updateFile);
					byte[] buf = new byte[1024];
					int ch = -1;
					while ((!cancelled) && ((ch = ins.read(buf)) != -1)) {
						fileOutputStream.write(buf, 0, ch);
						downloadFileSize += ch;
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					Log.i(TAG, "downloadFile 下载成功");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			mContext.sendBroadcast(new Intent(ACTION_UPGRADE_NETWORK_DISCONNECTED));
		} catch (IOException e) {
			e.printStackTrace();
			mContext.sendBroadcast(new Intent(ACTION_UPGRADE_IOEXCEPTION));
		}
	}
	int pos=0;
	int total=0;
	int percent=0;
	@SuppressLint("NewApi")
	public void downloadAppFileNew(final String url) {
		
		cancelled = false;
		HttpURLConnection conn =null;
		InputStream ins=null;
		FileOutputStream fileOutputStream = null;
		createUpdateFile();
		
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET"); // 设置请求方法, 注意: 必须大写
			conn.setConnectTimeout(60000); // 连接的超时时间
			conn.setReadTimeout(60000); // 读取的超时时间
			int responseCode = conn.getResponseCode(); // 获得服务器返回的响应码
			if (responseCode == 200) {
				fileSize = conn.getContentLength();
				total=(int) (fileSize/1024);
				progressHandler.sendEmptyMessage(3);
				ins = conn.getInputStream();
				if (ins != null) {
					fileOutputStream = new FileOutputStream(updateFile);
					byte[] buf = new byte[1024];
					int ch = -1;
					while ((!cancelled) && ((ch = ins.read(buf)) != -1)) {
						fileOutputStream.write(buf, 0, ch);
//						downloadFileSize += ch;
						if(pos<=total){
							pos++;
						}
						percent=(int)(Float.parseFloat(pos+"")/total*100);
						progressHandler.sendEmptyMessage(2);
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					Log.i(TAG, "downloadFile 下载成功");
					progressHandler.sendEmptyMessage(4);
					
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			mContext.sendBroadcast(new Intent(ACTION_UPGRADE_NETWORK_DISCONNECTED));
		} catch (IOException e) {
			e.printStackTrace();
			mContext.sendBroadcast(new Intent(ACTION_UPGRADE_IOEXCEPTION));
		}
	}
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	protected void createUpdateFile() {
		try {
			if (mkFilePath(pkgPath)) {
				updateFile = new File(pkgPath + "/newApp.apk");
			}
			Log.i(TAG, "file --" + updateFile.getAbsolutePath());
			Log.i(TAG, "file --" + updateFile.exists());
			if (!(updateFile.exists())) {
				updateFile.createNewFile();
				Log.i(TAG, "creat file --" + updateFile.exists());
			}
			// 设置文件可以被其他程序读写
			boolean readable = updateFile.setReadable(true, false);
			Log.i(TAG, "file readable --" + readable);
			boolean writable = updateFile.setWritable(true, false);
			Log.i(TAG, "file writable--" + writable);
		} catch (IOException e) {
			e.printStackTrace();
			mContext.sendBroadcast(new Intent(ACTION_UPGRADE_IOEXCEPTION));
		}

	}

	/**
	 * <p>
	 * Title:mkdir
	 * </p>
	 * <p>
	 * Description: 创建path指定目录.
	 * </p>
	 * 
	 * @param path
	 */

	public static boolean mkFilePath(String path) {
		File fPath = new File(path);
		if (fPath.exists()) {
			return true;
		} else {
			if (fPath.mkdirs()) {
				Log.i("mkFilePath", "create directory [" + fPath.getName() + "] success!!!");
				return true;
			} else {
				Log.e("mkFilePath", "create directory [" + fPath.getName() + "] failed!!!");
				return false;
			}
		}
	}
	
	/**
	 * 安装apk
	 * @param file
	 */
	private void openFile(File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
        mContext.startActivity(intent);
	}

}
