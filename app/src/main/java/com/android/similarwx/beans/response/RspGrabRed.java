package com.android.similarwx.beans.response;

import com.android.similarwx.beans.BaseBean;

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

    public static class GrabRedBean extends BaseBean{
      /*  *
         * thunder : false
         * thunderAmount : null
         * amount : null
         * retCode : 9999
         * lucky : false
         * retMsg : 玩家已结算积分，红包作废
         * luckAmount : null*/


        private String thunder;
        private String thunderAmount;
        private String amount;
        private String lucky;
        private String luckAmount;

        public String getThunder() {
            return thunder;
        }

        public void setThunder(String thunder) {
            this.thunder = thunder;
        }

        public String getThunderAmount() {
            return thunderAmount;
        }

        public void setThunderAmount(String thunderAmount) {
            this.thunderAmount = thunderAmount;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getLucky() {
            return lucky;
        }

        public void setLucky(String lucky) {
            this.lucky = lucky;
        }

        public String getLuckAmount() {
            return luckAmount;
        }

        public void setLuckAmount(String luckAmount) {
            this.luckAmount = luckAmount;
        }
    }
}
