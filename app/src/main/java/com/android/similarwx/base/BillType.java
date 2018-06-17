package com.android.similarwx.base;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public enum BillType {


    /**
     * 发红包
     */
    SEND_PACKAGE("发红包","SEND_PACKAGE"),
    /**
     * 抢红包
     */
    GRAP_PACKAGE("抢红包","GRAP_PACKAGE"),
    /**
     * 红包返点
     */
    PACKAGE_REBATE("红包返点","PACKAGE_REBATE"),
    /**
     * 红包奖励
     */
    PACKAGE_REWARD("红包奖励","PACKAGE_REWARD"),
    /**
     * 雷包扣款
     */
    THUNDER_PACKAGE("雷包扣款","THUNDER_PACKAGE"),
    /**
     * 雷包奖励
     */
    THUNDER_REWARD("雷包奖励","THUNDER_REWARD"),
    /**
     * 红包退回
     */
    PACKAGE_RETURN("红包退回","PACKAGE_RETURN"),
    /**
     * 在线充值
     */
    RECHARGE("在线充值","RECHARGE"),
    /**
     * 提现
     */
    WITHDRAW("提现","WITHDRAW"),
    /**
     * 转账
     */
    TRANSFER("转账","TRANSFER"),
    /**
     * 线下充值
     */
    OFFLINE_RECHARGE("线下充值","OFFLINE_RECHARGE");

    private String name;
    private String content;

    private BillType(String name,String content){
        this.name=name;
        this.content=content;
    }

    @Override
    public String toString(){
        return content;
    }
    public String toName(){
        return name;
    }
}
