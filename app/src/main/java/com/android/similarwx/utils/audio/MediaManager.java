package com.android.similarwx.utils.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.*;

import java.io.IOException;

/**
 * Created by hanhuailong on 2018/5/28.
 */

public class MediaManager {
    private static String TAG="MediaManager";
    private static MediaPlayer mPlayer;
    private static boolean isPause;

    public static void playSendMessageSoundDefault(Context context,MediaPlayer.OnCompletionListener onCompletionListener){
        AssetManager am = context.getAssets();
        try {
            AssetFileDescriptor afd = am.openFd("sent_message.mp3");
            playSendMessageSound(context,afd,onCompletionListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean playSendMessageSound(Context context,AssetFileDescriptor afd ,MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            // 保险起见，设置报错监听
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mPlayer.reset();
                    return false;
                }
            });
        } else {
            mPlayer.reset();// 就回复
        }

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnCompletionListener(onCompletionListener);
            mPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean playSound(String filePathString,
                                    MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            // 保险起见，设置报错监听
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mPlayer.reset();
                    return false;
                }
            });
        } else {
            mPlayer.reset();// 就回复
        }

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnCompletionListener(onCompletionListener);
            mPlayer.setDataSource(filePathString);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // 停止函数
    public static void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
        }
    }

    // 继续
    public static void resume() {
        if (mPlayer != null && isPause) {
            mPlayer.start();
            isPause = false;
        }
    }

    public static void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    // 播放音频的代码
    public void playMusic(String path) {
        // 当前音乐是否播放mediaPlayer.isPlaying();
        try {
            mPlayer.reset();
            mPlayer.setDataSource(path);
            mPlayer.prepare(); // might take long! (for buffering,
            mPlayer.start();
            mPlayer.setLooping(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
