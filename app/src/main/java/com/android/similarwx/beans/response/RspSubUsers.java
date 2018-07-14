package com.android.similarwx.beans.response;

import com.android.similarwx.beans.SubUser;

import java.util.List;

/**
 * Created by Administrator on 2018/7/14.
 */

public class RspSubUsers extends BaseResponse{
    private SubUsersBean data;

    public SubUsersBean getData() {
        return data;
    }

    public void setData(SubUsersBean data) {
        this.data = data;
    }

    public class SubUsersBean{
        private String count;
        private List<SubUser> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<SubUser> getList() {
            return list;
        }

        public void setList(List<SubUser> list) {
            this.list = list;
        }
    }
}
