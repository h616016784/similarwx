package com.android.outbaselibrary.utils;

import android.os.Environment;
import android.text.TextUtils;


import com.android.outbaselibrary.primary.AppContext;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AudioUtil {

    private static final String TAG = "AudioUtils";

    private static String mAudioRootDir = "/mnt/sdcard/uschool/cache";

    private static String mAudioRootDownLoad = "/data/data/com.uschool/cache";

    private static String mSdcardDir = "/mnt/sdcard/uschool";

    public static final String TYPE_SPX = ".spx";

    public static final String TYPE_AMR = ".amr";

    public static final String TYPE_AAC = ".aac";

    public static final String TYPE_WAV = ".wav";

    public static final String TYPE_MP3 = ".mp3";

    public static final String INTENT_TYPE_MP3 = "audio/mpeg";

    public static final String INTENT_TYPE_EXT_MP3 = "audio/ext-mpeg";

    public static final String INTENT_TYPE_WAV = "audio/x-wav";

    public static String getAudioDirRoot() {
        return mAudioRootDir;
    }

    public static void setAudioRootDir(String dir) {
        mAudioRootDir = dir;
    }

    static {
        AudioUtil.tryInit();
    }

    public static File getDiskCacheDir(String uniqueName) throws IOException {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        //        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
        //                .getExternalStorageState()) || !Utils.isExternalStorageRemovable() ? Utils
        //                .getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();

        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !FileUtil.isExternalStorageRemovable()) {
            File file = FileUtil.getExternalCacheDir(AppContext.getContext());
            if (file == null) {
                file = AppContext.getContext().getCacheDir();
            }
            if (file == null) {
                throw new IOException("Unable to open storage");
            }
            cachePath = file.getPath();
        } else {
            cachePath = AppContext.getContext().getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }

    public static void tryInit() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !FileUtil.isExternalStorageRemovable()) {
            String cachePath = FileUtil.getCachePath();
            if (!TextUtils.isEmpty(cachePath)) {
                mAudioRootDownLoad = cachePath;
            } else {
                mAudioRootDownLoad = AppContext.getContext().getCacheDir().getPath();
            }
            File f = FileUtil.getExternalAppDir(AppContext.getContext());
            mSdcardDir = f.getPath();
        } else {
            mAudioRootDownLoad = AppContext.getContext().getCacheDir().getPath();
        }
        try {
            AudioUtil.setAudioRootDir(AudioUtil.getDiskCacheDir("audios")
                    .getPath());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static boolean openCache(File cacheDir) {
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }

        if (cacheDir.isDirectory() && cacheDir.canWrite()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public static File getAudioFileInfoFile(String uri) {
        File file = new File(mAudioRootDir, getAudioFileInfoPath(uri));
        return file;
    }

    public static String getAudioFileInfoPath(String uri) {
        return getAudioCacheFileName(uri) + ".dat";
    }

    public static String getAudioCacheFileName(String uri) {
        return "cache_" + AudioUtil.getMd5(uri);
    }

    public static File getOfflineDownLoadingDir() {
        tryInit();
        File file = new File(mAudioRootDownLoad + File.separator + ".downloading");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    /**
     * 得到通过StreamProxy下载的缓存Path,下载成功后move到
     *
     * @param urlString
     * @return
     */
    public static String getDownLoadingPath(String urlString) {
        File file = getOfflineDownLoadingDir();
        String path = file.getPath() + File.separator + "down_" + AudioUtil.getMd5(urlString)
                + ".dat";
        return path;
    }

    public static File getOfflineAudioParentDir() {
        tryInit();
        return new File(mAudioRootDownLoad);
    }

    /**
     * 保存json文件目录
     *
     * @return
     */
    public static File getOfflineJsonDir() {
        tryInit();
        File file = new File(mAudioRootDownLoad + File.separator + "offlines_v2");

        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getOfflineDownLoadedDir() {
        tryInit();
        File file = new File(mAudioRootDownLoad + File.separator + ".downloaded");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    public static File getPublicOfflineDownLoadedDir() {
        tryInit();
        File file = new File(mSdcardDir + File.separator + "offline");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    public static File getPublicCacheDir() {
        tryInit();
        File file = new File(mSdcardDir + File.separator + "cache");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    /**
     * @param url
     * @return
     */
    public static File getDownloadedFile(String url) {
        File file = getOfflineDownLoadedDir();
        String path = file.getPath() + File.separator + "down_" + AudioUtil.getMd5(url) + ".dat";
        file = new File(path);
        if (file.exists()) {
            return file;
        }
        file = new File(getPublicOfflineDownLoadedDir(), "download_" + AudioUtil.getMd5(url)
                + ".dat");
        return file;
    }

    public static String getMd5(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return text;
    }

}
