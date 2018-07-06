package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RedDetialBean extends BaseBean{


    /**
     * amount : 3.14
     * bestHand : true
     * icon : https://nim.nosdn.127.net/NTI1MzE5Mg==/bmltYV8xOTI1NTU5MDA3XzE1MjgyNjQ4MTEzMzZfODlmZWNiZDAtMjIzNi00YTQ2LThjNmItNzFjNGFjMGQxNGMy
     * lucky : false
     * thunderDeduct : null
     * luckyReward : null
     * name : zhou
     * userId : 27
     * isLucky : false
     * accId : zhou123
     * isThunder : false
     */

    private double amount;
    private boolean bestHand;
    private String icon;
    private boolean lucky;
    private String thunderDeduct;
    private String luckyReward;
    private String name;
    private String userId;
    private boolean isLucky;
    private String accId;
    private boolean isThunder;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public boolean isLucky() {
        return lucky;
    }

    public void setLucky(boolean lucky) {
        this.lucky = lucky;
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
}
