package com.android.similarwx.widget.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.similarwx.R;
import com.android.similarwx.fragment.RedDetailFragment;
import com.android.similarwx.utils.FragmentUtils;

/**
 * Created by Administrator on 2018/4/9.
 */

public class GroupTypeDialogFragment extends DialogFragment implements View.OnClickListener {
    public static GroupTypeDialogFragment newInstance(String message) {
        return newInstance(null, message);
    }
    public static GroupTypeDialogFragment newInstance(String title, String message) {
        GroupTypeDialogFragment redDialogFragment = new GroupTypeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putString("title", title);
        redDialogFragment.setArguments(bundle);
        return redDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(R.style.Theme_AppCompat_Light_Dialog_MinWidth,0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_fragment_group_type,container);
        initView(view);
        return view;
    }

    private LinearLayout dialog_group_type_normal_ll,dialog_group_type_game_ll;
    private LinearLayout dialog_group_type_game_detail_ll,dialog_group_type_game_normal_ll,dialog_group_type_game_lei_ll,dialog_group_type_game_long_ll;

    private ImageView dialog_group_type_normal_iv,dialog_group_type_game_iv;
    private ImageView dialog_group_type_game_normal_iv,dialog_group_type_game_lei_iv,dialog_group_type_game_long_iv;
    private Button dialog_group_type_confirm;

    private  OnGroupTypeListener mListener;
    private void initView(View view) {
        dialog_group_type_normal_ll=view.findViewById(R.id.dialog_group_type_normal_ll);
        dialog_group_type_game_ll=view.findViewById(R.id.dialog_group_type_game_ll);
        dialog_group_type_game_normal_ll=view.findViewById(R.id.dialog_group_type_game_normal_ll);
        dialog_group_type_game_lei_ll=view.findViewById(R.id.dialog_group_type_game_lei_ll);
        dialog_group_type_game_long_ll=view.findViewById(R.id.dialog_group_type_game_long_ll);
        dialog_group_type_game_detail_ll=view.findViewById(R.id.dialog_group_type_game_detail_ll);

        dialog_group_type_normal_iv=view.findViewById(R.id.dialog_group_type_normal_iv);
        dialog_group_type_game_iv=view.findViewById(R.id.dialog_group_type_game_iv);
        dialog_group_type_game_normal_iv=view.findViewById(R.id.dialog_group_type_game_normal_iv);
        dialog_group_type_game_lei_iv=view.findViewById(R.id.dialog_group_type_game_lei_iv);
        dialog_group_type_game_long_iv=view.findViewById(R.id.dialog_group_type_game_long_iv);

        dialog_group_type_confirm=view.findViewById(R.id.dialog_group_type_confirm);
        dialog_group_type_confirm.setOnClickListener(this);

        dialog_group_type_normal_ll.setOnClickListener(this);
        dialog_group_type_game_ll.setOnClickListener(this);
        dialog_group_type_game_lei_ll.setOnClickListener(this);
    }

    public static void show(Activity activity,OnGroupTypeListener listener){
        GroupTypeDialogFragment redResultDialogFragment= GroupTypeDialogFragment.newInstance("");
        redResultDialogFragment.setOnConfirmListener(listener);

        FragmentTransaction transaction=activity.getFragmentManager().beginTransaction();
        transaction.add(redResultDialogFragment,"groupTypeDialogFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public static void disMiss(Activity activity){
        FragmentManager transaction=activity.getFragmentManager();
        Fragment prev = transaction.findFragmentByTag("groupTypeDialogFragment");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    private int groupType=-1;//1是游戏群  2是普通群
    private int gameType=-1;//1是普通 2是扫雷 3是接龙
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_group_type_normal_ll://普通群
                groupType=2;
                gameType=-1;
                dialog_group_type_normal_iv.setVisibility(View.VISIBLE);
                dialog_group_type_game_iv.setVisibility(View.GONE);
                dialog_group_type_game_detail_ll.setVisibility(View.GONE);
                break;
            case R.id.dialog_group_type_game_ll://游戏群
                groupType=1;
                gameType=2;
                dialog_group_type_normal_iv.setVisibility(View.GONE);
                dialog_group_type_game_detail_ll.setVisibility(View.VISIBLE);
                dialog_group_type_game_iv.setVisibility(View.VISIBLE);
                dialog_group_type_game_lei_iv.setVisibility(View.VISIBLE);
                break;
            case R.id.dialog_group_type_game_lei_ll:
                gameType=2;
                dialog_group_type_game_lei_iv.setVisibility(View.VISIBLE);
                break;
            case R.id.dialog_group_type_confirm:
                if (mListener!=null){
                    mListener.onGroupTypeClick(groupType+"",gameType+"");
                }
                disMiss(getActivity());
                mListener=null;
                break;
        }
    }

    public void setOnConfirmListener(OnGroupTypeListener listener){
        mListener=listener;
    }
    public interface OnGroupTypeListener{
        void onGroupTypeClick(String groupType,String gameType);
    }
}
