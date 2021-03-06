package com.android.similarwx.beans;

/**
 * Created by Administrator on 2018/7/14.
 */

public class SubUser extends BaseBean {
    private String userId;
    private String level;
    private String amount;
    private String returnAmout;
    private SubUerInfo userInfo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReturnAmout() {
        return returnAmout;
    }

    public void setReturnAmout(String returnAmout) {
        this.returnAmout = returnAmout;
    }

    public SubUerInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SubUerInfo userInfo) {
        this.userInfo = userInfo;
    }

    public class SubUerInfo{


        /**
         * passwd : 7c4a8d09ca3762af61e59520943dc26494f8941b
         * inviter : 29
         * birth : 2018-06-04 00:00:00
         * threeLevelCount : 0
         * serviceFlg : 0
         * unionid : null
         * cashFlg : 0
         * state : null
         * adminFlg : 0
         * modifyDate : null
         * grandpaInviter : 33
         * loginFlg : 1
         * userType : 0
         * id : 31
         * invitationCode : qo5u4
         * token : 49c304777baf5fc1de746cf0aab139b2
         * wechatAccount : 13601047550
         * parentInviter : 27
         * name : 唐刚
         * paymentPasswd : null
         * gender : 0
         * createDate : 2018-06-11 20:34:03
         * inviterFlg : 1
         * accId : tanggang
         * personalitySignature : null
         * twoLevelCount : 0
         * icon : https://nim.nosdn.127.net/NTI1MzE5Mg==/bmltYV8yMDI3OTQxNTgwXzE1MzE4MTU5NjUwMzNfYjM4ZWFiMTEtOTcyOS00OTg0LWE1NTYtZjNjOWU1Nzc5ZWFl
         * verifyCode : null
         * systemFlg : 0
         * totalBalance : 7.37
         * oneLevelCount : 1
         * userChildCount : 1
         * birthStr : null
         * passwdStr : null
         * alipay : tanggang@qq.com
         * email : tanggang@qq.com
         * recentLoginTime : 2018-07-25 17:24:57
         * registIp : null
         * registTime : 2018-06-11 20:34:03
         * mobile : 13601047550
         */

        private String passwd;
        private String inviter;
        private String birth;
        private int threeLevelCount;
        private int serviceFlg;
        private String unionid;
        private int cashFlg;
        private String state;
        private int adminFlg;
        private String modifyDate;
        private String grandpaInviter;
        private int loginFlg;
        private int userType;
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
        private int twoLevelCount;
        private String icon;
        private String verifyCode;
        private int systemFlg;
        private double totalBalance;
        private int oneLevelCount;
        private int userChildCount;
        private String birthStr;
        private String passwdStr;
        private String alipay;
        private String email;
        private String recentLoginTime;
        private String registIp;
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

        public int getThreeLevelCount() {
            return threeLevelCount;
        }

        public void setThreeLevelCount(int threeLevelCount) {
            this.threeLevelCount = threeLevelCount;
        }

        public int getServiceFlg() {
            return serviceFlg;
        }

        public void setServiceFlg(int serviceFlg) {
            this.serviceFlg = serviceFlg;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public int getCashFlg() {
            return cashFlg;
        }

        public void setCashFlg(int cashFlg) {
            this.cashFlg = cashFlg;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
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

        public int getLoginFlg() {
            return loginFlg;
        }

        public void setLoginFlg(int loginFlg) {
            this.loginFlg = loginFlg;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
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

        public int getTwoLevelCount() {
            return twoLevelCount;
        }

        public void setTwoLevelCount(int twoLevelCount) {
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

        public double getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(double totalBalance) {
            this.totalBalance = totalBalance;
        }

        public int getOneLevelCount() {
            return oneLevelCount;
        }

        public void setOneLevelCount(int oneLevelCount) {
            this.oneLevelCount = oneLevelCount;
        }

        public int getUserChildCount() {
            return userChildCount;
        }

        public void setUserChildCount(int userChildCount) {
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
    }
}
