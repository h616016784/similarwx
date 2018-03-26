package com.android.similarwx.inteface;

import android.view.KeyEvent;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public interface OnInterceptKeyListener {
    public boolean isInterceptKeyEvent(KeyEvent event);

    public boolean isInterceptBackEvent();
}
