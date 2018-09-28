package com.android.similarwx.inteface;

import com.android.similarwx.beans.response.VerifyCodeResponse;

/**
 * Created by Administrator on 2018/7/8.
 */

public interface PhoneVerifyViewInterface extends ViewInterface {
    void refreshGettMobileVerifyCode(VerifyCodeResponse.VerifyCodeBean bean);
}
