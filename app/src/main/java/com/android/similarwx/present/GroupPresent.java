package com.android.similarwx.present;

import com.android.outbaselibrary.primary.AppContext;
import com.android.similarwx.R;
import com.android.similarwx.beans.GroupMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class GroupPresent extends BasePresent {

    public List<GroupMessageBean> getInitData(){
        List<GroupMessageBean> mListData=new ArrayList<>();
        GroupMessageBean bean=new GroupMessageBean();
        bean.setName(AppContext.getString(R.string.main_notice));
        mListData.add(bean);
        GroupMessageBean bean1=new GroupMessageBean();
        bean1.setName(AppContext.getString(R.string.main_online_answer));
        mListData.add(bean1);
        return mListData;
    }
}
