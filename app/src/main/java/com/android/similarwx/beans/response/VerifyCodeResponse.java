package com.android.similarwx.beans.response;

/**
 * Created by hanhuailong on 2018/9/28.
 */

public class VerifyCodeResponse extends BaseResponse{
    public VerifyCodeBean getData() {
        return data;
    }

    public void setData(VerifyCodeBean data) {
        this.data = data;
    }

    private VerifyCodeBean data;
    public class VerifyCodeBean{
        private String verifyCode;

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }
    }
}
