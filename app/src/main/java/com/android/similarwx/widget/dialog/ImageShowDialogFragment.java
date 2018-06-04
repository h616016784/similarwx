package com.android.similarwx.widget.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;

import java.util.ArrayList;

/**
 * Created by hanhuailong on 2018/4/9.
 */

public class ImageShowDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String flag="position";
    private static final String imageFlag="iamgUrls";

    private ViewPager mVp;
    private TextView mTv;
    private MyViewPager viewPager;
    private ArrayList<String> imageUrls;
    private View[] mview;
    int scrollPositon=0;
    public static ImageShowDialogFragment newInstance(int position,ArrayList<String> imageUrls) {
        ImageShowDialogFragment f = new ImageShowDialogFragment();
        Bundle args = new Bundle();
        args.putInt(flag, position);
        args.putStringArrayList(imageFlag,imageUrls);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);//宽度是基本和屏幕差不多
        initData();
    }

    private void initData() {
        Bundle bundle=getArguments();
        if (bundle!=null) {
            scrollPositon = bundle.getInt(flag);
            imageUrls=bundle.getStringArrayList(imageFlag);
        }
        if (imageUrls!=null){
            int size=imageUrls.size();
            mview=new View[size];
            for (int i=0;i<size;i++){
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_view_pager_show_image,null);
                ViewHolder viewHolder=new ViewHolder();
                viewHolder.showImageIv=view.findViewById(R.id.item_viewpager_show_image_iv);
                mview[i]=view;
                mview[i].setTag(viewHolder);

                viewHolder.showImageIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.dialog_image_show, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        mVp=v.findViewById(R.id.dialog_show_vp);
        mTv=v.findViewById(R.id.dialog_show_tv);
        viewPager=new MyViewPager();
        mVp.setAdapter(viewPager);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTv.setText("第 "+(position+1)+" 张图");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVp.setCurrentItem(scrollPositon);
    }

    public static ImageShowDialogFragment showDialog(FragmentManager fragmentManager, Integer postiton, ArrayList<String> imageUrls) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("imageShow");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ImageShowDialogFragment newFragment = ImageShowDialogFragment.newInstance(postiton,imageUrls);
        newFragment.show(ft, "imageShow");
        return newFragment;
    }

    @Override
    public void onClick(View v) {
    }

    public class MyViewPager extends PagerAdapter {

        public MyViewPager(){
        }

        @Override
        public int getCount() {
            if (imageUrls!=null)
                return imageUrls.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view=mview[position];
            container.addView(view);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            String imageUrl=imageUrls.get(position);
//            if (!TextUtils.isEmpty(imageUrl)){
//                GlideUtil.displayImageSpe(getActivity(),imageUrl,viewHolder.showImageIv);
//            }
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(mview[position]);
        }
    }

    public class ViewHolder {
        public ImageView showImageIv;
        public ViewHolder() {
        }
    }
}
