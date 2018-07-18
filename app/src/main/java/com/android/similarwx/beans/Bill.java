package com.android.similarwx.beans;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public class Bill extends BaseBean {

    private  String count;
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
          /*
          * 			"id": "1806112337241447839",
			"amount": 50,
			"tradeType": "SEND_PACKAGE",
			"userId": "28",
			"refId": "1806112337245791242",
			"isSyncAccount": true,
			"rebateLevel": null,
			"modifyDate": null,
			"createDate": "2018-06-11 23:37:25",
			"rebateUserId": null,
			"version": "0"*/
        private String id;
        private String amount;
        private String tradeType;
        private String userId;
        private String refId;
        private String isSyncAccount;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
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

        public String getIsSyncAccount() {
            return isSyncAccount;
        }

        public void setIsSyncAccount(String isSyncAccount) {
            this.isSyncAccount = isSyncAccount;
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
