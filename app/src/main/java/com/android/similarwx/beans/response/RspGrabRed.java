package com.android.similarwx.beans.response;

/**
 * Created by Administrator on 2018/6/12.
 */

public class RspGrabRed extends BaseResponse {

    /**
     * data : {"thunder":false,"thunderAmount":null,"amount":null,"retCode":"9999","lucky":false,"retMsg":"玩家已结算积分，红包作废","luckAmount":null}
     */

    private GrabRedBean data;

    public GrabRedBean getData() {
        return data;
    }

    public void setData(GrabRedBean data) {
        this.data = data;
    }

    public static class GrabRedBean {
        /**
         * thunder : false
         * thunderAmount : null
         * amount : null
         * retCode : 9999
         * lucky : false
         * retMsg : 玩家已结算积分，红包作废
         * luckAmount : null
         */

        private boolean thunder;
        private Object thunderAmount;
        private Object amount;
        private String retCode;
        private boolean lucky;
        private String retMsg;
        private Object luckAmount;

        public boolean isThunder() {
            return thunder;
        }

        public void setThunder(boolean thunder) {
            this.thunder = thunder;
        }

        public Object getThunderAmount() {
            return thunderAmount;
        }

        public void setThunderAmount(Object thunderAmount) {
            this.thunderAmount = thunderAmount;
        }

        public Object getAmount() {
            return amount;
        }

        public void setAmount(Object amount) {
            this.amount = amount;
        }

        public String getRetCode() {
            return retCode;
        }

        public void setRetCode(String retCode) {
            this.retCode = retCode;
        }

        public boolean isLucky() {
            return lucky;
        }

        public void setLucky(boolean lucky) {
            this.lucky = lucky;
        }

        public String getRetMsg() {
            return retMsg;
        }

        public void setRetMsg(String retMsg) {
            this.retMsg = retMsg;
        }

        public Object getLuckAmount() {
            return luckAmount;
        }

        public void setLuckAmount(Object luckAmount) {
            this.luckAmount = luckAmount;
        }
    }
}
