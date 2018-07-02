package com.android.similarwx.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.beans.PopMoreBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/1.
 */

public class ListPopWindowHelper {
    PopupWindow popupWindow;
    private RecyclerView pop_client_more_rv;
    private List<PopMoreBean> res;
    private MyAdapter adapter;

    private ListPopWindow.OnItemClickListener mItemClickListener;
    public ListPopWindowHelper(Context context, List<PopMoreBean> res){
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

        adapter=new MyAdapter();
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
    public void setOnClickItem(ListPopWindow.OnItemClickListener onItemClickListener){
        this.mItemClickListener=onItemClickListener;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pop,parent,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (res!=null && res.size()>0){
                PopMoreBean bean=res.get(position);
                holder.iv.setImageResource(bean.getImage());
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
            ImageView iv;

            public ViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.item_list_pop_tv);
                iv=view.findViewById(R.id.item_list_pop_iv);
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
