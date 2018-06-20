package com.android.similarwx.beans;

/**
 * Created by hanhuailong on 2018/6/13.
 */

public class GroupRule extends BaseBean {
    private String Id;
    private String groupId;
    private String rewardName;
    private String rewardValue;
    private String amountReward;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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
}
