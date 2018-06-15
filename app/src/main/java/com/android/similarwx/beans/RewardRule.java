package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/15.
 */

public class RewardRule extends BaseBean{

    /**
     * rewardName : 豹子
     * rewardValue : 6.66
     * amountReward : 6.66
     * groupId
     * id :
     */

    private String rewardName;
    private String rewardValue;
    private String amountReward;
    private String groupId;
    private String id;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(String rewardValue) {
        this.rewardValue = rewardValue;
    }

    public String getAmountReward() {
        return amountReward;
    }

    public void setAmountReward(String amountReward) {
        this.amountReward = amountReward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
