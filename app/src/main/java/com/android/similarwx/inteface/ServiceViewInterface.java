package com.android.similarwx.inteface;

import com.android.similarwx.beans.response.RspService;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/13.
 */

public interface ServiceViewInterface extends ViewInterface {
    void serviceRefresh(List<RspService.DataBean.ResUsersDetailListBean> resUsersDetailList);
}
