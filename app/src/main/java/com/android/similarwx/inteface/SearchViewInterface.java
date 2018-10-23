package com.android.similarwx.inteface;

import com.android.similarwx.beans.GroupUser;
import com.android.similarwx.beans.User;

/**
 * Created by hanhuailong on 2018/8/1.
 */

public interface SearchViewInterface extends ViewInterface {
    void refreshSearchUser();
    void refreshSearchUser(User user);

    void refreshSearchUser(GroupUser data);
}
