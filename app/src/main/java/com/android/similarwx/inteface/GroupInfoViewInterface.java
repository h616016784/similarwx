package com.android.similarwx.inteface;

import com.android.similarwx.beans.GroupUser;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/4.
 */

public interface GroupInfoViewInterface extends ViewInterface {
    void refreshUserlist(GroupUser groupUser);
    void refreshDeleteGroup();
}
