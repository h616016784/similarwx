package com.android.similarwx.beans.response;

/**
 * Created by Administrator on 2018/7/14.
 */

public class RspCashUser extends BaseResponse{
    private CashUser data;

    public CashUser getData() {
        return data;
    }

    public void setData(CashUser data) {
        this.data = data;
    }

    public  static class CashUser {

        /**
         * passwd : 7c4a8d09ca3762af61e59520943dc26494f8941b
         * inviter : 25
         * birth : null
         * serviceFlg : 0
         * threeLevelCount : null
         * cashFlg : 1
         * state : 0
         * adminFlg : 0
         * modifyDate : null
         * grandpaInviter : null
         * userType : null
         * loginFlg : 1
         * id : 38
         * invitationCode : qo4rc
         * token : 1c671b86be031963aa6927d298b8ac1a
         * wechatAccount : 1
         * parentInviter : null
         * name : 2222
         * paymentPasswd : null
         * gender : 0
         * createDate : 2018-07-13 16:08:29
         * inviterFlg : 1
         * accId : 6666
         * personalitySignature : null
         * twoLevelCount : null
         * icon : null
         * verifyCode : null
         * systemFlg : 0
         * totalBalance : null
         * oneLevelCount : null
         * userChildCount : null
         * birthStr : null
         * passwdStr : null
         * alipay : 1
         * email :
         * registIp : null
         * recentLoginTime : 2018-07-13 16:18:34
         * registTime : 2018-07-13 16:08:29
         * mobile : 18888888888
         */

        private String passwd;
        private String inviter;
        private String birth;
        private int serviceFlg;
        private String threeLevelCount;
        private int cashFlg;
        private int state;
        private int adminFlg;
        private String modifyDate;
        private String grandpaInviter;
        private String userType;
        private int loginFlg;
        private String id;
        private String invitationCode;
        private String token;
        private String wechatAccount;
        private String parentInviter;
        private String name;
        private String paymentPasswd;
        private int gender;
        private String createDate;
        private int inviterFlg;
        private String accId;
        private String personalitySignature;
        private String twoLevelCount;
        private String icon;
        private String verifyCode;
        private int systemFlg;
        private String totalBalance;
        private String oneLevelCount;
        private String userChildCount;
        private String birthStr;
        private String passwdStr;
        private String alipay;
        private String email;
        private String registIp;
        private String recentLoginTime;
        private String registTime;
        private String mobile;

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getInviter() {
            return inviter;
        }

        public void setInviter(String inviter) {
            this.inviter = inviter;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public int getServiceFlg() {
            return serviceFlg;
        }

        public void setServiceFlg(int serviceFlg) {
            this.serviceFlg = serviceFlg;
        }

        public String getThreeLevelCount() {
            return threeLevelCount;
        }

        public void setThreeLevelCount(String threeLevelCount) {
            this.threeLevelCount = threeLevelCount;
        }

        public int getCashFlg() {
            return cashFlg;
        }

        public void setCashFlg(int cashFlg) {
            this.cashFlg = cashFlg;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getAdminFlg() {
            return adminFlg;
        }

        public void setAdminFlg(int adminFlg) {
            this.adminFlg = adminFlg;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        public String getGrandpaInviter() {
            return grandpaInviter;
        }

        public void setGrandpaInviter(String grandpaInviter) {
            this.grandpaInviter = grandpaInviter;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public int getLoginFlg() {
            return loginFlg;
        }

        public void setLoginFlg(int loginFlg) {
            this.loginFlg = loginFlg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
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

        public String getParentInviter() {
            return parentInviter;
        }

        public void setParentInviter(String parentInviter) {
            this.parentInviter = parentInviter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPaymentPasswd() {
            return paymentPasswd;
        }

        public void setPaymentPasswd(String paymentPasswd) {
            this.paymentPasswd = paymentPasswd;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getInviterFlg() {
            return inviterFlg;
        }

        public void setInviterFlg(int inviterFlg) {
            this.inviterFlg = inviterFlg;
        }

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getPersonalitySignature() {
            return personalitySignature;
        }

        public void setPersonalitySignature(String personalitySignature) {
            this.personalitySignature = personalitySignature;
        }

        public String getTwoLevelCount() {
            return twoLevelCount;
        }

        public void setTwoLevelCount(String twoLevelCount) {
            this.twoLevelCount = twoLevelCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public int getSystemFlg() {
            return systemFlg;
        }

        public void setSystemFlg(int systemFlg) {
            this.systemFlg = systemFlg;
        }

        public String getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(String totalBalance) {
            this.totalBalance = totalBalance;
        }

        public String getOneLevelCount() {
            return oneLevelCount;
        }

        public void setOneLevelCount(String oneLevelCount) {
            this.oneLevelCount = oneLevelCount;
        }

        public String getUserChildCount() {
            return userChildCount;
        }

        public void setUserChildCount(String userChildCount) {
            this.userChildCount = userChildCount;
        }

        public String getBirthStr() {
            return birthStr;
        }

        public void setBirthStr(String birthStr) {
            this.birthStr = birthStr;
        }

        public String getPasswdStr() {
            return passwdStr;
        }

        public void setPasswdStr(String passwdStr) {
            this.passwdStr = passwdStr;
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

        public String getRegistIp() {
            return registIp;
        }

        public void setRegistIp(String registIp) {
            this.registIp = registIp;
        }

        public String getRecentLoginTime() {
            return recentLoginTime;
        }

        public void setRecentLoginTime(String recentLoginTime) {
            this.recentLoginTime = recentLoginTime;
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
    }
}
