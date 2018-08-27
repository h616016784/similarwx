package com.android.similarwx.present;

import com.android.outbaselibrary.utils.Toaster;
import com.android.similarwx.beans.Notice;
import com.android.similarwx.beans.response.RspConfig;
import com.android.similarwx.beans.response.RspMoney;
import com.android.similarwx.beans.response.RspNotice;
import com.android.similarwx.inteface.SysNoticeViewInterface;
import com.android.similarwx.model.API;

import java.util.List;

/**
 * Created by Administrator on 2018/7/14.
 */

public class SysNoticePresent {
    SysNoticeViewInterface view;
    public SysNoticePresent(SysNoticeViewInterface view){
        this.view=view;
    }
    public void getNotice(){
        API.getInstance().getNotices("NOTIFY",this);
    }

    public void getMoney(){
        API.getInstance().getMoney("",this);
    }
    public void getContract(){
        API.getInstance().getNotices("CONTACT",this);
    }
    public void getExplain(){
        API.getInstance().getNotices("EXPLAIN",this);
    }
    public void getConfig(){
        API.getInstance().getConfig("",this);
    }
    public void analyze(RspNotice rspGroupSave) {
        if (rspGroupSave!=null){
            String result=rspGroupSave.getResult();
            if (result.equals("success")){
                List<Notice> list=rspGroupSave.getData().getList();
                view.refreshSysNotice(list);
            }else {
                Toaster.toastShort(rspGroupSave.getErrorMsg());
            }
        }
    }

    public void analyzeMoney(RspMoney rspMoney) {
        if (rspMoney!=null){
            String result=rspMoney.getResult();
            if (result.equals("success")){
                view.refreshSysMoney(rspMoney.getData());
            }else {
                Toaster.toastShort(rspMoney.getErrorMsg());
            }
        }
    }

    public void analyzeConfig(RspConfig rspMoney) {
        if (rspMoney!=null){
            String result=rspMoney.getResult();
            if (result.equals("success")){
                view.refreshSysConfig(rspMoney.getData());
            }else {
                Toaster.toastShort(rspMoney.getErrorMsg());
            }
        }
    }
}
