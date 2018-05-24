package com.android.similarwx.beans;

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

    private String retCode;
    private int count;
    private String retMsg;
    private List<ListBean> list;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
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
        private Object createTime;
        private String groupName;
        private String requirement;
        private int thunderNumber;
        private String gameType;
        private Object updateTime;
        private Object multipleRate;
        private Object attr;
        private int startRange;
        private Object attrValue;
        private int id;
        private String groupId;
        private int px;
        private Object groupIcon;
        private Object description;
        private String rewardRules;
        private int totalNumber;
        private int totalRedEnvelopes;
        private String notice;
        private Object attrName;
        private String createId;

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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getMultipleRate() {
            return multipleRate;
        }

        public void setMultipleRate(Object multipleRate) {
            this.multipleRate = multipleRate;
        }

        public Object getAttr() {
            return attr;
        }

        public void setAttr(Object attr) {
            this.attr = attr;
        }

        public int getStartRange() {
            return startRange;
        }

        public void setStartRange(int startRange) {
            this.startRange = startRange;
        }

        public Object getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(Object attrValue) {
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

        public Object getGroupIcon() {
            return groupIcon;
        }

        public void setGroupIcon(Object groupIcon) {
            this.groupIcon = groupIcon;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getRewardRules() {
            return rewardRules;
        }

        public void setRewardRules(String rewardRules) {
            this.rewardRules = rewardRules;
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

        public Object getAttrName() {
            return attrName;
        }

        public void setAttrName(Object attrName) {
            this.attrName = attrName;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }
    }
}
