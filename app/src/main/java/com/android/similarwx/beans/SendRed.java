package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/5.
 */

public class SendRed extends BaseBean{

    private String type;
    private SendRedBean data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SendRedBean getData() {
        return data;
    }

    public void setData(SendRedBean data) {
        this.data = data;
    }

    public static class SendRedBean extends BaseBean{
        private String title;//标题
        private String cotent;//内容
        private String url;//图片地址
        private String redPacId;//红包编号
        private String amount;//发包金额
        private String time;//发包时间
        private String shouqi;//手气
        private String requestNum;//请求编号，每个用户，保证唯一
        private String userId;//发包用户编号（本地服务器）
        private String myUserId;//云信的accid
        private String myGroupId;//群组编号（云信）
        private String groupId;//群组编号（本地服务器）
        private String type;//发包类型，MINE：扫雷红包；LUCK：拼手气红包
        private String thunder;//中雷数字
        private String click;//0表示未点击，1表示点击
        private String count;//数量，type为LUCK时必填

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCotent() {
            return cotent;
        }

        public void setCotent(String cotent) {
            this.cotent = cotent;
        }

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getThunder() {
            return thunder;
        }

        public void setThunder(String thunder) {
            this.thunder = thunder;
        }

        public String getMyUserId() {
            return myUserId;
        }

        public void setMyUserId(String myUserId) {
            this.myUserId = myUserId;
        }

        public String getMyGroupId() {
            return myGroupId;
        }

        public void setMyGroupId(String myGroupId) {
            this.myGroupId = myGroupId;
        }

        public String getRedPacId() {
            return redPacId;
        }

        public void setRedPacId(String redPacId) {
            this.redPacId = redPacId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getShouqi() {
            return shouqi;
        }

        public void setShouqi(String shouqi) {
            this.shouqi = shouqi;
        }

        public String getRequestNum() {
            return requestNum;
        }

        public void setRequestNum(String requestNum) {
            this.requestNum = requestNum;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
