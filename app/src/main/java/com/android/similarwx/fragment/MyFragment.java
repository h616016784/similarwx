package com.android.similarwx.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.widget.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.my_base_name_tv)
    TextView myBaseNameTv;
    @BindView(R.id.my_base_name_iv)
    ImageView myBaseNameIv;
    @BindView(R.id.my_base_fen_tv)
    TextView myBaseFenTv;
    @BindView(R.id.my_base_account_tv)
    TextView myBaseAccountTv;
    @BindView(R.id.my_code_item)
    ItemView myCodeItem;
    Unbinder unbinder;
    @BindView(R.id.my_money_item)
    ItemView myMoneyItem;
    @BindView(R.id.my_play_item)
    ItemView myPlayItem;
    @BindView(R.id.my_set_item)
    ItemView mySetItem;
    @BindView(R.id.my_version_item)
    ItemView myVersionItem;
    @BindView(R.id.my_quit_item)
    ItemView myQuitItem;
    Unbinder unbinder1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.my_title);
        unbinder = ButterKnife.bind(this, contentView);
        init();
    }

    private void init() {
        myCodeItem.setNameText(R.string.my_code);myCodeItem.setImageView(R.drawable.icon_wo_shoucang);myCodeItem.setRightText("0");
        myMoneyItem.setNameText(R.string.my_money);myMoneyItem.setImageView(R.drawable.icon_wo_btn3);myMoneyItem.setRightText("");
        myPlayItem.setNameText(R.string.my_player);myPlayItem.setImageView(R.drawable.icon_wo_btn2);myPlayItem.setRightText("推荐:");
        mySetItem.setNameText(R.string.my_set);mySetItem.setImageView(R.drawable.icon_wo_shezhi);mySetItem.setRightText("");
        myVersionItem.setNameText(R.string.my_version);myVersionItem.setImageView(R.drawable.icon_wo_qianbao);myVersionItem.setRightText(getVersionName());
        myQuitItem.setNameText(R.string.my_quit);myQuitItem.setImageView(R.drawable.icon_wo_logout);myQuitItem.setRightText("");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_code_item,R.id.my_money_item, R.id.my_play_item, R.id.my_set_item, R.id.my_version_item, R.id.my_quit_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_code_item:
                break;
            case R.id.my_money_item:
                break;
            case R.id.my_play_item:
                break;
            case R.id.my_set_item:
                break;
            case R.id.my_version_item:
                break;
            case R.id.my_quit_item:
                break;
        }
    }
    private String getVersionName()
    {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = activity.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
