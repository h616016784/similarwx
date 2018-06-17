package com.android.similarwx.inteface;

import com.android.similarwx.beans.RedDetailBean;
import com.android.similarwx.beans.SendRed;
import com.android.similarwx.beans.response.RspGrabRed;

/**
 * Created by hanhuailong on 2018/5/30.
 */

public interface MiViewInterface extends ViewInterface{
    void reFreshCustemRed(SendRed data);
    void grabRed(RspGrabRed.GrabRedBean bean);
    void canGrab(RspGrabRed.GrabRedBean bean);
}
