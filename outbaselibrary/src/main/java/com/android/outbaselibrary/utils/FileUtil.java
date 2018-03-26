package com.android.outbaselibrary.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.android.outbaselibrary.R;
import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.primary.Log;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static final String APP_FOLDER_NAME = "outlibraryfile";
    private static final String TAG = "FileUtil";
    private static String appFolderPath = null; // 此程序存放文件的文件夹路径

    public static File generateTempImageFile(Context context) {
        return new File(context.getCacheDir(), "tmp_photo_"
                + String.valueOf(System.currentTimeMillis()) + ".jpg");
    }

    public static File generateTempAudioFile(String recordAudioSuffix) {
        return new File(getTempAudioFilePath(recordAudioSuffix));
    }

    public static String getAudioFilePath(Context context, String fileName, String recordAudioSuffix) {
        return context.getCacheDir() + File.separator + fileName + recordAudioSuffix;
    }

    private static String getTempAudioFilePath(String recordAudioSuffix) {
        return AppContext.getContext().getFilesDir() + File.separator + "tmp_audio_"
                + String.valueOf(System.currentTimeMillis()) + recordAudioSuffix;
    }

    public static File generateFilterAudioFile(Context context, String filterName,
                                               String recordAudioSuffix) {
        return new File(getFilterAudioFilePath(context, filterName, recordAudioSuffix));
    }

    public static String getFilterAudioFilePath(Context context, String filterName,
                                                String recordAudioSuffix) {
        String audioFolderPath = getAudioFolderPath(context);
        File dir = new File(audioFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return audioFolderPath + File.separator + filterName + recordAudioSuffix;
    }

    public static String getAudioFolderPath(Context context) {
        return context.getFilesDir() + File.separator + "andios";
    }

    public static File getTempCacheImageFile() {
        return new File(getCachePath(), "tmp_photo_" + String.valueOf(System.currentTimeMillis())
                + ".jpg");
    }

    // 判断SD卡是否存在
    public static boolean isSdcardValid() {

        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED)) {
            // 获得存储卡的路径
            String sdcardPath = Environment.getExternalStorageDirectory().getPath();
            if (!sdcardPath.endsWith(File.separator)) {
                sdcardPath = sdcardPath + File.separator;
            }
            // 拼接本程序用到的目录
            if (Log.DEBUG) {
                Log.d("FileUtils", "isSdcardValid(), isSdcardValid=" + sdcardPath);
            }
            SaveReport.infoLog("sdcard valid, sdcard path", sdcardPath);
            initAppFolderPath(sdcardPath);
            return true;

        } else {
            // 判断SD卡是否正在使用文件共享（U盘模式挂载）
            String msg = status.equals(Environment.MEDIA_SHARED) ?
                    "你的SD卡正在用于其他用途，请在通知中选择“关闭USB存储设备" :"你的设备没有安装SD卡";
            Toaster.toastShort(msg);
            SaveReport.infoLog("sdcard invalid, sdcard status", status);
            return false;
        }
    }

    public static boolean isSdcardValidNoToast() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        } else {
            // 获得存储卡的路径
            String sdcardPath = Environment.getExternalStorageDirectory().getPath();
            if (!sdcardPath.endsWith(File.separator)) {
                sdcardPath = sdcardPath + File.separator;
            }
            // 拼接本程序用到的目录
            initAppFolderPath(sdcardPath);
            return true;
        }
    }

    /**
     * 拼接本程序用到的目录
     *
     * @param sdcardPath
     */
    private static void initAppFolderPath(String sdcardPath) {
        appFolderPath = sdcardPath + APP_FOLDER_NAME + File.separator;
    }

    public static String getAppFolderPath() {
        if (createFolder(appFolderPath)) {
            return appFolderPath;
        } else {
            return "/";
        }
    }

    public static boolean isFileExist(String filePath) {
        try {
            return new File(filePath).exists();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createFolder(String folderPath) {

        boolean success = false;
        try {
            File folder = new File(folderPath);
            if (folder.exists() && folder.isDirectory()) {
                success = true;
            } else {
                success = folder.mkdirs();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void createFileFolder(String filePath) {
        try {
            new File(filePath).getParentFile().mkdirs();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String filePath) {
        try {
            return new File(filePath).delete();
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        try {
            byte[] bytes = new byte[SystemUtil.IO_BUFFER_SIZE];
            int i = inputStream.read(bytes);
            if (i > 0) {
                outputStream.write(bytes, 0, i);
            }
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    public static boolean copy(File resFile, File desFile) {
        boolean result = false;
        if (!resFile.exists()) {
            return result;
        }
        FileOutputStream fos = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(resFile);
            bis = new BufferedInputStream(fis);
            fos = new FileOutputStream(desFile);
            if (!desFile.getParentFile().exists()) {
                desFile.getParentFile().mkdirs();
            }
            byte[] buffer = new byte[SystemUtil.IO_BUFFER_SIZE];
            int lg = -1;
            while ((lg = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, lg);
            }
            fos.flush();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 删除指定文件夹中的文件,忽略文件夹
     */
    public static boolean cleanDirectory(String folderPath) {
        if (TextUtils.isEmpty(folderPath)) {
            return false;
        }

        return cleanDirectory(new File(folderPath));
    }

    public static boolean cleanDirectory(File folderPath) {

        try {
            File[] files = folderPath.listFiles();

            if (files != null && files.length > 0) {
                for (File tempFile : files) {
                    if (!tempFile.isDirectory()) {
                        tempFile.delete();
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean cleanDirectoryContainSonDir(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        try {
            if (!file.isDirectory()) {
                file.delete();
                return true;
            }
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File tempFile : files) {
                    if (tempFile.isDirectory()) {
                        cleanDirectoryContainSonDir(tempFile);
                    } else {
                        tempFile.delete();
                    }
                }
            }
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件夹大小
     * *
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static long getStorageAvailableSize() {
        File storagefile = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !isExternalStorageRemovable()) {
            storagefile = Environment.getExternalStorageDirectory();

            if (storagefile == null) {
                storagefile = Environment.getRootDirectory();
            }

        } else {
            storagefile = Environment.getRootDirectory();
        }

        StatFs sf = new StatFs(storagefile.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        return availCount * blockSize;

    }

    public static void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }

    public static String getCachePath() {

        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !isExternalStorageRemovable()) {
            File file = getExternalCacheDir(AppContext.getContext());
            if (file == null) {
                file = AppContext.getContext().getCacheDir();
            }
            if (file == null) {
                return null;
            }
            cachePath = file.getPath();

        } else {
            cachePath = AppContext.getContext().getCacheDir().getPath();
        }

        return cachePath;
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    @TargetApi(9)
    public static long getUsableSpace(File path) {
        if (SystemUtil.hasGingerbread()) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    /**
     * 删除File目录下的文件
     *
     * @param fileName
     */
    public static void deleteTemp(String fileName) {

        try {
            File file = AppContext.getContext().getFileStreamPath(fileName);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card),
     * false otherwise.
     */
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
        if (SystemUtil.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
        final String cacheDir = "/uschool/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * 获取啪啪的根存储目录
     *
     * @param context
     * @return
     */
    public static File getExternalAppDir(Context context) {
        final String cacheDir = "/uschool/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * 在4.0以前版本调用的
     *
     * @param context
     * @return
     */
    public static File getExternalOldAppDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName();
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static void moveDir(File fromDir, File toDir) {
        long time = System.currentTimeMillis();
        if (!fromDir.exists() || !fromDir.isDirectory()) {
            return;
        }
        if (!toDir.exists()) {
            toDir.mkdirs();
        }

        for (File file : fromDir.listFiles()) {
            if (file.isFile()) {
                File newFile = new File(toDir + File.separator + file.getName());
                if (!newFile.exists()) {
                    file.renameTo(newFile);
                }
            } else if (file.isDirectory()) {
                moveDir(new File(fromDir.getPath() + File.separator + file.getName()), new File(
                        toDir.getPath() + File.separator + file.getName()));
            }
        }
        fromDir.delete();
        if (Log.DEBUG) {
            Log.i(TAG, "move dir=" + fromDir + " time：" + (System.currentTimeMillis() - time));
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImagePath(Uri uri) {
        String scheme = uri.getScheme();
        String imagePath = uri.getPath();
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            //直接就是uri.getPath
        } else if (SystemUtil.hasKitkat()
                && DocumentsContract.isDocumentUri(AppContext.getContext(), uri)) {
            try {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String[] column = {MediaStore.Images.Media.DATA};
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = AppContext
                        .getContext()
                        .getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
                                new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    imagePath = cursor.getString(columnIndex);
                }
                cursor.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            //这里只处理图片
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = AppContext.getContext().getContentResolver()
                    .query(uri, proj, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                imagePath = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            }
            if (cursor != null) {
                cursor.close();
            }

        }
        return imagePath;
    }

    public static File generateAppImageFile() {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "uschool");

        if ((!file.exists()) && (!file.mkdirs())) {
            Log.d(TAG, "failed to create directory");
        }

        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return new File(file.getPath() + File.separator + "IMG_" + date + ".jpg");
    }

    public static File generateAppImageDir() {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "uschool");

        if ((!file.exists()) && (!file.mkdirs())) {
            Log.d(TAG, "failed to create directory");
        }
        return new File(file.getPath());
    }

    public static File generateOfflineImageFile(String url) {
        return new File(getOfflineImagePath(url));
    }

    public static String getOfflineImagePath(String url) {
        return AudioUtil.getPublicOfflineDownLoadedDir().getPath() + File.separator
                + StringUtil.stringToMD5(url) + ".jpg";
    }

}
