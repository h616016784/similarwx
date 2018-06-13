package com.android.similarwx.beans.response;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/13.
 */

public class RspService extends BaseResponse{
    /**
     * data : {"retCode":null,"count":2,"treeNode":null,"resUsersDetailList":[{"personalitySignature":null,"inviter":null,"icon":null,"passwd":"da39a3ee5e6b4b0d3255bfef95601890afd80709","birth":null,"totalBalance":null,"state":null,"userChildCount":null,"modifyDate":"2018-06-13 10:43:46","userType":3,"loginFlg":null,"birthStr":null,"id":"25","passwdStr":null,"invitationCode":"","alipay":"ffddfg","email":"","token":null,"wechatAccount":"wwewerw","name":"李世明","gender":null,"createDate":"2018-06-13 10:43:46","recentLoginTime":"2018-06-13 10:43:46","registIp":null,"registTime":"2018-06-13 10:43:46","mobile":"13977644738","accId":"liyx588"},{"personalitySignature":null,"inviter":null,"icon":null,"passwd":"da39a3ee5e6b4b0d3255bfef95601890afd80709","birth":"2018-06-11 00:00:00","totalBalance":null,"state":0,"userChildCount":null,"modifyDate":"2018-06-13 10:44:05","userType":3,"loginFlg":0,"birthStr":null,"id":"29","passwdStr":null,"invitationCode":"qowfi","alipay":"123456@qq.com","email":"123456@qq.com","token":"089e5b1d6a8cdaed338b7ada8a4b1d8c","wechatAccount":"m924508034","name":"系统用户","gender":0,"createDate":"2018-06-13 10:44:05","recentLoginTime":"2018-06-13 10:44:05","registIp":null,"registTime":"2018-06-13 10:44:05","mobile":"13601047552","accId":"menglidong"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * retCode : null
         * count : 2
         * treeNode : null
         * resUsersDetailList : [{"personalitySignature":null,"inviter":null,"icon":null,"passwd":"da39a3ee5e6b4b0d3255bfef95601890afd80709","birth":null,"totalBalance":null,"state":null,"userChildCount":null,"modifyDate":"2018-06-13 10:43:46","userType":3,"loginFlg":null,"birthStr":null,"id":"25","passwdStr":null,"invitationCode":"","alipay":"ffddfg","email":"","token":null,"wechatAccount":"wwewerw","name":"李世明","gender":null,"createDate":"2018-06-13 10:43:46","recentLoginTime":"2018-06-13 10:43:46","registIp":null,"registTime":"2018-06-13 10:43:46","mobile":"13977644738","accId":"liyx588"},{"personalitySignature":null,"inviter":null,"icon":null,"passwd":"da39a3ee5e6b4b0d3255bfef95601890afd80709","birth":"2018-06-11 00:00:00","totalBalance":null,"state":0,"userChildCount":null,"modifyDate":"2018-06-13 10:44:05","userType":3,"loginFlg":0,"birthStr":null,"id":"29","passwdStr":null,"invitationCode":"qowfi","alipay":"123456@qq.com","email":"123456@qq.com","token":"089e5b1d6a8cdaed338b7ada8a4b1d8c","wechatAccount":"m924508034","name":"系统用户","gender":0,"createDate":"2018-06-13 10:44:05","recentLoginTime":"2018-06-13 10:44:05","registIp":null,"registTime":"2018-06-13 10:44:05","mobile":"13601047552","accId":"menglidong"}]
         */

        private String retCode;
        private int count;
        private String treeNode;
        private String retMsg;
        private List<ResUsersDetailListBean> resUsersDetailList;

        public String getRetCode() {
            return retCode;
        }

        public void setRetCode(String retCode) {
            this.retCode = retCode;
        }

        public String getRetMsg() {
            return retMsg;
        }

        public void setRetMsg(String retMsg) {
            this.retMsg = retMsg;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTreeNode() {
            return treeNode;
        }

        public void setTreeNode(String treeNode) {
            this.treeNode = treeNode;
        }

        public List<ResUsersDetailListBean> getResUsersDetailList() {
            return resUsersDetailList;
        }

        public void setResUsersDetailList(List<ResUsersDetailListBean> resUsersDetailList) {
            this.resUsersDetailList = resUsersDetailList;
        }

        public static class ResUsersDetailListBean {
            /**
             * personalitySignature : null
             * inviter : null
             * icon : null
             * passwd : da39a3ee5e6b4b0d3255bfef95601890afd80709
             * birth : null
             * totalBalance : null
             * state : null
             * userChildCount : null
             * modifyDate : 2018-06-13 10:43:46
             * userType : 3
             * loginFlg : null
             * birthStr : null
             * id : 25
             * passwdStr : null
             * invitationCode :
             * alipay : ffddfg
             * email :
             * token : null
             * wechatAccount : wwewerw
             * name : 李世明
             * gender : null
             * createDate : 2018-06-13 10:43:46
             * recentLoginTime : 2018-06-13 10:43:46
             * registIp : null
             * registTime : 2018-06-13 10:43:46
             * mobile : 13977644738
             * accId : liyx588
             */

            private String personalitySignature;
            private String inviter;
            private String icon;
            private String passwd;
            private String birth;
            private String totalBalance;
            private String state;
            private String userChildCount;
            private String modifyDate;
            private int userType;
            private String loginFlg;
            private String birthStr;
            private String id;
            private String passwdStr;
            private String invitationCode;
            private String alipay;
            private String email;
            private String token;
            private String wechatAccount;
            private String name;
            private String gender;
            private String createDate;
            private String recentLoginTime;
            private String registIp;
            private String registTime;
            private String mobile;
            private String accId;

            public String getPersonalitySignature() {
                return personalitySignature;
            }

            public void setPersonalitySignature(String personalitySignature) {
                this.personalitySignature = personalitySignature;
            }

            public String getInviter() {
                return inviter;
            }

            public void setInviter(String inviter) {
                this.inviter = inviter;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPasswd() {
                return passwd;
            }

            public void setPasswd(String passwd) {
                this.passwd = passwd;
            }

            public String getBirth() {
                return birth;
            }

            public void setBirth(String birth) {
                this.birth = birth;
            }

            public String getTotalBalance() {
                return totalBalance;
            }

            public void setTotalBalance(String totalBalance) {
                this.totalBalance = totalBalance;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getUserChildCount() {
                return userChildCount;
            }

            public void setUserChildCount(String userChildCount) {
                this.userChildCount = userChildCount;
            }

            public String getModifyDate() {
                return modifyDate;
            }

            public void setModifyDate(String modifyDate) {
                this.modifyDate = modifyDate;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public String getLoginFlg() {
                return loginFlg;
            }

            public void setLoginFlg(String loginFlg) {
                this.loginFlg = loginFlg;
            }

            public String getBirthStr() {
                return birthStr;
            }

            public void setBirthStr(String birthStr) {
                this.birthStr = birthStr;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPasswdStr() {
                return passwdStr;
            }

            public void setPasswdStr(String passwdStr) {
                this.passwdStr = passwdStr;
            }

            public String getInvitationCode() {
                return invitationCode;
            }

            public void setInvitationCode(String invitationCode) {
                this.invitationCode = invitationCode;
            }

            public String getAlipay() {
                return alipay;
            }

            public void setAlipay(String alipay) {
                this.alipay = alipay;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getWechatAccount() {
                return wechatAccount;
            }

            public void setWechatAccount(String wechatAccount) {
                this.wechatAccount = wechatAccount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getRecentLoginTime() {
                return recentLoginTime;
            }

            public void setRecentLoginTime(String recentLoginTime) {
                this.recentLoginTime = recentLoginTime;
            }

            public String getRegistIp() {
                return registIp;
            }

            public void setRegistIp(String registIp) {
                this.registIp = registIp;
            }

            public String getRegistTime() {
                return registTime;
            }

            public void setRegistTime(String registTime) {
                this.registTime = registTime;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getAccId() {
                return accId;
            }

            public void setAccId(String accId) {
                this.accId = accId;
            }
        }
    }
}
