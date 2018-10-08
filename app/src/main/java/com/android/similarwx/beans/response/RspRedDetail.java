package com.android.similarwx.beans.response;

import com.android.similarwx.beans.RedDetialBean;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/19.
 */

public class RspRedDetail extends BaseResponse{
    private RedListData data;

    public RedListData getData() {
        return data;
    }

    public void setData(RedListData data) {
        this.data = data;
    }

    public static class RedListData {
        private String count;
        private String spendSecond;
        private List<RedDetialBean> redPacDetailList;

        public String getSpendSecond() {
            return spendSecond;
        }

        public void setSpendSecond(String spendSecond) {
            this.spendSecond = spendSecond;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<RedDetialBean> getRedPacDetailList() {
            return redPacDetailList;
        }

        public void setRedPacDetailList(List<RedDetialBean> redPacDetailList) {
            this.redPacDetailList = redPacDetailList;
        }
    }
}
