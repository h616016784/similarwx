package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RedDetialBean extends BaseBean{

    /**
     * pacId : 1806191142543649989
     * groupName : 测试群001
     * thunderDeduct : null
     * luckyReward : null
     * modifyDate : null
     * isLucky : false
     * version : 0
     * isThunder : false
     * id : 1806191143018672359
     * amount : 2.65
     * groupId : 7
     * userId : 28
     * isSyncAccount : true
     * createDate : 2018-06-19 11:43:01
     * accId : hhltest1
     */

    private String pacId;
    private String groupName;
    private String thunderDeduct;
    private String luckyReward;
    private String modifyDate;
    private boolean isLucky;
    private String version;
    private boolean isThunder;
    private String id;
    private double amount;
    private String groupId;
    private String userId;
    private boolean isSyncAccount;
    private String createDate;
    private String accId;

    public String getPacId() {
        return pacId;
    }

    public void setPacId(String pacId) {
        this.pacId = pacId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getThunderDeduct() {
        return thunderDeduct;
    }

    public void setThunderDeduct(String thunderDeduct) {
        this.thunderDeduct = thunderDeduct;
    }

    public String getLuckyReward() {
        return luckyReward;
    }

    public void setLuckyReward(String luckyReward) {
        this.luckyReward = luckyReward;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public boolean isLucky() {
        return isLucky;
    }

    public void setLucky(boolean lucky) {
        isLucky = lucky;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isThunder() {
        return isThunder;
    }

    public void setThunder(boolean thunder) {
        isThunder = thunder;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isSyncAccount() {
        return isSyncAccount;
    }

    public void setSyncAccount(boolean syncAccount) {
        isSyncAccount = syncAccount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }
}
