package com.android.similarwx.inteface;

import com.android.similarwx.beans.User;

/**
 * Created by hanhuailong on 2018/7/30.
 */

public interface WxViewInterface extends ViewInterface {
    void refreshWxLogin(User user);
    void refreshWxUpdate(User user);
}
