package com.android.similarwx.inteface;

import com.android.similarwx.beans.SubUser;

import java.util.List;

/**
 * Created by Administrator on 2018/7/14.
 */

public interface SubUsersViewInterface extends ViewInterface {
    void refreshSubUsers(List<SubUser> subUsers);
}
