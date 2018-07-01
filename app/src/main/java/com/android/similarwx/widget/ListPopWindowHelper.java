package com.android.similarwx.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.beans.PopMoreBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/1.
 */

public class ListPopWindowHelper {
    private ListPopupWindow listPopupWindow=null;
    private Context mContext;
    public ListPopWindowHelper(List<PopMoreBean> list, Context context, View view){
        this.mContext=context;
        listPopupWindow=new ListPopupWindow(mContext);

        listPopupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setAnchorView(view);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        listPopupWindow.setModal(true);//设置是否是模式

        if (list!=null){
            listPopupWindow.setAdapter(new MyAdapter(list));
        }
    }

    public void show(){
        listPopupWindow.show();
    }

    public ListPopupWindow getListInstance(){
        return listPopupWindow;
    }
    private class MyAdapter extends BaseAdapter {

        private List<PopMoreBean> list;
        public MyAdapter(List<PopMoreBean> list){
            this.list=list;
        }
        @Override
        public int getCount() {
            if (list!=null){
                return list.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(mContext, R.layout.item_list_pop,null);
            TextView tv=view.findViewById(R.id.item_list_pop_tv);
            ImageView iv=view.findViewById(R.id.item_list_pop_iv);
            if (list!=null){
                iv.setImageResource(list.get(position).getImage());
                tv.setText(list.get(position).getName());
            }
            return view;
        }
    }
}
