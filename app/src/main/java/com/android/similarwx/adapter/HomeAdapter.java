package com.android.similarwx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.similarwx.R;
import com.android.similarwx.beans.GroupMessageBean;
import com.android.similarwx.utils.glide.NetImageUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;

import java.util.List;

/**
 * Created by Administrator on 2018/4/1.
 */

public class HomeAdapter extends BaseQuickAdapter<GroupMessageBean.ListBean,BaseViewHolder>{
    private Context context;
    private List<RecentContact> recents;
    private ImageView mainChartRedIv,mainExplainRedIv;
    public HomeAdapter(int layoutResId,Context context,List<GroupMessageBean.ListBean> mListData,ImageView mainChartRedIv,ImageView mainExplainRedIv){
        super(layoutResId,mListData);
        this.mainChartRedIv=mainChartRedIv;
        this.mainExplainRedIv=mainExplainRedIv;
        this.context=context;
    }
    public HomeAdapter(int layoutResId,Context context,List<GroupMessageBean.ListBean> mListData){
        super(layoutResId,mListData);
        this.context=context;
    }
    @Override
    protected void convert(BaseViewHolder helper, GroupMessageBean.ListBean item) {
        helper.setGone(R.id.item_group_count_tv,false);//隐藏这个控件
        helper.setText(R.id.item_group_tv,item.getGroupName());
        if(helper.getLayoutPosition()==0){
            helper.setImageResource(R.id.item_group_iv,R.drawable.online_notice);
//            helper.setGone(R.id.item_group_count_tv,true);//显示这个控件
        }else if(helper.getLayoutPosition()==1){
//            helper.setGone(R.id.item_group_count_tv,true);
            helper.setImageResource(R.id.item_group_iv,R.drawable.online_answer);
        }else{
            String groupIcon =item.getGroupIcon();
            if (!TextUtils.isEmpty(groupIcon)){
                NetImageUtil.glideImageNormal(context,groupIcon,(ImageView) helper.getView(R.id.item_group_iv));
            }
            String joinmode=item.getHallDisplay();
            if (!TextUtils.isEmpty(joinmode)){
                if (Integer.parseInt(joinmode)==1){//大厅显示的群
                    if (recents!=null){
                        for (RecentContact recentContact:recents){
                            if (recentContact.getSessionType()== SessionTypeEnum.Team){
                                if (recentContact.getContactId().equals(item.getGroupId())){
                                    int unReadCount=recentContact.getUnreadCount();
                                    if (unReadCount>0){
                                        helper.setGone(R.id.item_group_count_tv,true);
                                        if (unReadCount>=10)
                                            helper.setText(R.id.item_group_count_tv,"9+");
                                        else
                                            helper.setText(R.id.item_group_count_tv,unReadCount+"");
                                    }else {
                                        helper.setGone(R.id.item_group_count_tv,false);
                                    }
                                }
                            }
                        }
                    }
                }else {//非大厅显示的群
                    if (item.getUserExists().equals("1")){//在群里

                    }
                }
            }

        }

    }
    public void setRecentContacts(List<RecentContact> recents){
        this.recents=recents;
    }
}
