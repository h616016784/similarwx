package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public class Bill extends BaseBean {
    private  String userId;
    private  String  amount;
    private  String isThunder;
    private  String thunderAmount;
    private  String isLucky;
    private  String luckAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsThunder() {
        return isThunder;
    }

    public void setIsThunder(String isThunder) {
        this.isThunder = isThunder;
    }

    public String getThunderAmount() {
        return thunderAmount;
    }

    public void setThunderAmount(String thunderAmount) {
        this.thunderAmount = thunderAmount;
    }

    public String getIsLucky() {
        return isLucky;
    }

    public void setIsLucky(String isLucky) {
        this.isLucky = isLucky;
    }

    public String getLuckAmount() {
        return luckAmount;
    }

    public void setLuckAmount(String luckAmount) {
        this.luckAmount = luckAmount;
    }
}
