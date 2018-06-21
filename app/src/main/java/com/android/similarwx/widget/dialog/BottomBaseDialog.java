package com.android.similarwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.beans.PopMoreBean;
import com.android.similarwx.widget.ListPopWindow;

import java.util.List;

/**
 * Created by hanhuailong on 2018/6/21.
 */

public class BottomBaseDialog extends Dialog{
    private TextView dialog_bottom_title_tv;
    private RecyclerView dialog_bottom_rv;
    private Button dialog_bottom_cancel_bt;

    private List<PopMoreBean> res;
    private PopMoreAdapter adapter;
    private OnItemClickListener mItemClickListener;
    public BottomBaseDialog(Context context) {
        super(context);
        init(context);
    }
    public BottomBaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
    private void init(Context context) {
        Window win=this.getWindow();
        win.requestFeature(Window.FEATURE_NO_TITLE);
        onCreate();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.windowAnimations = R.style.BottomInAndOutStyle;
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);
        win.setBackgroundDrawableResource(android.R.color.transparent);

        View view=View.inflate(getContext(),R.layout.dialog_bottom_in,null);
        dialog_bottom_title_tv=view.findViewById(R.id.dialog_bottom_title_tv);
        dialog_bottom_rv=view.findViewById(R.id.dialog_bottom_rv);
        dialog_bottom_cancel_bt=view.findViewById(R.id.dialog_bottom_cancel_bt);
        dialog_bottom_cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter=new PopMoreAdapter();
        dialog_bottom_rv.setLayoutManager(new LinearLayoutManager(context));
        dialog_bottom_rv.setAdapter(adapter);
        setContentView(view);

    }
    private void onCreate() {
    }

    public void setTitle(String title){
        dialog_bottom_title_tv.setText(title);
    }
    public void setList(List<PopMoreBean> res){
        this.res=res;
    }
    public void setOnClickItem(OnItemClickListener onItemClickListener){
        this.mItemClickListener=onItemClickListener;
    }

    private class PopMoreAdapter extends RecyclerView.Adapter<PopMoreAdapter.ViewHolder>{

        @Override
        public PopMoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_in,parent,false);
            PopMoreAdapter.ViewHolder holder = new PopMoreAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(PopMoreAdapter.ViewHolder holder, int position) {
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
                name = (TextView) view.findViewById(R.id.item_bottom_tv);
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
