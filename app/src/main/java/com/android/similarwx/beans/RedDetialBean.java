package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RedDetialBean extends BaseBean{

    /**
     * pacId : 1808031525384137774
     * bestHand : false
     * icon : https://nos.netease.com/nim/NTI1MzE5Mg==/bmltYV8xOTI4NDg0MjYyXzE1Mjk1NTM3MTI3MDZfNWRjNjQwNWQtNDk5NC00MTVmLTgxNWItZGUxNmUzYzk3MWYz
     * groupName : 韩的测试群5
     * thunderDeduct : null
     * luckyReward : null
     * isLucky : false
     * isThunder : false
     * amount : 3.14
     * lucky : false
     * name : jokehhhhh
     * userId : 28
     * createDate : 2018-08-03 15:25:43
     * accId : hhltest1
     */

    private String pacId;
    private boolean bestHand;
    private String icon;
    private String groupName;
    private String thunderDeduct;
    private String luckyReward;
    private boolean isLucky;
    private boolean isThunder;
    private double amount;
    private boolean lucky;
    private String name;
    private String userId;
    private String createDate;
    private String accId;

    public String getPacId() {
        return pacId;
    }

    public void setPacId(String pacId) {
        this.pacId = pacId;
    }

    public boolean isBestHand() {
        return bestHand;
    }

    public void setBestHand(boolean bestHand) {
        this.bestHand = bestHand;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public boolean isLucky() {
        return isLucky;
    }

    public void setLucky(boolean lucky) {
        isLucky = lucky;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isThunder() {
        return isThunder;
    }

    public void setThunder(boolean thunder) {
        isThunder = thunder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
