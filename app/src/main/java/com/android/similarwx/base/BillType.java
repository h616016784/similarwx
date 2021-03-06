package com.android.similarwx.base;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public enum BillType {

    /**
     * 全部
     */
    ALL("全部",""),
    /**
     * 红包发布
     */
    SEND_PACKAGE("红包发布","SEND_PACKAGE"),
    /**
     * 红包领取
     */
    GRAP_PACKAGE("红包领取","GRAP_PACKAGE"),
    /**
     * 推荐返点
     */
    PACKAGE_REBATE("推荐返点","PACKAGE_REBATE"),
    /**
     * 红包奖励
     */
    PACKAGE_REWARD("红包奖励","PACKAGE_REWARD"),
    /**
     * 中了雷包
     */
    THUNDER_PACKAGE("中了雷包","THUNDER_PACKAGE"),
    /**
     * 收到雷包
     */
    THUNDER_REWARD("收到雷包","THUNDER_REWARD"),
    /**
     * 红包积分结算
     */
    PACKAGE_RETURN("红包积分结算","PACKAGE_RETURN"),
    /**
     * 充值
     */
    RECHARGE("充值","RECHARGE"),
    /**
     * 扣除
     */
    WITHDRAW("扣除","WITHDRAW"),
    /**
     * 转账
     */
    TRANSFER("转账","TRANSFER"),

    /**
     * 收到转账
     */
    REC_TRANSFER("收到转账","REC_TRANSFER"),
    /**
     * 增加
     */
    OFFLINE_RECHARGE("增加","OFFLINE_RECHARGE"),

    /**
     * 冻结
     */
    FREEZE("冻结","FREEZE"),
    /**
     * 解冻
     */
    UNFREEZE("解冻","UNFREEZE");

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
