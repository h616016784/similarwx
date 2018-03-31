package com.android.similarwx.inteface;

import com.android.similarwx.beans.User;

/**
 * Created by Administrator on 2018/3/31.
 */

public interface RegisterViewInterface extends ViewInterface{
    void loginScucces(User user);
    void getCodeScucces(Integer code);
}
