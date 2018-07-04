package com.android.similarwx.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class GroupMessageBean extends BaseBean {


    /**
     * retCode : null
     * count : 1
     * retMsg : null
     * list : [{"groupType":"1","endRange":100,"createTime":null,"groupName":"测试群001","requirement":"bbb","thunderNumber":9,"gameType":"1","updateTime":null,"multipleRate":null,"attr":null,"startRange":10,"attrValue":null,"id":7,"groupId":"499364036","px":1,"groupIcon":null,"description":null,"rewardRules":"[{\"rewardName\":\"豹子\",\"rewardValue\":\"6.66\",\"amountReward\":\"6.66\",\"id\":\"\"}]","totalNumber":100,"totalRedEnvelopes":200,"notice":"aaa","attrName":null,"createId":"hhltest1"}]
     */

    private int count;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * groupType : 1
         * endRange : 100
         * createTime : null
         * groupName : 测试群001
         * requirement : bbb
         * thunderNumber : 9
         * gameType : 1
         * updateTime : null
         * multipleRate : null
         * attr : null
         * startRange : 10
         * attrValue : null
         * id : 7
         * groupId : 499364036
         * px : 1
         * groupIcon : null
         * description : null
         * rewardRules : [{"rewardName":"豹子","rewardValue":"6.66","amountReward":"6.66","id":""}]
         * totalNumber : 100
         * totalRedEnvelopes : 200
         * notice : aaa
         * attrName : null
         * createId : hhltest1
         */

        private String groupType;
        private int endRange;
        private String createTime;
        private String groupName;
        private String requirement;
        private int thunderNumber;
        private String gameType;
        private String updateTime;
        private String multipleRate;
        private String attr;
        private int startRange;
        private String attrValue;
        private int id;
        private String groupId;
        private int px;
        private String groupIcon;
        private String description;
        private String rewardRules;
        private int totalNumber;
        private int totalRedEnvelopes;
        private String notice;
        private String attrName;
        private String createId;
        private String userExists;

        private String hallDisplay;
        private String joinmode;
        private String deleteFlag;
        private String grabBagNumber;
        private String groupUserRule;

        public String getGroupUserRule() {
            return groupUserRule;
        }

        public void setGroupUserRule(String groupUserRule) {
            this.groupUserRule = groupUserRule;
        }

        public String getHallDisplay() {
            return hallDisplay;
        }

        public void setHallDisplay(String hallDisplay) {
            this.hallDisplay = hallDisplay;
        }

        public String getJoinmode() {
            return joinmode;
        }

        public void setJoinmode(String joinmode) {
            this.joinmode = joinmode;
        }

        public String getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(String deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getGrabBagNumber() {
            return grabBagNumber;
        }

        public void setGrabBagNumber(String grabBagNumber) {
            this.grabBagNumber = grabBagNumber;
        }

        public String getRewardRules() {
            return rewardRules;
        }

        public void setRewardRules(String rewardRules) {
            this.rewardRules = rewardRules;
        }

        public String getGroupType() {
            return groupType;
        }

        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }

        public int getEndRange() {
            return endRange;
        }

        public void setEndRange(int endRange) {
            this.endRange = endRange;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getRequirement() {
            return requirement;
        }

        public void setRequirement(String requirement) {
            this.requirement = requirement;
        }

        public int getThunderNumber() {
            return thunderNumber;
        }

        public void setThunderNumber(int thunderNumber) {
            this.thunderNumber = thunderNumber;
        }

        public String getGameType() {
            return gameType;
        }

        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getMultipleRate() {
            return multipleRate;
        }

        public void setMultipleRate(String multipleRate) {
            this.multipleRate = multipleRate;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public int getStartRange() {
            return startRange;
        }

        public void setStartRange(int startRange) {
            this.startRange = startRange;
        }

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getPx() {
            return px;
        }

        public void setPx(int px) {
            this.px = px;
        }

        public String getGroupIcon() {
            return groupIcon;
        }

        public void setGroupIcon(String groupIcon) {
            this.groupIcon = groupIcon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(int totalNumber) {
            this.totalNumber = totalNumber;
        }

        public int getTotalRedEnvelopes() {
            return totalRedEnvelopes;
        }

        public void setTotalRedEnvelopes(int totalRedEnvelopes) {
            this.totalRedEnvelopes = totalRedEnvelopes;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public String getUserExists() {
            return userExists;
        }
        public void setUserExists(String userExists) {
            this.userExists = userExists;
        }
    }
}
