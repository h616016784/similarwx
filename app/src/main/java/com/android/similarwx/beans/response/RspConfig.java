package com.android.similarwx.beans.response;

/**
 * Created by Administrator on 2018/8/5.
 */

public class RspConfig extends BaseResponse{
    private ConfigBean data;

    public ConfigBean getData() {
        return data;
    }

    public void setData(ConfigBean data) {
        this.data = data;
    }

    public class ConfigBean{
        /**
         * transfer : 1
         * androidDownloadUrl : http://www.qiakey.com
         * appVersion : 1
         * personChat : 0
         * iosDownloadUrl : http://www.qiakey.com
         */

        private String transfer;
        private String androidDownloadUrl;
        private int appVersion;
        private String personChat;
        private String iosDownloadUrl;

        public String getTransfer() {
            return transfer;
        }

        public void setTransfer(String transfer) {
            this.transfer = transfer;
        }

        public String getAndroidDownloadUrl() {
            return androidDownloadUrl;
        }

        public void setAndroidDownloadUrl(String androidDownloadUrl) {
            this.androidDownloadUrl = androidDownloadUrl;
        }

        public int getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(int appVersion) {
            this.appVersion = appVersion;
        }

        public String getPersonChat() {
            return personChat;
        }

        public void setPersonChat(String personChat) {
            this.personChat = personChat;
        }

        public String getIosDownloadUrl() {
            return iosDownloadUrl;
        }

        public void setIosDownloadUrl(String iosDownloadUrl) {
            this.iosDownloadUrl = iosDownloadUrl;
        }
    }
}
