package com.android.similarwx.widget.emoji;

public interface IEmoticonSelectedListener {
    void onEmojiSelected(String key);

    void onStickerSelected(String categoryName, String stickerName);
}
