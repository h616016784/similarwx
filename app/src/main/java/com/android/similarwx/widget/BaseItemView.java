package com.android.similarwx.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.similarwx.R;

/**
 * Created by Administrator on 2018/4/10.
 */

public class BaseItemView extends LinearLayout {
    private TextView view_name_tv;
    private TextView view_right_tv;
    private ImageView view_iv;

    public BaseItemView(Context context) {
        super(context);
        iniView(context);
    }

    public BaseItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        iniView(context);
    }

    public BaseItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniView(context);
    }
    void iniView(Context context){
        View view=LayoutInflater.from(context).inflate(R.layout.view_base_item, this);
        view_iv=view.findViewById(R.id.view_base_iv);
        view_name_tv=view.findViewById(R.id.view_base_name_tv);
        view_right_tv=view.findViewById(R.id.view_base_right_tv);
    }
    public void setImageView(int image){
        view_iv.setVisibility(View.VISIBLE);
        view_iv.setImageResource(image);
    }
    public void setNameText(String text){
        view_name_tv.setText(text);
    }
    public void setNameText(int text){
        view_name_tv.setText(this.getResources().getString(text));
    }
    public void setRightText(String text){
        view_right_tv.setText(text);
    }
    public void setRightText(int text){
        view_right_tv.setText(this.getResources().getString(text));
    }
    public ImageView getRightImageView(){
        return view_iv;
    }
}
