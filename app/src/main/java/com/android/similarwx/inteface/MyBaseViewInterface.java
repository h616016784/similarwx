package com.android.similarwx.inteface;

import com.android.similarwx.beans.User;

/**
 * Created by hanhuailong on 2018/6/14.
 */

public interface MyBaseViewInterface extends ViewInterface {
    void reFreshUser(User user);
}
