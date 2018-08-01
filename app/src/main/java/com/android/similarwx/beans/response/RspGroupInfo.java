package com.android.similarwx.beans.response;

import com.android.similarwx.beans.BaseBean;

/**
 * Created by hanhuailong on 2018/7/12.
 */

public class RspGroupInfo extends BaseResponse{

    public GroupInfo getData() {
        return data;
    }

    public void setData(GroupInfo data) {
        this.data = data;
    }

    private GroupInfo data;

    public static class GroupInfo extends BaseBean{
        /**
         * createTime : null
         * groupName : 测试群001
         * thunderNumber : null
         * gameType : 2
         * startRange : 25
         * id : 7
         * userExists : 1
         * hallDisplay : 0
         * description : null
         * totalNumber : 100
         * rewardRules :
         * totalRedEnvelopes : 37
         * joinmode : null
         * notice : aaa
         * deleteFlag : 1
         * createId : hhltest1
         * groupType : 1
         * endRange : 100
         * requirement : bbb
         * grabBagNumber : 7
         * updateTime : 2018-06-19 09:09:46
         * multipleRate : 2
         * attr : null
         * attrValue : null
         * updateUserId : null
         * groupId : 499364036
         * px : 1
         * groupIcon : http://39.105.107.104/images/group/group_default.png,http://39.105.107.104/images/group/group_default.png
         * attrName : null
         * groupUserRule : 3
         */

        private String createTime;
        private String groupName;
        private String thunderNumber;
        private String gameType;
        private int startRange;
        private int id;
        private String userExists;
        private String hallDisplay;
        private String description;
        private int totalNumber;
        private String rewardRules;
        private int totalRedEnvelopes;
        private String joinmode;
        private String notice;
        private String deleteFlag;
        private String createId;
        private String groupType;
        private int endRange;
        private String requirement;
        private int grabBagNumber;
        private String updateTime;
        private double multipleRate;
        private String attr;
        private String attrValue;
        private String updateUserId;
        private String groupId;
        private int px;
        private String groupIcon;
        private String attrName;
        private String groupUserRule;

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

        public String getThunderNumber() {
            return thunderNumber;
        }

        public void setThunderNumber(String thunderNumber) {
            this.thunderNumber = thunderNumber;
        }

        public String getGameType() {
            return gameType;
        }

        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

        public int getStartRange() {
            return startRange;
        }

        public void setStartRange(int startRange) {
            this.startRange = startRange;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserExists() {
            return userExists;
        }

        public void setUserExists(String userExists) {
            this.userExists = userExists;
        }

        public String getHallDisplay() {
            return hallDisplay;
        }

        public void setHallDisplay(String hallDisplay) {
            this.hallDisplay = hallDisplay;
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

        public String getRewardRules() {
            return rewardRules;
        }

        public void setRewardRules(String rewardRules) {
            this.rewardRules = rewardRules;
        }

        public int getTotalRedEnvelopes() {
            return totalRedEnvelopes;
        }

        public void setTotalRedEnvelopes(int totalRedEnvelopes) {
            this.totalRedEnvelopes = totalRedEnvelopes;
        }

        public String getJoinmode() {
            return joinmode;
        }

        public void setJoinmode(String joinmode) {
            this.joinmode = joinmode;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(String deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
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

        public String getRequirement() {
            return requirement;
        }

        public void setRequirement(String requirement) {
            this.requirement = requirement;
        }

        public int getGrabBagNumber() {
            return grabBagNumber;
        }

        public void setGrabBagNumber(int grabBagNumber) {
            this.grabBagNumber = grabBagNumber;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public double getMultipleRate() {
            return multipleRate;
        }

        public void setMultipleRate(double multipleRate) {
            this.multipleRate = multipleRate;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
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

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public String getGroupUserRule() {
            return groupUserRule;
        }

        public void setGroupUserRule(String groupUserRule) {
            this.groupUserRule = groupUserRule;
        }
    }



}
