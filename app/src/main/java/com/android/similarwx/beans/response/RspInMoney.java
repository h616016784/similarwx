package com.android.similarwx.beans.response;

import com.android.similarwx.beans.BaseBean;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RspInMoney extends BaseResponse{
    private InMoneyBean data;

    public InMoneyBean getData() {
        return data;
    }

    public void setData(InMoneyBean data) {
        this.data = data;
    }

    public class InMoneyBean extends BaseBean{
        private String userID;
        private String  money;
        private String Price;
        private String pay_id;
        private String endTime;
        private String qrcode;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getPay_id() {
            return pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
    }
}
