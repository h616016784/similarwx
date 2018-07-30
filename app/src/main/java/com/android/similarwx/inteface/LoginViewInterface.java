package com.android.similarwx.inteface;

import com.android.similarwx.beans.User;

/**
 * Created by Administrator on 2018/3/31.
 */

public interface LoginViewInterface extends ViewInterface{
    void loginScucces(User user);
    void logoutScucces(User user);
    void refreshTotalBalance(User user);
}
