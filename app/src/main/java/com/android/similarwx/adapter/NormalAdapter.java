package com.android.similarwx.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.similarwx.R;

import java.util.List;

/**
 * Created by hanhuailong on 2018/3/28.
 */

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.VH> {
    private List<String> mDatas;
    public NormalAdapter(List<String> datas){
        this.mDatas=datas;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test,parent,false);
        return new VH(v);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.title.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas!=null){
            return mDatas.size();
        }
        return 0;
    }

    public void addData(String s){
        mDatas.add(s);
        notifyItemInserted(mDatas.size()-1);
    }
    public class VH extends RecyclerView.ViewHolder{
        public TextView title;
        public VH(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.item_test_tv);
        }
    }
}
