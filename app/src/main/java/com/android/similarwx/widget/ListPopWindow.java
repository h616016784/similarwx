package com.android.similarwx.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.android.similarwx.R;
import com.android.similarwx.beans.PopMoreBean;

import java.util.List;

/**
 * Created by hanhuailong on 2018/4/10.
 */

public class ListPopWindow {
    PopupWindow popupWindow;
    private RecyclerView pop_client_more_rv;
    private List<PopMoreBean> res;
    private PopMoreAdapter adapter;

    private OnItemClickListener mItemClickListener;
    public ListPopWindow(Context context, List<PopMoreBean> res){
        this.res=res;
        init(context);
    }

    private void init(Context context) {
        //准备PopupWindow的布局View
        View popupView = LayoutInflater.from(context).inflate(R.layout.pop_more_list, null);
        pop_client_more_rv=popupView.findViewById(R.id.pop_client_more_rv);
        popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popupView);
        //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        //设置PopupWindow动画
//        popupWindow.setAnimationStyle(R.style.AnimDown);
        //设置是否允许PopupWindow的范围超过屏幕范围
        popupWindow.setClippingEnabled(true);

        adapter=new PopMoreAdapter();
        pop_client_more_rv.setLayoutManager(new LinearLayoutManager(context));
        pop_client_more_rv.setAdapter(adapter);
    }
    public void show(View view){
        popupWindow.showAsDropDown(view);
    }
    public void dismiss(){
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }
    public void destroy(){
        if(popupWindow!=null){
            popupWindow.dismiss();
            popupWindow=null;
        }
    }

    public void setData(List<PopMoreBean> res){
        this.res=res;
        adapter.notifyDataSetChanged();
    }
    public void setOnClickItem(OnItemClickListener onItemClickListener){
        this.mItemClickListener=onItemClickListener;
    }

    private class PopMoreAdapter extends RecyclerView.Adapter<PopMoreAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pop_client_more,parent,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (res!=null && res.size()>0){
                PopMoreBean bean=res.get(position);
                holder.name.setText(bean.getName());
                holder.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mItemClickListener!=null){
                            mItemClickListener.onItemClick(position);
                        }
                        dismiss();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if(res!=null){
                return res.size();
            }
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView name;

            public ViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.item_pop_client_more_tv);
            }
            public View getView(){
                return itemView;
            }
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
