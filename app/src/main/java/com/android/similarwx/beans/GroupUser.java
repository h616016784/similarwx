package com.android.similarwx.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanhuailong on 2018/6/4.
 */

public class GroupUser {

    /**
     * retCode : null
     * count : 3
     * retMsg : null
     * list : [{"id":2,"groupId":"499364036","createTime":"2018-05-22 11:21:40","updateTime":"2018-05-22 11:21:45","userId":"liyx588","userStatus":"1","userName":"李耀鑫"},{"id":3,"groupId":"499364036","createTime":"2018-05-22 11:21:40","updateTime":"2018-05-22 11:21:45","userId":"zhou123","userStatus":"1","userName":"ff"},{"id":1,"groupId":"499364036","createTime":"2018-05-22 11:21:40","updateTime":"2018-05-22 11:21:45","userId":"hhltest1","userStatus":"1","userName":"joke1"}]
     */

    private Object retCode;
    private int count;
    private Object retMsg;
    private List<ListBean> list;

    public Object getRetCode() {
        return retCode;
    }

    public void setRetCode(Object retCode) {
        this.retCode = retCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(Object retMsg) {
        this.retMsg = retMsg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 2
         * groupId : 499364036
         * createTime : 2018-05-22 11:21:40
         * updateTime : 2018-05-22 11:21:45
         * userId : liyx588
         * userStatus : 1
         * userName : 李耀鑫
         */

        private int id;
        private String groupId;
        private String createTime;
        private String updateTime;
        private String userId;
        private String userStatus;
        private String userName;
        private String userIcon;
        private String groupUserRule;

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getGroupUserRule() {
            return groupUserRule;
        }

        public void setGroupUserRule(String groupUserRule) {
            this.groupUserRule = groupUserRule;
        }
    }
}
