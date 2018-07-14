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

        /**
         * userID : 42993
         * money : 1.00
         * price : 1.00
         * chart : utf-8
         * pay_id : 28
         * type : 1
         * call :
         * trueID : 42993
         * tag : 0
         * qr_user : 0
         * come : 2
         * trade_no : 115315622811429934629373442
         * endTime : 1531562641
         * order_id : 120569731432
         * status : 0
         * msg : ok
         * mode : 0
         * mobile : 0
         * qrcode : http://codepay.fateqq.com:52888/qr/42993/1/1/0_0.png
         * kfqq :
         * https : 0
         * serverTime : 1531562281
         * notiry_key : 15cf97370f5fa4d0a3d55b97961d4d50
         */

        private int userID;
        private String money;
        private String price;
        private String chart;
        private String pay_id;
        private int type;
        private String call;
        private int trueID;
        private String tag;
        private int qr_user;
        private int come;
        private String trade_no;
        private int endTime;
        private String order_id;
        private int status;
        private String msg;
        private int mode;
        private int mobile;
        private String qrcode;
        private String kfqq;
        private int https;
        private int serverTime;
        private String notiry_key;

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getChart() {
            return chart;
        }

        public void setChart(String chart) {
            this.chart = chart;
        }

        public String getPay_id() {
            return pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCall() {
            return call;
        }

        public void setCall(String call) {
            this.call = call;
        }

        public int getTrueID() {
            return trueID;
        }

        public void setTrueID(int trueID) {
            this.trueID = trueID;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getQr_user() {
            return qr_user;
        }

        public void setQr_user(int qr_user) {
            this.qr_user = qr_user;
        }

        public int getCome() {
            return come;
        }

        public void setCome(int come) {
            this.come = come;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public int getMobile() {
            return mobile;
        }

        public void setMobile(int mobile) {
            this.mobile = mobile;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getKfqq() {
            return kfqq;
        }

        public void setKfqq(String kfqq) {
            this.kfqq = kfqq;
        }

        public int getHttps() {
            return https;
        }

        public void setHttps(int https) {
            this.https = https;
        }

        public int getServerTime() {
            return serverTime;
        }

        public void setServerTime(int serverTime) {
            this.serverTime = serverTime;
        }

        public String getNotiry_key() {
            return notiry_key;
        }

        public void setNotiry_key(String notiry_key) {
            this.notiry_key = notiry_key;
        }
    }
}
