package com.android.similarwx.present;

import com.android.outbaselibrary.primary.AppContext;
import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.R;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.beans.User;
import com.android.similarwx.beans.response.RspGroup;
import com.android.similarwx.beans.response.RspGroupApply;
import com.android.similarwx.beans.response.RspUser;
import com.android.similarwx.inteface.MainGroupView;
import com.android.similarwx.model.API;
import com.android.similarwx.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class GroupPresent extends BasePresent {
    private MainGroupView mView;
    public GroupPresent(MainGroupView view){
        this.mView=view;
    }
    public List<GroupMessageBean.ListBean> getInitData(){
        List<GroupMessageBean.ListBean> mListData=new ArrayList<>();
//        GroupMessageBean bean=new GroupMessageBean();
//        bean.setName(AppContext.getString(R.string.main_notice));
//        mListData.add(bean);
//        GroupMessageBean bean1=new GroupMessageBean();
//        bean1.setName(AppContext.getString(R.string.main_online_answer));
//        mListData.add(bean1);
//        GroupMessageBean bean2=new GroupMessageBean();
//        bean2.setName("扫雷红包群");
//        bean2.setImageUrl("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%8D%A1%E9%80%9A%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=7&spn=0&di=188083435760&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2135025143%2C1274637269&os=4242483252%2C297718869&simid=3507610653%2C537022103&adpicid=0&lpn=0&ln=1994&fr=&fmq=1523785832464_R&fm=rs5&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fpic.58pic.com%2F58pic%2F14%2F18%2F74%2F858PICZ58PIC5Ci_1024.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bcbrtv_z%26e3Bv54AzdH3Frf1AzdH3F898b09bd_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=");
//        mListData.add(bean2);
        return mListData;
    }

    public void getGroupList(){
        String accid=SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ACCID,"无");
        API.getInstance().reqGroupList(accid,this);
    }

    public void analyzeRes(RspGroup rspGroup) {
        if (rspGroup!=null){
            String result=rspGroup.getResult();
            if (result.equals("success")){
                List<GroupMessageBean.ListBean> list=rspGroup.getData().getList();
                mView.groupRefresh(list);
            }else {
                Toaster.toastShort(rspGroup.getErrorMsg());
            }
        }
    }

    public void doGroupAppley(String groupId) {
        String accid=SharePreferenceUtil.getString(AppContext.getContext(), AppConstants.USER_ACCID,"无");
        API.getInstance().doGroupAppley(groupId,accid,this);
    }

    public void analyzeApplyRes(RspGroupApply rspGroupApply) {
        if (rspGroupApply!=null){
            String result=rspGroupApply.getResult();
            if (result.equals("success")){
                mView.groupApply("success");
            }else {
                Toaster.toastShort(rspGroupApply.getErrorMsg());
            }
        }
    }
}
