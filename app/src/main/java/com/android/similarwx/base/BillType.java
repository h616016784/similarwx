package com.android.similarwx.base;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public enum BillType {


    /**
     * 发红包
     */
    SEND_PACKAGE("SEND_PACKAGE"),
    /**
     * 抢红包
     */
    GRAP_PACKAGE("GRAP_PACKAGE"),
    /**
     * 红包返点
     */
    PACKAGE_REBATE("PACKAGE_REBATE"),
    /**
     * 红包奖励
     */
    PACKAGE_REWARD("PACKAGE_REWARD"),
    /**
     * 雷包扣款
     */
    THUNDER_PACKAGE("THUNDER_PACKAGE"),
    /**
     * 雷包奖励
     */
    THUNDER_REWARD("THUNDER_REWARD"),
    /**
     * 红包退回
     */
    PACKAGE_RETURN("PACKAGE_RETURN"),
    /**
     * 在线充值
     */
    RECHARGE("RECHARGE"),
    /**
     * 提现
     */
    WITHDRAW("WITHDRAW"),
    /**
     * 转账
     */
    TRANSFER("TRANSFER"),
    /**
     * 线下充值
     */
    OFFLINE_RECHARGE("OFFLINE_RECHARGE");

    private String content;

    private BillType(String content){
        this.content=content;
    }

    @Override
    public String toString(){
        return content;
    }
}
