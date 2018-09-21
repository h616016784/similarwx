package com.android.similarwx.beans;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public class Bill extends BaseBean {

    private  String count;
    private  String freezeAmount;
    private  String totalAmount;
    private  String sumAmount;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(String freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    private List<BillDetail> accountDetailList;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<BillDetail> getAccountDetailList() {
        return accountDetailList;
    }

    public void setAccountDetailList(List<BillDetail> accountDetailList) {
        this.accountDetailList = accountDetailList;
    }

    public class BillDetail extends BaseBean{

        /**
         * tradeType : GRAP_PACKAGE
         * refId : 1808152240065127556
         * rebateLevel : null
         * modifyDate : null
         * rebateUserId : null
         * version : 0
         * id : 1808152240066069820
         * amount : 0.36
         * balance : 9.999956699E7
         * nickName : jokehhhhh
         * userId : 28
         * isSyncAccount : true
         * createDate : 2018-08-15 22:40:07
         */

        private String tradeType;
        private String refId;
        private String rebateLevel;
        private String modifyDate;
        private String rebateUserId;
        private String version;
        private String id;
        private double amount;
        private double balance;
        private String nickName;
        private String userId;
        private boolean isSyncAccount;
        private String createDate;
        private String rebateAmount;

        public boolean isSyncAccount() {
            return isSyncAccount;
        }

        public void setSyncAccount(boolean syncAccount) {
            isSyncAccount = syncAccount;
        }

        public String getRebateAmount() {
            return rebateAmount;
        }

        public void setRebateAmount(String rebateAmount) {
            this.rebateAmount = rebateAmount;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public String getRebateLevel() {
            return rebateLevel;
        }

        public void setRebateLevel(String rebateLevel) {
            this.rebateLevel = rebateLevel;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

        public String getRebateUserId() {
            return rebateUserId;
        }

        public void setRebateUserId(String rebateUserId) {
            this.rebateUserId = rebateUserId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isIsSyncAccount() {
            return isSyncAccount;
        }

        public void setIsSyncAccount(boolean isSyncAccount) {
            this.isSyncAccount = isSyncAccount;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
