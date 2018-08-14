package com.android.similarwx.utils.audio;

import android.content.Context;

import com.netease.nim.uikit.common.media.audioplayer.BaseAudioControl;

/**
 * Created by hanhuailong on 2018/8/14.
 */

public class AudioPlayer extends BaseAudioControl<String> {
    public AudioPlayer(Context context, boolean suffix) {
        super(context, suffix);
    }

    @Override
    public void startPlayAudioDelay(long delayMillis, String s, AudioControlListener audioControlListener, int audioStreamType) {

    }

    @Override
    public String getPlayingAudio() {
        return null;
    }
}
