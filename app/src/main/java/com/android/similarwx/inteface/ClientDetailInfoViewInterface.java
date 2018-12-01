package com.android.similarwx.inteface;

import com.android.similarwx.beans.User;

/**
 * Created by hanhuailong on 2018/6/27.
 */

public interface ClientDetailInfoViewInterface extends ViewInterface {
    void refreshUserInfo(User user,int flag);
    void refreshUpdateUser();
    void refreshUpdateUserStatus(String userStatus);
    void refreshDeleteUser();
}
