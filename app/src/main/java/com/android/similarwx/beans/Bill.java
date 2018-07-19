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
         * id : 1807190935059965853
         * amount : -10
         * tradeType : TRANSFER
         * balance : 1.0000001229E8
         * userId : 28
         * refId : 1807190935051103103
         * isSyncAccount : true
         * rebateLevel : null
         * modifyDate : null
         * createDate : 2018-07-19 09:35:05
         * rebateUserId : null
         * version : 0
         */

        private String id;
        private double amount;
        private String tradeType;
        private double balance;
        private String userId;
        private String refId;
        private boolean isSyncAccount;
        private String rebateLevel;
        private String modifyDate;
        private String createDate;
        private String rebateUserId;
        private String version;

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

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

        public boolean isSyncAccount() {
            return isSyncAccount;
        }

        public void setSyncAccount(boolean syncAccount) {
            isSyncAccount = syncAccount;
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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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
    }
}
