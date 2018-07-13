package com.android.similarwx.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.activity.LoginActivity;
import com.android.similarwx.base.AppConstants;
import com.android.similarwx.base.BaseFragment;
import com.android.similarwx.beans.User;
import com.android.similarwx.inteface.LoginViewInterface;
import com.android.similarwx.present.LoginPresent;
import com.android.similarwx.utils.FragmentUtils;
import com.android.similarwx.utils.SharePreferenceUtil;
import com.android.similarwx.utils.glide.CircleCrop;
import com.android.similarwx.widget.ItemView;
import com.android.similarwx.widget.dialog.CancelDialogBuilder;
import com.bumptech.glide.Glide;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyFragment extends BaseFragment implements LoginViewInterface {
    @BindView(R.id.my_base_head_iv)
    ImageView myBaseHeadIv;
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
    @BindView(R.id.my_base_ll)
    LinearLayout myBaseLl;

    private User mUser;
    private LoginPresent present;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onInitView(View contentView) {
        super.onInitView(contentView);
        mActionbar.setTitle(R.string.my_title);
        unbinder = ButterKnife.bind(this, contentView);
        present=new LoginPresent(this);
        init();
    }

    private void init() {
        myCodeItem.setImageView(R.drawable.icon_wo_shoucang);
        myMoneyItem.setNameText(R.string.my_money);
        myMoneyItem.setImageView(R.drawable.icon_wo_btn3);
        myMoneyItem.setRightText("");
        myPlayItem.setNameText(R.string.my_player);
        myPlayItem.setImageView(R.drawable.icon_wo_btn2);

        mySetItem.setNameText(R.string.my_set);
        mySetItem.setImageView(R.drawable.icon_wo_shezhi);
        mySetItem.setRightText("");
        myVersionItem.setNameText(R.string.my_version);
        myVersionItem.setImageView(R.drawable.icon_wo_qianbao);
        myVersionItem.setRightText(getVersionName());
        myQuitItem.setNameText(R.string.my_quit);
        myQuitItem.setImageView(R.drawable.icon_wo_logout);
        myQuitItem.setRightText("");

    }
    @Override
    public void onResume() {
        super.onResume();
        mUser= (User) SharePreferenceUtil.getSerializableObjectDefault(activity, AppConstants.USER_OBJECT);
        myCodeItem.setNameText(R.string.my_code);
        if (mUser!=null){
            myCodeItem.setRightText(mUser.getInvitationCode());
            myPlayItem.setRightText("推荐: "+mUser.getUserChildCount());
            myBaseNameTv.setText(mUser.getName());
            String url=mUser.getIcon();
            if (url!=null){
                Glide.with(activity)
                        .load(url)
//                        .override(120,120)
//                        .transform(new CircleCrop(activity))
//                        .placeholder(R.drawable.rp_avatar)
//                        .error(R.drawable.rp_avatar)
                        .into(myBaseHeadIv);
            }
            myBaseAccountTv.setText(mUser.getAccId());
            //性别
            String gender=mUser.getGender();
            if (TextUtils.isEmpty(gender)){
                myBaseNameIv.setImageResource(R.drawable.icon_sex_male);
            }else {
                if ("0".equals(gender))
                    myBaseNameIv.setImageResource(R.drawable.icon_sex_male);
                else if ("1".equals(gender))
                    myBaseNameIv.setImageResource(R.drawable.icon_sex_female);
                else
                    myBaseNameIv.setImageResource(R.drawable.icon_sex_male);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_code_item, R.id.my_money_item, R.id.my_play_item, R.id.my_set_item, R.id.my_version_item, R.id.my_quit_item, R.id.my_base_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_code_item:

                break;
            case R.id.my_money_item:
                FragmentUtils.navigateToNormalActivity(activity, new MoneyFragment(), null);
                break;
            case R.id.my_play_item:
                FragmentUtils.navigateToNormalActivity(activity, new PlayerFragment(), null);
                break;
            case R.id.my_set_item:
                FragmentUtils.navigateToNormalActivity(activity, new SetFragment(), null);
                break;
            case R.id.my_version_item:
                break;
            case R.id.my_quit_item:
                showQuitDialog();
                break;
            case R.id.my_base_ll://基本信息
                FragmentUtils.navigateToNormalActivity(activity, new MyBaseFragment(), null);
                break;
        }
    }

    private void showQuitDialog() {
        final CancelDialogBuilder cancel_dialogBuilder = CancelDialogBuilder
                .getInstance(getActivity());

        cancel_dialogBuilder.setTitleText("确定要退出？");
        cancel_dialogBuilder.setDetermineText("确定");

        cancel_dialogBuilder.isCancelableOnTouchOutside(true)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_dialogBuilder.dismiss();
                    }
                }).setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel_dialogBuilder.dismiss();
                        present.logout();

                    }
        }).show();
    }

    private String getVersionName() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = activity.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void showErrorMessage(String err) {

    }

    @Override
    public void loginScucces(User user) {

    }

    @Override
    public void logoutScucces(User user) {
        NIMClient.getService(AuthService.class).logout();
        startActivity(new Intent(getActivity(),
                LoginActivity.class));
        getActivity().finish();
    }
}
