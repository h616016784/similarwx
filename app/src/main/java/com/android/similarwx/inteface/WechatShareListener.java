package com.android.similarwx.inteface;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public interface WechatShareListener {
    void shareToWechat(boolean circle);

    void onShareInvalid();
}
